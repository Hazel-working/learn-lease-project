package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT) // insert时自动填充
    @JsonIgnore // 序列化时会忽略此字段
    private Date createTime;

    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE) // update时自动更新
    @JsonIgnore
    private Date updateTime;

    @Schema(description = "逻辑删除")
    @TableField("is_deleted")
    @JsonIgnore
    @TableLogic //只查询is_deleted=0的数据
    private Byte isDeleted;

}