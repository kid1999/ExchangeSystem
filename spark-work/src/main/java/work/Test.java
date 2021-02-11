package work;

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
import util.RatingUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author kid1999
 * @create 2021-02-10 13:08
 * @description TODO
 **/
public class Test {
    public static void main(String[] args) throws InterruptedException {
        SparkConf sparkConf = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]");

        // TODO 原始数据
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        final JavaRDD<Rating> originalData = jsc.textFile("src/main/resources/data/data.txt")
                .map(new Function<String, Rating>() {
                    @Override
                    public Rating call(String str) {
                        return RatingUtil.parseRating(str);
                    }
                });
        // 读不起来
        MatrixFactorizationModel model = MatrixFactorizationModel.load(jsc.sc(), "src/main/resources/model");
        Rating[] ratings = model.recommendProducts(4, 3);
        for(Rating rating:ratings){
            System.out.println(rating.toString());
        }

    }
}