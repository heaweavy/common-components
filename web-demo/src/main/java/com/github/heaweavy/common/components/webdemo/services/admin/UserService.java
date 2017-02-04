package com.github.heaweavy.common.components.webdemo.services.admin;


import com.github.heaweavy.common.components.datasource.admin.entity.User;
import com.github.heaweavy.common.components.webdemo.helper.Pager;

import java.util.List;

/**
 * Created by Rogers on 15-3-20.
 */
public interface UserService {
    public User createUser(User user) throws Exception;

    public void updateUser(User user) throws Exception;

    public void changePassword(Integer userId, String oldPassword, String newPassword) throws Exception;

    public void deleteUser(int userId) throws Exception;

    public User getUserById(int userId) throws Exception;

    public User getUserByMobile(String mobile) throws Exception;

    public User getUserByAccount(String account) throws Exception;

    public boolean validateUserByAccount(String account, String password) throws Exception;

    public boolean validateUserByMobile(String mobile, String password) throws Exception;

    public List<User> getAllUsers(Pager pager);

    public void updateUserPassword(User user, String password, String newpassword, String newpasswordsecond) throws Exception;

    /**
     * 更新用户的姓名 账号 email
     * @param user
     */
    public void updateUserNameOrMobileOrEmail(User user);

    /**
     * 变更用户状态
     * @param userId
     * @throws Exception
     */
    public void changeUserStatus(Integer userId) throws Exception;
}
