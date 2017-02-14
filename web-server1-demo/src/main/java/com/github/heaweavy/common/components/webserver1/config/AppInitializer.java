package com.github.heaweavy.common.components.webserver1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Rogers on 15-8-31.
 */
@Component
public class AppInitializer implements ApplicationListener<ContextRefreshedEvent> {
    static Logger logger = LoggerFactory.getLogger(AppInitializer.class);

    //启动时可以使用命令行参数来控制是否初始化某些东西
    @Value("${command.argName}")
    private boolean initArgName;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(!initArgName){
            logger.info("argName not to init!");
            return;
        }
        logger.info("argName init!");
        //ApplicationContext context = event.getApplicationContext();
        //BEAN bean = context.getBean(BEAN.class);

    }

}
