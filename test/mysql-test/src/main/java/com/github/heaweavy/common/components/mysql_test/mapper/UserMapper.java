package com.github.heaweavy.common.components.mysql_test.mapper;

import com.github.heaweavy.common.components.mysql_test.entity.*;


import java.util.List;
import java.util.Map;

/**
 * Created by Rogers on 15-3-20.
 */

public interface UserMapper {

    public int createUser(User user);

    public void updateUser(User user);

    public void deleteUser(int id);

    public User getUserById(int id);

    public User getUserByMobile(String mobile);

    public User getUserByAccount(String account);

    public List<User> queryUsers(Map<String, Object> param);

    public Integer countUsers(Map<String, Object> param);
}
