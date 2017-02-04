package com.github.heaweavy.common.components.webdemo.services.admin.impl;


import com.github.heaweavy.common.components.common.helper.Password;
import com.github.heaweavy.common.components.datasource.admin.entity.User;
import com.github.heaweavy.common.components.datasource.admin.mapper.UserMapper;
import com.github.heaweavy.common.components.webdemo.helper.Pager;
import com.github.heaweavy.common.components.webdemo.services.admin.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Rogers on 15-4-2.
 */
@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public User createUser(User user)throws Exception{
        checkArgument(user != null, "user is null");
        checkNotNull(user.getPassword(), "user password is null");
        user.setPassword(Password.hash(user.getPassword()));
        userMapper.createUser(user);
        return user;
    }

    @Transactional
    public void updateUser(User user) throws Exception{
        checkNotNull(user, "user is null");
        checkNotNull(user.getId(), "user password is null");
        User oldUser = userMapper.getUserById(user.getId());
        if(user.getPassword() == null || user.getPassword().equals("")){
            user.setPassword(oldUser.getPassword());
        }else{
            user.setPassword(Password.hash(user.getPassword()));
        }
        userMapper.updateUser(user);
    }

    @Transactional
    public void changePassword(Integer userId, String oldPassword, String newPassword) throws Exception{
        checkNotNull(userId, "user is null");
        checkNotNull(oldPassword, "old password is null");
        checkNotNull(newPassword, "new password is null");
        User user = userMapper.getUserById(userId);
        checkArgument(Password.check(oldPassword, user.getPassword()), "password dose not match");
        user.setPassword(Password.hash(newPassword));
        userMapper.updateUser(user);
    }

    @Transactional
    public void deleteUser(int userId){
       userMapper.deleteUser(userId);
    }

    @Transactional
    public User getUserById(int userId){
        checkArgument(userId >= 0, "invalid user id");
        return userMapper.getUserById(userId);
    }

    @Transactional
    public User getUserByMobile(String mobile){
        checkArgument(mobile != null, "invalid user mobile");
        return userMapper.getUserByMobile(mobile);
    }

    @Transactional
    public User getUserByAccount(String account){
        checkArgument(account != null, "invalid user account");
        return userMapper.getUserByAccount(account);
    }

    @Transactional
    public boolean validateUserByAccount(String account, String password){
        checkArgument(account != null, "invalid user account");
        checkArgument(password != null, "invalid user password");

        User user = userMapper.getUserByAccount(account);
        if (user == null){
            return false;
        }
        if(!user.getEnabled()) {
            return false;
        }
        try {
            return Password.check(password, user.getPassword());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public boolean validateUserByMobile(String mobile, String password){
        checkArgument(mobile != null, "invalid user mobile");
        checkArgument(password != null, "invalid user password");

        User user = userMapper.getUserByMobile(mobile);
        if (user == null){
            return false;
        }
        if(!user.getEnabled()) {
            return false;
        }

        try {
            return Password.check(password, user.getPassword());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<User> getAllUsers(Pager pager){
        String query = StringUtils.isEmpty(pager.getQuery()) ? "%" : '%' + pager.getQuery() + '%';
        Map<String,Object> param = new HashMap<String,Object>(4);
        param.put("query",query);
        param.put("limit", pager.getSize());
        param.put("offset", (pager.getCurrent() - 1) * pager.getSize());
        pager.setTotal(this.userMapper.countUsers(param));
        return userMapper.queryUsers(param);
    }

    public void updateUserPassword(User user, String password, String newPassword, String newPasswordSecond) throws Exception{
        checkArgument(user != null, "user is null");
        checkArgument(!StringUtils.isEmpty(password) && !StringUtils.isEmpty(newPassword) && !StringUtils.isEmpty(newPasswordSecond),"请正确输入密码");
        checkArgument(Password.check(password, user.getPassword()), "旧密码与原密码不匹配");
        checkArgument(newPassword.equals(newPasswordSecond), "两次新密码输入不相同");
        checkArgument(newPassword.length() > 5,"新密码不能少于6位");
        user.setPassword(Password.hash(newPassword));
        userMapper.updateUser(user);
    }

    /**
     * 更新用户的名称 手机 email
     * @param user
     */
    public void updateUserNameOrMobileOrEmail(User user) {
        checkNotNull(user, "user is null");
        checkNotNull(user.getId(), "user id is null");
        checkArgument(!StringUtils.isEmpty(user.getName()), "用户名称不能为空");
        checkArgument(!StringUtils.isEmpty(user.getMobile()),"用户的手机号码不能为空");
        checkArgument(user.getMobile().length() == 11,"请输入正确的手机号");
        userMapper.updateUser(user);
    }

    public void changeUserStatus(Integer userId) throws Exception {
        User user = this.getUserById(userId);
        user.setEnabled(!user.getEnabled());
        userMapper.updateUser(user);
    }

}
