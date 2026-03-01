package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.web.admin.vo.login.CaptchaVo;
import com.atguigu.lease.web.admin.vo.login.LoginVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {
    /**
     * 获取登录验证码
     * @return 验证码信息
     */
    CaptchaVo getCaptcha();


    /**
     * 登录
     * @param loginVo 登录信息
     * @return 登录成功后返回的token
     */
    String login(LoginVo loginVo);

    /**
     * 获取登录用户信息
     * @param userId 用户id
     * @return 登录用户信息
     */
    SystemUserInfoVo getLoginUserInfo(Long userId);
}
