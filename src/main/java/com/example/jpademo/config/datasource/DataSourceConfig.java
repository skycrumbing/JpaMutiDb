package com.example.jpademo.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: DataSourceConfig
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/8 9:16
 * @Version: 1.0
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    public final static String WRITE_DATASOURCE_KEY = "masterDataSource";
    public final static String READ_DATASOURCE_KEY = "slaveDataSource";

    @Bean
    public DataSource routingDataSource(
            @Qualifier(WRITE_DATASOURCE_KEY) DataSource masterDataSource,
            @Qualifier(READ_DATASOURCE_KEY) DataSource slaveDataSource
    ) throws Exception {
        Map<Object, Object> targetDataSources = new HashMap();
        targetDataSources.put(WRITE_DATASOURCE_KEY, masterDataSource);
        targetDataSources.put(READ_DATASOURCE_KEY, slaveDataSource);
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

}
