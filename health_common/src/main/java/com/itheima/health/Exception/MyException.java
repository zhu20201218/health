package com.itheima.health.Exception;

/**
 *
 * 自定义异常的作用：
 *   1.区分是由系统抛出还是程序员抛出
 *   2.可以封装友好提示
 *   3.终止不符合业务逻辑的代码
 *
 *
 */
public class MyException extends RuntimeException {
     public MyException(String msg){
         super(msg);
     }
}
