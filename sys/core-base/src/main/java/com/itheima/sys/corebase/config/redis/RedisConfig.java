package com.itheima.sys.corebase.config.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.itheima.sys.corebase.constants.AuthConstants;
import com.itheima.sys.corebase.constants.Constants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Redis配置
 * @author 10445
 */
@Configuration
//开启缓存支持
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
    /**
     * 自定义key生成策略 按类名::方法名::参数名
     * 没有填主键时  生成主键规则
     * @return
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append("::" + method.getName() + ":");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 缓存管理器
     * @param factory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        //链式调用
        //redis缓存配置
        RedisCacheConfiguration redisCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        //过期时间
                        .entryTtl(Duration.ofDays(1))
                        //设置序列化方式，否则redis客户端会呈现二进制字符。 value使用JSON序列化，key使用String序列化
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()))
                        //定义key前缀
                        .computePrefixWith(cacheName -> AuthConstants.CACHEKEY.concat(":").concat(cacheName))
                        .disableCachingNullValues();

        //RedisCacheManager 构造器需要二个参数： RedisCacheWriter 负责操作redis 不依赖redisTemplate
        //RedisCacheConfiguration 设置redis缓存配置

        //创建无锁的RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
        return RedisCacheManager.builder(redisCacheWriter).cacheDefaults(redisCacheConfiguration).build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericFastJsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericFastJsonRedisSerializer());
        return redisTemplate;
    }


}
