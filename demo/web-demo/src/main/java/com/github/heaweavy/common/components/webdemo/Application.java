package com.github.heaweavy.common.components.webdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Rogers on 15-3-25.
 */

@SpringBootApplication
@EnableWebMvc
// extends SpringBootServletInitializer 可以配置servlet, 以跑在servlet容器中
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
