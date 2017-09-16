package com.github.heaweavy.common.components.webserver2.admin.service;


import com.github.heaweavy.common.components.datasource.admin.entity.User;

import java.util.List;

/**
 * @author Roma
 * @datetime 2016/1/27 - 11:35
 */
public interface UserService {

    public User login(String account, String password);

}
