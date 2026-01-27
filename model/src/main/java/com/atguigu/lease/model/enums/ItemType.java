package com.atguigu.lease.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;


public enum ItemType implements BaseEnum {
    // 枚举类型，定义如下两个实例（两种类型） 实例就是对象，对象就是实例

    APARTMENT(1, "公寓"),

    ROOM(2, "房间");


    @EnumValue // mbp提供，前端请求时将code属性值与枚举类实例相互映射
    @JsonValue // Jackson便可将controller方法的返回ItemType对象于code属性之间的互相映射。
    private Integer code; // 编码
    private String name;  // 文字说明

    @Override
    public Integer getCode() {
        return this.code;
    }


    @Override
    public String getName() {
        return name;
    }

    ItemType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
