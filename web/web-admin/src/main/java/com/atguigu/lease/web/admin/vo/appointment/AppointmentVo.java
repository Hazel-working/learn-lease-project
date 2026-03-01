package com.atguigu.lease.web.admin.vo.appointment;

import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.lease.model.entity.ViewAppointment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "预约看房信息")
public class AppointmentVo extends ViewAppointment {
    //继承ViewAppointment属性，VO对象与实体对象字段高度重合
    //数据安全：只返回前端需要的数据
    @Schema(description = "预约公寓信息")
    private ApartmentInfo apartmentInfo;

}
