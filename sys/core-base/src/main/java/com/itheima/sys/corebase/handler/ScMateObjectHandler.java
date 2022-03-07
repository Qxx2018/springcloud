package com.itheima.sys.corebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.itheima.sys.corebase.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 自动填充器
 * sql自动注入字段
 * @author 10445
 */
@Slf4j
@Configuration
public class ScMateObjectHandler implements MetaObjectHandler {
    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        //创建时间
        Object createdDate = this.getFieldValByName("createdTime",metaObject);
        if (Objects.isNull(createdDate)) {
            this.setFieldValByName("createdTime",now, metaObject);
        }
        //更新时间
        Object updatedTime = this.getFieldValByName("updatedTime",metaObject);
        if (Objects.isNull(updatedTime)) {
            this.setFieldValByName("updatedTime",now,metaObject);
        }
        //逻辑删除字段
        Object deleted = this.getFieldValByName("deleted",metaObject);
        if (Objects.isNull(deleted)) {
            this.setFieldValByName("deleted", Constants.NOT_DELETE,metaObject);
        }
        /**
         * 初始化乐观锁
         */
        Object version = this.getFieldValByName("version",metaObject);
        if (Objects.isNull(version)) {
            this.setFieldValByName("version",0L,metaObject);
        }
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //更新时间
        this.setFieldValByName("updatedTime",LocalDateTime.now(),metaObject);
    }

}
