package com.atguigu.lease.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import com.atguigu.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Schema(description = "标签信息表")
@TableName(value = "label_info")
@Data
public class LabelInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;
    // 定义了一个静态常量serialVersionUID，值为1L。这是Java序列化机制中用于标识类版本的唯一ID，
    // 当对象被序列化和反序列化时，JVM会检查这个ID来确保发送方和接收方的类版本兼容，防止因类结构变化导致的序列化异常。

    @Schema(description = "类型")
    @TableField(value = "type")
    private ItemType type;
    // type在实体类LabeInfo中为枚举类型，表示标签类型。数据库、前后端交互所传递的数据中type字段为数字

    @Schema(description = "标签名称")
    @TableField(value = "name")
    private String name;


}