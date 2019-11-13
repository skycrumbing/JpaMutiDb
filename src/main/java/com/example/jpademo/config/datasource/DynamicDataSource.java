package com.example.jpademo.config.datasource;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;

/**
 * @ClassName: DynamicDataSource
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/11 14:46
 * @Version: 1.0
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        //可以做一个简单的负载均衡策略
        String lookupKey = DynamicDataSourceHolder.getDataSource();
        log.info("------------lookupKey---------" + lookupKey);
        return lookupKey;
    }

}