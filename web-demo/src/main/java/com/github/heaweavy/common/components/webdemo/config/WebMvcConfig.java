package com.schoolguard.commander.config;

import com.schoolguard.commander.controllers.authentication.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

/**
 * Created by Rogers on 15-3-25.
 */

@Configuration
//@EnableWebMvc   //加上这行会关闭spring boot默认的mvc配置 see boot chapter 26.1.1
public class WebMvcConfig extends WebMvcConfigurerAdapter{
    @Autowired
    public AuthInterceptor authInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**").
                addResourceLocations("classpath:/static/");
    }

    @Bean
    public MappedInterceptor getMappedInterceptor(){
        MappedInterceptor interceptor = new MappedInterceptor(null,
                new String[]{"/login", "/captcha", "/static/**"},
                authInterceptor);
        return interceptor;
    }

    /**
     * Thymeleaf需要配置engine， templateResolver和ViewResolver
     *
     * @return
     */
    /*
    @Bean
    public ServletContextTemplateResolver getTemplateResolver(){
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
//        templateResolver.setCacheable(false);   //production mode可以打开缓存
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine getThymeleafEngine(){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addTemplateResolver(getTemplateResolver());
        return engine;
    }

    @Bean
    public ThymeleafViewResolver getViewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(getThymeleafEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setOrder(1);   //optional, 可在templateResolver中设置
        viewResolver.setViewNames(new String[]{"*.html", "*.xhtml"});   //optional, 可在templateResolver中设置
        return viewResolver;
    }
    */

    @Bean
    public HandlerMapping getHandlerMapping(){
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setInterceptors(new Object[]{getMappedInterceptor()});
        return mapping;
    }


}
