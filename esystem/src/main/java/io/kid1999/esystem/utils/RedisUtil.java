package io.kid1999.esystem.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.kid1999.esystem.common.Constants.REDIS_EXPIRE_DATE;

/**
 * @author kid1999
 * @create 2021-01-10 13:45
 * @description redis 工具类
 **/
@Deprecated
@Slf4j
@Configuration
public class RedisUtil {

    @Autowired
    private  RedisTemplate redisTemplate;


    /**
     * String
     */

    // 字段自增
    public void incr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        if ((null == increment || increment.longValue() == 0) && REDIS_EXPIRE_DATE > 0) {
            entityIdCounter.expire(REDIS_EXPIRE_DATE, TimeUnit.SECONDS);
        }
    }

    // 设置定时key-value
    public void setStringKey(String key,Object value,long sec){
        redisTemplate.opsForValue().set(key,value,sec,TimeUnit.SECONDS);
    }

    // 设置key-value
    public void setStringKey(String key1,Object value){
        redisTemplate.opsForValue().set(key1,value);
    }

    // 获取value
    public Object getStringValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

    // key是否存在
    public boolean isExistStringKey(String key){
        return redisTemplate.opsForValue().get(key) == null ? false : true;
    }


    /**
     * hash
     */


    // 字段自增
    public void incrementHash(String key1,Long key2){
        redisTemplate.opsForHash().increment(key1, key2 + "", 1);
    }

    public Map<String, Long> getHashList(String key1){
        Map<String, Long> entries = redisTemplate.opsForHash().entries(key1);
        return entries;
    }




    public boolean deleteKey(String key){
        return redisTemplate.delete(key);
    }

}