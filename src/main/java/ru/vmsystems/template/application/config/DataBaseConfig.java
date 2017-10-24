package ru.vmsystems.template.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

//
//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.Database;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
@Profile("dev")
//@EnableCaching
@Configuration
@ImportResource({"file:database-config.xml"})
public class DataBaseConfig {
//
////    @Bean
////    public CacheManager cacheManager() {
////        return new ConcurrentMapCacheManager("users");
////    }
//
////    @Bean(destroyMethod = "close")
////    public BasicDataSource datasource() {
////        BasicDataSource datasource = new BasicDataSource();
////
////        datasource.setDriverClassName("org.h2.Driver");
////        datasource.setUrl("jdbc:h2:file:./systems/db");
////        datasource.setPoolPreparedStatements(true);
////
////        return datasource;
////    }
//
//    @Bean(destroyMethod = "close")
//    public BasicDataSource datasource() {
//        BasicDataSource datasource = new BasicDataSource();
//
//        datasource.setDriverClassName("org.postgresql.Driver");
//        datasource.setUrl("jdbc:postgresql://172.23.16.50:5432/med");
//        datasource.setUsername("dukh");
//        datasource.setPassword("dukh");
//        datasource.setPoolPreparedStatements(true);
//
//        return datasource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactory.setDataSource(datasource());
//        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
//        return entityManagerFactory;
//    }
//
//    @Bean
//    public HibernateJpaVendorAdapter jpaVendorAdapter() {
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
//        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL9Dialect");
//        jpaVendorAdapter.setShowSql(true);
//        return jpaVendorAdapter;
//    }
//
////    @Bean
////    public JpaTransactionManager jpaTransactionalManager() {
////        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
////        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
////        return jpaTransactionManager;
////    }
}
