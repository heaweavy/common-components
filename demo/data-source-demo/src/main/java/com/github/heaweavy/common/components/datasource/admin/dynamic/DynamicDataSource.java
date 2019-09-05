package com.github.heaweavy.common.components.datasource.admin.dynamic;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.org.apache.bcel.internal.util.ClassLoader;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
//import org.springframework.context.annotation.Bean;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * Created by caimb on 2017/2/8.
 */
public class DynamicDataSource {
    private SqlSessionFactory sqlSessionFactory;
    private ComboPooledDataSource dataSource;
    public DynamicDataSource() throws Exception {
        dataSource = new ComboPooledDataSource();
    };
    public void init() throws Exception{
        DynamicSqlSessionFactoryBean factoryBean = new DynamicSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        ClassPathResource classPathResource = new ClassPathResource("mybatis-dynamic-config.xml");
        InputStream is = classPathResource.getInputStream(); //DynamicDataSource.class.getResourceAsStream("mybatis-dynamic-config.xml");
        InputStreamReader isr = new InputStreamReader(is,"UTF-8");
        BufferedReader bufferedReader = new BufferedReader(isr);
        String lineString = null;
        StringBuilder stringbuilder = new StringBuilder();
        while ((lineString = bufferedReader.readLine()) != null){
            if(lineString.contains("${jdbc.url}")){
                lineString = lineString.replace("${jdbc.url}",this.dataSource.getJdbcUrl());
            }
            else if(lineString.contains("${jdbc.user}")){
                lineString = lineString.replace("${jdbc.user",this.dataSource.getUser());
            }
            else if(lineString.contains("${jdbc.password}")){
                lineString = lineString.replace("${jdbc.password}",this.dataSource.getPassword());
            }
            else if(lineString.contains("${jdbc.driverClass}")){
                lineString = lineString.replace("${jdbc.driverClass}",this.dataSource.getDriverClass());
            }
            stringbuilder.append(lineString);
        }
        File tmpFile = File.createTempFile("createTempFile",".tmp");
        BufferedWriter out = new BufferedWriter(new FileWriter(tmpFile));
        out.write(stringbuilder.toString());
        out.close();
        factoryBean.setConfigLocationInputStream(new FileInputStream(tmpFile));
        this.sqlSessionFactory = factoryBean.getObject();
        tmpFile.delete();
    }
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public ComboPooledDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
