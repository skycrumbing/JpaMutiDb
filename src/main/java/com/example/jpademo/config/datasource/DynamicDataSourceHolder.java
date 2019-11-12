package com.example.jpademo.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName: DynamicDataSourceHolder
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/11 14:45
 * @Version: 1.0
 */
public class DynamicDataSourceHolder {
    //使用ThreadLocal把数据源与当前线程绑定
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();
    public static void setDataSource(String dataSourceName) {
        dataSources.set(dataSourceName);
    }
    public static String getDataSource() {
        return (String) dataSources.get();
    }
    public static void clearDataSource() {
        dataSources.remove();
    }
}