package com.itheima.sys.corebase.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.sys.corebase.constants.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基本Entity
 * @author 10445
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseEntity<T extends Model<T>>  extends Model<T> implements Serializable {

    private static final long serialVersionUID = 5361209634636523305L;
    /**
     * 表id
     * 格式化时转string
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long id;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * 状态位
     * @TableLogoc
     */
    @TableLogic
    @TableField(value = "deleted")
    protected String deleted = Constants.NOT_DELETE;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    protected LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE, update = "NOW()")
    protected LocalDateTime updatedTime;

    /**
     * 版本
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT_UPDATE, update = "%s+1")
    protected Long version;
}
