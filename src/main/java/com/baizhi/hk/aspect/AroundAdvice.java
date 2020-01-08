package com.baizhi.hk.aspect;


import com.baizhi.hk.entity.EntityLog;
import com.baizhi.hk.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Component
@Aspect
@Slf4j
public class AroundAdvice {
    @Autowired
    HttpServletRequest request;
    @Autowired
    LogService service;

    @Around(value = "pt()")
    public Object AroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed;
        //获取执行操作的用户
        String user = (String) request.getSession().getAttribute("admin");
        //获取执行时间
        Date date = new Date();
        //获取方法对象
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        //获取注解对象
        Log annotation = method.getAnnotation(Log.class);
        String name = annotation.name();
        //执行结果
        boolean flag = false;

        try {
            proceed = proceedingJoinPoint.proceed();
            flag = true;
            return proceed;
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            log.info("管理员--->{},日期--->{},执行的操作--->{},执行的结果--->{}", user, date, name, flag);
            EntityLog entityLog = new EntityLog(UUID.randomUUID().toString(), name, user, date, flag);
            service.add(entityLog);
        }

    }


    @Pointcut(value = "@annotation(com.baizhi.hk.aspect.Log)")

    public void pt(){

    }
}
