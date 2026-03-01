package com.atguigu.lease.web.admin.controller.lease;


import com.atguigu.lease.common.result.Result;
import com.atguigu.lease.model.entity.ViewAppointment;
import com.atguigu.lease.model.enums.AppointmentStatus;
import com.atguigu.lease.web.admin.mapper.ViewAppointmentMapper;
import com.atguigu.lease.web.admin.service.ViewAppointmentService;
import com.atguigu.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.atguigu.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "预约看房管理")
@RequestMapping("/admin/appointment")
@RestController
public class ViewAppointmentController {
    @Autowired
    private ViewAppointmentService service;


    @Operation(summary = "分页查询预约信息")
    @GetMapping("page")
    public Result<IPage<AppointmentVo>> page(@RequestParam long current, @RequestParam long size, AppointmentQueryVo queryVo) {
        //创建MyBatis-Plus的Page对象,并封装接收到的分页参数。泛型ViewAppointment表示查询的实体类型
        Page<ViewAppointment> page = new Page<>(current, size);
        //调用service层方法查询预约信息,传入分页参数和查询条件。
        //IPage<>是MyBatis-Plus提供的分页结果接口(封装了完整的分页信息)
        IPage<AppointmentVo> result = service.pageAppointment(page, queryVo);
        return Result.ok(result);
    }

    @Operation(summary = "根据id更新预约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam AppointmentStatus status) {
        LambdaUpdateWrapper<ViewAppointment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ViewAppointment::getId, id);
        updateWrapper.set(ViewAppointment::getAppointmentStatus, status);
        service.update(updateWrapper);
        return Result.ok();
    }

}
