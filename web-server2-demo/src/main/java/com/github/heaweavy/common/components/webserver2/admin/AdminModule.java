package com.github.heaweavy.common.components.webserver2.admin;

import com.github.heaweavy.common.components.webserver2.admin.service.UserService;
import com.github.heaweavy.common.components.webserver2.admin.service.impl.UserServiceImpl;
import com.google.inject.AbstractModule;
/**
 * @author Roma
 * @datetime 2016/1/27 - 11:27
 */
public class AdminModule extends AbstractModule {

    @Override
    public void configure(){

        bind(UserService.class).to(UserServiceImpl.class);
    }

}
