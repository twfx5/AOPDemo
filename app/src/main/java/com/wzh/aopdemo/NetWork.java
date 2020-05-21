package com.wzh.aopdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记切点
 */
// 如果不清楚怎么写，点进override看看
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) // RUNTIME运行时， SOURCE资源， CLASS编译时，如butterknief
public @interface NetWork {
    // int value(); int类型的参数 @NetWork(3)
    // String value(); String类型的参数，如处理权限 @NetWork("123")
}
