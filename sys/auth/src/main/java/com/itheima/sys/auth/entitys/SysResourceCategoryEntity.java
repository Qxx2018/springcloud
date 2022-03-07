package com.itheima.sys.auth.entitys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.sys.corebase.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 资源分类
 * @author 10445
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_resource_category")
public class SysResourceCategoryEntity extends BaseEntity<SysResourceCategoryEntity> {

    /**
     * 资源分类名
     */
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

}
