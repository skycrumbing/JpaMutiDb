package com.example.jpademo.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @ClassName: WriteDataSourceConfig
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/11 14:29
 * @Version: 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(value = "com.example.*.repository",
        entityManagerFactoryRef = "myEntityManagerFactoryBean",
        transactionManagerRef = "myTransactionManager")
public class JpaDataSourceConfig {
    @Autowired
    HibernateProperties hibernateProperties;
    @Autowired
    JpaProperties jpaProperties;
    @Autowired
    @Qualifier("routingDataSource")
    private DataSource routingDataSource;
    /**
     * 我们通过LocalContainerEntityManagerFactoryBean来获取EntityManagerFactory实例
     * @return
     */
    @Primary
    @Bean(name = "myEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean myEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings()
        );
        return builder
                .dataSource(routingDataSource)
                .properties(properties)
                .packages("com.example.*.entity") //设置实体类所在位置
                .persistenceUnit("myPersistenceUnit")
                .build();
        //.getObject();//不要在这里直接获取EntityManagerFactory
    }

    @Bean(name = "myEntityManager")
    @Primary
    public EntityManager  myEntityManager(EntityManagerFactoryBuilder builder) {
        return this.myEntityManagerFactoryBean(builder).getObject().createEntityManager();
    }
    /**
     * 配置事物管理器
     * @return
     */
    @Bean(name = "myTransactionManager")
    @Primary
    public PlatformTransactionManager myTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(myEntityManagerFactoryBean(builder).getObject());
    }
}
