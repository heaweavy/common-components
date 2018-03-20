package com.github.heaweavy.common.components.mysql_test.config;

import com.dxtech.common.component.common.helper.ThreadUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ExecutorService;

/**
 * @author caimb
 * @date Created in 2017/12/4 21:27
 * @modefier
 */
@Component
public class AppConfig {

    @Bean
    public ExecutorService getExecutorService() {
        return ThreadUtils.getExecutorService();
    }

    @Bean
    public Random getRandom() {
        return new Random( System.currentTimeMillis() );
    }

}
