package com.github.heaweavy.common.components.webdemo.config;

import com.github.heaweavy.common.components.common.bean.DynamicRegister;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by caimb on 2017/3/13.
 */
@Component
public class DynamicInitializer implements ApplicationContextAware {

    private static DynamicRegister register;
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DynamicInitializer.applicationContext = applicationContext;
        DynamicInitializer.register = DynamicRegister.instance();
        register.setApplicationContext(applicationContext);

        register.register("Pager","com.github.heaweavy.common.components.common.helper.Pager");
//        register.register("dynamicAction","com.github.heaweavy.common.components.common.bean.DynamicAction");
    }

    public static ApplicationContext getApplicationContext() {
        return DynamicInitializer.applicationContext;
    }
}
