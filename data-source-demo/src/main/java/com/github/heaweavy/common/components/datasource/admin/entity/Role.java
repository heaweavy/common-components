package com.schoolguard.common.admin.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rogers on 15-3-20.
 */
public class Role {
    //超级管理员
    public static final String ADMIN = "ADMIN";
    //普通管理用户
    public static final String USER = "USER";
    //代理商
    public static final String AGENCY = "AGENCY";

    public static Map<String, String> getRoles(){
        Map<String, String> roles = new HashMap<>();
        roles.put(ADMIN, "超级管理员");
        roles.put(USER, "管理员");
        roles.put(AGENCY, "代理商");
        return roles;
    }

}
