package io.kid1999.esystem.utils;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static io.kid1999.esystem.common.Constants.REDIS_EXPIRE_DATE;
import static java.util.concurrent.TimeUnit.MICROSECONDS;

/**
 * @author kid1999
 * @create 2021-01-10 13:45
 * @description redis 工具类
 **/
@Log
@Component
@Configuration
public class RedisUtil {

    @Autowired
    private  RedisTemplate redisTemplate;


    // 字段自增
    public void incr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        if ((null == increment || increment.longValue() == 0) && REDIS_EXPIRE_DATE > 0) {
            entityIdCounter.expire(REDIS_EXPIRE_DATE, TimeUnit.SECONDS);
        }
    }

    // 设置定时key-value
    public void setKey(String key,Object value,long sec){
        redisTemplate.opsForValue().set(key,value,sec,TimeUnit.SECONDS);
    }

    // 获取value
    public Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

}