package com.itheima.sys.corebase.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author 10445
 */
public class BeanCopyUtils {
    /**
     * 复制Bean实体
     * @param object
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> T convertBean(Object object, Class<T> entityClass) {
        if (Objects.isNull(object)) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(object), entityClass);
    }

    /**
     * 复制List中的Bean实体 以List返回
     * @param list
     * @param entityClass
     * @param <E>
     * @param <T>
     * @return
     */
    public static <E, T> List<E> convertList(List<T> list, Class<E> entityClass) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().map(item -> convertBean(item, entityClass)).collect(Collectors.toList());
    }
}
