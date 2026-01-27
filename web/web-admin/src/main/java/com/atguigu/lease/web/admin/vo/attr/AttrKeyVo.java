package com.atguigu.lease.web.admin.vo.attr;

import com.atguigu.lease.model.entity.AttrKey;
import com.atguigu.lease.model.entity.AttrValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Data
public class AttrKeyVo extends AttrKey {
    // vo 用于定义接口的返回值类型，或者请求参数的类型。
    // 继承AttrKey类，拥有属性名称的全部字段；定义添加属性valueList，类型是List<AttrValue>，即属性值列表

    @Schema(description = "属性value列表")
    private List<AttrValue> attrValueList;
}
