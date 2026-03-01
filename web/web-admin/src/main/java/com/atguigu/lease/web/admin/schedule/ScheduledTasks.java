package com.atguigu.lease.web.admin.schedule;

import com.atguigu.lease.model.entity.LeaseAgreement;
import com.atguigu.lease.model.enums.LeaseStatus;
import com.atguigu.lease.web.admin.service.LeaseAgreementService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component // 将类交给Spring管理
public class ScheduledTasks {

    @Autowired
    private LeaseAgreementService leaseAgreementService;

    /**
     * 定时任务：检查出租约已到期的租约，并设置为已到期状态
     * 要求检查所有已签约状态的租约
     */
    @Scheduled(cron = "0 0 0 * * *")
    //cron 六位：秒 分 时 日 月 周
    public void checkLeaseStatus() {
        LambdaUpdateWrapper<LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
        Date now = new Date(); //获取当前日期
        updateWrapper.le(LeaseAgreement::getLeaseEndDate, now); //过滤出到期时间小于当前日期的租约（即已过期的租约）
        updateWrapper.in(LeaseAgreement::getStatus, LeaseStatus.SIGNED, LeaseStatus.WITHDRAWING);// 过滤出所有在租状态的租约（已签约或退租待确认）
        updateWrapper.set(LeaseAgreement::getStatus, LeaseStatus.EXPIRED);//根据过滤结果，更新租约状态为到期

        leaseAgreementService.update(updateWrapper);  //调用通用更新方法，根据更新包装器执行更新操作
    }
}
