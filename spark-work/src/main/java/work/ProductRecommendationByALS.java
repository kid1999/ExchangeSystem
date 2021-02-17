package work;

import avro.shaded.com.google.common.collect.ImmutableMap;
import cn.hutool.core.io.FileUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.spark_project.guava.collect.ImmutableList;
import util.RatingUtil;

import java.util.*;

/**
 * @author kid1999
 * @create 2021-02-10 12:56
 * @description TODO 商品推荐功能，基于spark-als 和 kafka
 **/

public final class ProductRecommendationByALS {

    //	商城系统采集的数据集格式 Data Format:
    //	用户ID，商品ID，用户行为评分(0-5)
    //	UserID,ItemId,Rating


    public static void main(String[] args) throws InterruptedException {
        final String brokers = "159.75.6.26:9092";
        final String KAFKA_TOPIC = "ProductRecommendation";
        final String modelPath = "F:\\Git\\ExchangeSystem\\spark-work\\src\\main\\resources\\model";
        final String dataPath = "F:\\Git\\ExchangeSystem\\spark-work\\src\\main\\resources\\data\\data.txt";
        final int RECOMMEND_NUM = 3;
        final String ES_INDEX = "recommendation-goods";


        SparkConf sparkConf = new SparkConf()
                .setAppName("Product Recommendation")
                .setMaster("local[2]")
                .set("es.index.auto.create", "true")
                .set("es.nodes", "159.75.6.26")
                .set("es.port", "9200")
                .set("es.nodes.wan.only", "true");



        // TODO 原始数据
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        final JavaRDD<Rating> originalData = jsc.textFile(dataPath)
                .map(new Function<String, Rating>() {
                    @Override
                    public Rating call(String str) {
                        return RatingUtil.parseRating(str);
                    }
                });


        // TODO 每隔5秒钟，咱们的spark streaming作业就会收集最近60秒内的数据源接收过来的数据
        JavaStreamingContext jssc = new JavaStreamingContext(
                jsc, Durations.seconds(20));


        // TODO 基于kafka direct api模式，构建Kafka参数
        // 两个值，key，value；key没有什么特殊的意义；value中包含了kafka topic中的一条一条的实时日志数据
        HashMap<String, Object> kafkaParams = new HashMap<>();
        //Kafka服务监听端口
        kafkaParams.put("bootstrap.servers", brokers);
        //指定kafka输出key的数据类型及编码格式（默认为字符串类型编码格式为uft-8）
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        //指定kafka输出value的数据类型及编码格式（默认为字符串类型编码格式为uft-8）
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        //消费者ID，随意指定
        kafkaParams.put("group.id", "jis");
        //指定从latest(最新,其他版本的是largest这里不行)还是smallest(最早)处开始读取数据
        kafkaParams.put("auto.offset.reset", "latest");
        //如果true,consumer定期地往zookeeper写入每个分区的offset
        kafkaParams.put("enable.auto.commit", false);

        // TODO 设置参数和topic，读取kafka消息流
        Collection<String> topics = Arrays.asList(KAFKA_TOPIC);
        final JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        jssc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics,kafkaParams)
                );

        // TODO 获取 kafka流数据
        JavaDStream<Rating> dStream = stream.map(new Function<ConsumerRecord<String, String>, Rating>() {
            @Override
            public Rating call(ConsumerRecord<String, String> record) throws Exception {
                return RatingUtil.parseRating(record.value());
            }
        });

        // TODO ==================================  开始计算 =================================================
        dStream.foreachRDD(rdd ->{

            // TODO 将原始数据集和新的用户行为数据进行合并
            JavaRDD<Rating> calculations = originalData.union(rdd);
            System.out.println("originalTextFile count:" + calculations.count());

            // TODO 设置ALS : 数据,维度,迭代次数,正则化参数
            MatrixFactorizationModel model = ALS.train(calculations.rdd(), 10, 6, 0.01);

            // TODO 推荐商品
            System.out.println(rdd.count());
            List<Rating> collect = rdd.collect();
            HashMap<String, String> map = new HashMap<>();
            map.put("es.mapping.id","userId");
            map.put("es.write.operation","upsert");
            for(Rating rating:collect){
                Rating[] ratings = model.recommendProducts(rating.user(), RECOMMEND_NUM);
                int[] products = new int[RECOMMEND_NUM];
                for (int i = 0; i <RECOMMEND_NUM ; i++) {
                    products[i] = ratings[i].product();
                }
                Map<String, ?> doc = ImmutableMap.of("userId", rating.user(),"goodsId",products);
                JavaRDD<Map<String, ?>> javaRDD = jsc.parallelize(ImmutableList.of(doc));
                JavaEsSpark.saveToEs(javaRDD, ES_INDEX + "/_doc",map);
            }

            // TODO 保存模型
            if(FileUtil.exist(modelPath)){
                FileUtil.del(modelPath);
            }
            model.save(jsc.sc(),modelPath);

            // TODO 保存新数据
            if(!rdd.isEmpty()){
                rdd.repartition(1)
                        .saveAsTextFile("F:\\Git\\ExchangeSystem\\spark-work\\src\\main\\resources\\data\\" + "save-" + System.currentTimeMillis());
            }
        });

        // TODO ====================================== 结束计算 =============================================
        jssc.start();
        jssc.awaitTermination();
    }
}