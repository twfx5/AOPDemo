package com.wzh.aopdemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 处理切点
 */
@Aspect
public class SectionAspect {

    /**
     * 找到处理的切点
     *  * *(..)  可以处理所有的方法
     */
    @Pointcut("execution(@com.wzh.aopdemo.NetWork * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 处理切面
     */
    @Around("checkNetBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        // 做埋点  日志上传  权限检测（我写的，RxPermission , easyPermission） 网络检测  登录判断  性能检测（trace view）
        // 网络检测
        // 1.获取 CheckNet 注解  NDK  图片压缩  C++ 调用Java 方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        NetWork checkNet = signature.getMethod().getAnnotation(NetWork.class);
        if (checkNet != null) {
            // 2.判断有没有网络  怎么样获取 context?
            Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类
            Context context = getContext(object);
            if (context != null) {
                if (!NetWorkUtils.isNetWorkAvailable(context)) {
                    // 3.没有网络不要往下执行
                    Toast.makeText(context,"请检查您的网络",Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        }
        Log.e("SectionAspect", "处理切面");
        return joinPoint.proceed();
    }

    /**
     * 通过对象获取上下文
     */
    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }
}
