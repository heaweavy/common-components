package com.github.heaweavy.common.components.common.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;

/**
 * Created by caimb on 2017/3/12.
 */
@Component
public class DynamicRegister{

    private ConfigurableApplicationContext configurableApplicationContext;
    private DefaultListableBeanFactory defaultListableBeanFactory;

    private static DynamicRegister register;
    private DynamicRegister() {
    }

    public void register(String beanName,String beanClassName) {
        Assert.hasText(beanName, "Bean name must not be empty");
        Assert.hasText(beanClassName, "Bean Class name must not be empty");
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(beanClassName);
        defaultListableBeanFactory.registerBeanDefinition(beanName,
                beanDefinitionBuilder.getRawBeanDefinition());
    }
    public void register(String beanName, Class<?> beanClass) {
        Assert.hasText(beanName, "Bean name must not be empty");
        Assert.notNull(beanClass, "Bean Class must not be empty");
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(beanClass);
        defaultListableBeanFactory.registerBeanDefinition(beanName,
                beanDefinitionBuilder.getRawBeanDefinition());
    }

    public static DynamicRegister instance() {
        if (register == null) {
            synchronized (DynamicRegister.class) {
                register = new DynamicRegister();
            }
        }
        return register;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext
                .getBeanFactory();
    }
}
