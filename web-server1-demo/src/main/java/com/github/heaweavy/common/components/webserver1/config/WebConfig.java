package com.github.heaweavy.common.components.webserver1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Rogers on 15-3-25.
 */

@Configuration
//@EnableWebMvc   //加上这行会关闭spring boot默认的mvc配置 see boot chapter 26.1.1
public class WebConfig extends WebMvcConfigurerAdapter {
}
