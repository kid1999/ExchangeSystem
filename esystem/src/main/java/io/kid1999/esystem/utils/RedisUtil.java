package io.kid1999.esystem.utils;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static io.kid1999.esystem.common.Constants.REDIS_EXPIRE_DATE;

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
    private static RedisTemplate redisTemplate;

    public static Long incr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        if ((null == increment || increment.longValue() == 0) && REDIS_EXPIRE_DATE > 0) {//初始设置过期时间
            entityIdCounter.expire(REDIS_EXPIRE_DATE, TimeUnit.SECONDS);
        }
        return increment;
    }

}