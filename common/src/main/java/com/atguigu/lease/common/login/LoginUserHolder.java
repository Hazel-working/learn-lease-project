package com.atguigu.lease.common.login;

public class LoginUserHolder {
    // ThreadLocal线程本地变量，每个使用它的线程拥有独立的变量副本，互不干扰
    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    //将当前登录的用户信息保存至threadLocal线程本地变量中
    public static void setLoginUser(LoginUser loginUser) {
        threadLocal.set(loginUser);
    }

    //从当前threadLocal线程本地变量中获取当前登录用户信息
    public static LoginUser getLoginUser() {
        return threadLocal.get();
    }

    //清空threadLocal线程本地变量的数据，防止内存泄漏
    public static void clear() {
        threadLocal.remove();
    }
}