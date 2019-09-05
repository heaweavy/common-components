package com.github.heaweavy.common.components.webserver2.admin.service.impl;

import com.github.heaweavy.common.components.common.helper.Password;
import com.github.heaweavy.common.components.datasource.admin.entity.User;
import com.github.heaweavy.common.components.datasource.admin.mapper.UserMapper;
import com.github.heaweavy.common.components.webserver2.admin.service.UserService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Roma
 * @datetime 2016/1/27 - 11:36
 */
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private UserMapper userMapper;


    public User login(String account, String password) {
        User user = userMapper.getUserByAccount(account);
        if (user != null) {
            try {
                boolean valid = Password.check(password, user.getPassword());
                if (valid){
                    return user;
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        logger.debug("User password validation failed!!");
        return null;
    }

}
