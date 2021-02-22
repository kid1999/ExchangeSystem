package io.kid1999.esystem.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kid1999
 * @create 2021-02-17 10:36
 * @description TODO
 **/
@Slf4j
@Component
public class KafkaUtil {

    @Resource
    private KafkaTemplate kafkaTemplate;


    public static final String KAFKA_TOPIC = "ProductRecommendation";

    /**
     * 商品推荐 发送给spark （userId,goodsId,score）
     */

    @Async("taskExecutor")
    public void send2Spark(Long userId,Long goodsId,double score){
        kafkaTemplate.send(KAFKA_TOPIC,"search",userId + "," + goodsId + "," + score);
    }

}