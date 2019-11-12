package com.example.jpademo.config.datasource;

import java.lang.annotation.*;

/**
 * @ClassName: TargetDataSource
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/11 14:44
 * @Version: 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String dataSource() default "";//数据源
}
