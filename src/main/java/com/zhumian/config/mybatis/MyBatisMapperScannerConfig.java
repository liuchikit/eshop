package com.zhumian.config.mybatis;


import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * ${DESCRIPTION}
 * Created by hanqunfeng on 2016/12/19 17:47.
 */

@Configuration
//必须在MyBatisConfig注册后再加载MapperScannerConfigurer，否则会报错
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.zhumian.mapper");

        Properties properties = new Properties();
        properties.setProperty("mappers", "com.zhumian.config.mybatis.BaseMapper");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        properties.setProperty("logImpl","STDOUT_LOGGING");

        mapperScannerConfigurer.setProperties(properties);

        return mapperScannerConfigurer;
    }
}
