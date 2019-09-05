package com.github.heaweavy.common.components.webdemo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * Created by Rogers on 15-3-25.
 */

@Configuration
public class MybatisConfig {

    @Bean(name = "dataSource")
    public DataSource getDataSource(){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "mapperScannerConfigure")
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configure = new MapperScannerConfigurer();
        //configure.setBasePackage("com.github.heaweavy.common.components.datasource.admin.mapper; com.github.heaweavy.common.components.datasource.school.mapper");
        configure.setBasePackage("com.github.heaweavy.common.components.datasource");
        return configure;
    }
}
