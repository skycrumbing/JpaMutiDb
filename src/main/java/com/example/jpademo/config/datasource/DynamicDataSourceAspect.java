package com.example.jpademo.config.datasource;

import javassist.bytecode.SignatureAttribute;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName: DynamicDataSourceAspect
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/11 14:50
 * @Version: 1.0
 */
@Aspect
@Component
@Order(1)
public class DynamicDataSourceAspect {
    @Around("execution(public * com.example.jpademo.service..*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(TargetDataSource.class)) {
            String targetDataSource = targetMethod.getAnnotation(TargetDataSource.class).dataSource();
            System.out.println("----------数据源是:" + targetDataSource + "------");
            DynamicDataSourceHolder.setDataSource(targetDataSource);
        }
        Object result = pjp.proceed();//执行方法
        DynamicDataSourceHolder.clearDataSource();
        return result;
    }

//    @Pointcut("@annotation(com.example.jpademo.config.datasource.TargetDataSource)")
//    public void annotationPointCut() {
//        // APSECT POINT CUT FUNCTION
//    }
//    @Before(value = "annotationPointCut()&&@annotation(datasource)")
//    public void beforeSwitchDS(JoinPoint point, TargetDataSource datasource) {
//        DynamicDataSourceHolder.setDataSource(datasource.dataSource());
//    }
//
//    @After(value = "annotationPointCut()")
//    public void afterSwitchDS(JoinPoint point) {
//        DynamicDataSourceHolder.clearDataSource();
//    }
}
