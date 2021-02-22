package io.kid1999.esystem.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * @author kid1999
 * @create 2021-02-22 18:05
 * @description spring cache - redis 配置文件
 *              配置cache的定时缓存
 **/
@Configuration
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> template) {

        // 基本配置
        RedisCacheConfiguration defaultCacheConfiguration =
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        // 此处使用的默认序列化方式  --> 来自 redis config
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getStringSerializer()))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getValueSerializer()))
                        // 不缓存null
                        .disableCachingNullValues()
                        // 缓存数据保存1小时
                        .entryTtl(Duration.ofHours(1));

        // 够着一个redis缓存管理器
        RedisCacheManager redisCacheManager =
                RedisCacheManager
                        .RedisCacheManagerBuilder
                        .fromConnectionFactory(template.getConnectionFactory())
                        .cacheDefaults(defaultCacheConfiguration)
                        .transactionAware()
                        .build();

        return redisCacheManager;
    }

}