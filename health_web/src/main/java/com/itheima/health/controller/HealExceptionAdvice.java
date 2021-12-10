package com.itheima.health.controller;

import com.itheima.health.Exception.MyException;
import com.itheima.health.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * before
 * after finally
 * after returning return
 * around
 * RestControllerAdvice就相当于after throwing，捕捉的是controller抛出的异常
 *
 * 日志的打印:
 * info: 打印流程性的东西
 * debug: 记录关键的数据key 订单id
 * error: 打印异常，代替e.printStackTrace, System.out.println
 *
 *  try{} catch(异常的类型)
 *
 */
@RestControllerAdvice
public class HealExceptionAdvice {

    public static final Logger log = LoggerFactory.getLogger(HealExceptionAdvice.class);


    //该注解表示 捕捉哪种异常
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException e){
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("发生错误",e);
        return new Result(false,"发生错误");
    }
}
