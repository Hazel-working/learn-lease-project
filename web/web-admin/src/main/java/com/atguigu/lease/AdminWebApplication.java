package com.atguigu.lease;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//启动后台管理系统
@SpringBootApplication
@EnableScheduling // SpringBoot内置,开启定时任务(如检查租约是否到期)
@MapperScan("com.atguigu.lease.web.admin.mapper")
public class AdminWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminWebApplication.class, args);
    }
}
