package com.itheima.sys.corebase.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;


/**
 * redis工具类
 * @author 10445
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class RedisUtil<T> {
    @Resource
    private RedisTemplate<String, T> redisTemplate;

    /**
     * 普通缓存存入
     * @param key
     * @param value
     * @return
     */
    public Boolean ptSet(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  HashSet
     * @param key
     * @param map
     * @return 
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key,map);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
