package com.github.heaweavy.common.components.webserver2;

import com.github.heaweavy.common.components.webserver2.admin.AdminAPI;
import com.github.heaweavy.common.components.webserver2.domain.admin.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author Roma
 * @datetime 2016/1/27 - 16:46
 */
public class AdminAPITest {
    private AdminAPI api;

    @Before
    public void setupGuardianApi(){
        api = new AdminAPI("http://localhost:8081/api/", "app", "123456");
    }

    @Test
    public void test1() throws Exception {
        User user = api.validateLogin("mtf", "123456");
        System.out.println(user);
    }

    @Test
    public void test2() throws Exception {
        User user = api.validateLoginGet("superadmin", "123456");
        System.out.println(user);
    }
    @Test
    public void test3() throws Exception {
        User example = new User();
        example.setAccount("superadmin");
        example.setPassword("123456");
        User user = api.validateLoginPostEntity(example);
        System.out.println(user);
    }


}
