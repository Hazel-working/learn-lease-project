package com.atguigu.lease.common.exception;

import com.atguigu.lease.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//全局异常处理器
@ControllerAdvice // @RestControllerAdvice = @ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //拦截所有类型的异常
    @ResponseBody //可以将方法返回值作为异常响应数据返回给前端
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }


    //增加自定义异常类的处理逻辑
    @ExceptionHandler(LeaseException.class)
    @ResponseBody
    public Result error(LeaseException e){
        e.printStackTrace();
        return Result.fail(e.getCode(), e.getMessage());
    }
}