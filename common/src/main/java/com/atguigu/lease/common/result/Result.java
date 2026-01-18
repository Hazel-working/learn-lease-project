package com.atguigu.lease.common.result;

import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
public class Result<T> {

    //返回码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data;

    public Result() {
    }

    private static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null)
            result.setData(data);
        return result;
    }//如果数据不为空，将数据封装到result对象中并返回

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);//调用私有方法封装数据body
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }


    public static <T> Result<T> ok(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
        //将传递的数据封装到result的data中 并设code: 200, message:S UCCESS 返回result
    }

    public static <T> Result<T> ok() {
        return Result.ok(null);//返回result，data为null，code：200，message：SUCCESS
    }

    public static <T> Result<T> fail() {
        return build(null, ResultCodeEnum.FAIL);
    }
}
