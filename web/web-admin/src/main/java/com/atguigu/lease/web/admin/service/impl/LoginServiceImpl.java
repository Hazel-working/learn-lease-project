package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.utils.JwtUtil;
import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import com.atguigu.lease.web.admin.service.LoginService;
import com.atguigu.lease.web.admin.vo.login.CaptchaVo;
import com.atguigu.lease.web.admin.vo.login.LoginVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * 获取图形验证码
     * @return CaptchaVo 包含验证码图片base64编码和key
     */

    @Override
    public CaptchaVo getCaptcha() {

        //使用SpecCaptcha 生成4位验证码（图片）对象
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);

        //使用test()方法获取验证码文本，并转换为小写
        String code = specCaptcha.text().toLowerCase();
        //本项目Redis中key命名规范：项目名:功能模块名:其他。"admin:login:"
        String key = RedisConstant.ADMIN_LOGIN_PREFIX +UUID.randomUUID();

        //RedisTemplate中，String类型的键值对 需要使用opsForValue()
        //并将验证码保存set()到Redis中, 并设置过期时间ttl(60秒)
        stringRedisTemplate.opsForValue().set(key, code, RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);

        return new CaptchaVo(specCaptcha.toBase64(), key);
    }

    /**
     * 登录
     * @param loginVo 登录信息
     * @return 登录成功返回token
     */
    @Override
    public String login(LoginVo loginVo) {
        //1.判断是否输入了验证码
        if (loginVo.getCaptchaCode() == null) {
            //返回给前端：304未输入验证码 异常
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }
        //2.校验验证码
        //通过传入的loginVo中key 获取Redis中保存的验证码
        String code = stringRedisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        //判断验证码是否为空
        if (code == null) {
            //返回给前端：303验证码已过期 异常
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }
        //判断传入的验证码是否与Redis中保存的验证码code一致
        if (!code.equals(loginVo.getCaptchaCode())) {
            //返回给前端：302验证码错误 异常
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }

        //3.校验用户是否存在 根据用户名查询（含密码）
        SystemUser systemUser = systemUserMapper.selectOneByUsername(loginVo.getUsername());

        if (systemUser == null) {
            //返回给前端：306账号不存在 异常
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }

        //4. 判断用户状态 是否被禁用
        if (systemUser.getStatus() == BaseStatus.DISABLE) {
            //返回给前端：308该用户已被禁用 异常
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }

        //5. 判断密码是否一致 将用户输入的密码进行MD5加密 再与数据库中的密码进行比较
        if (!systemUser.getPassword().equals(DigestUtils.md5DigestAsHex(loginVo.getPassword().getBytes()))) {
            //返回给前端：307用户名或密码错误 异常
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }

        //6. 为登录成功的用户生成jwt令牌并返回
        return JwtUtil.createToken(systemUser.getId(), systemUser.getUsername());
    }

    /**
     * 获取登录用户信息
     * @param userId 用户id
     * @return 登录用户信息
     */
    @Override
    public SystemUserInfoVo getLoginUserInfo(Long userId) {
        SystemUser systemUser = systemUserMapper.selectById(userId);

        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setName(systemUser.getName());
        systemUserInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return systemUserInfoVo;
    }

}
