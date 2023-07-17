package com.fw.Handler.exception;

import com.fw.entity.ResponseResult;
import com.fw.enums.AppHttpCodeEnum;
import com.fw.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//用于处理Controller层出现的异常
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //用于处理出现的SystemException异常
    @ExceptionHandler(SystemException.class)
    public ResponseResult SystemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常!",e);

        //封装异常对象返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常!{}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
