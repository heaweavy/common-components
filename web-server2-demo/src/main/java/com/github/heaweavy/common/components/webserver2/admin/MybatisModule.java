package com.github.heaweavy.common.components.webserver2.admin;

import org.mybatis.guice.XMLMyBatisModule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Rogers on 15-3-3.
 */
public class MybatisModule extends XMLMyBatisModule {
    @Override
    public void initialize(){
        setEnvironmentId("production");
        setClassPathResource("mybatis-config.xml");

    }

    public Properties getMybatisProperties() {
        Properties properties = new Properties();
        try{
            properties.load(MybatisModule.class.getClassLoader().getResourceAsStream("mybatis.properties"));
        }catch (FileNotFoundException exception){
            throw new RuntimeException("Load properties failed: " + exception.getMessage());
        } catch (IOException exception){
            throw new RuntimeException("Load properties failed: " + exception.getMessage());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return properties;
    }

}
