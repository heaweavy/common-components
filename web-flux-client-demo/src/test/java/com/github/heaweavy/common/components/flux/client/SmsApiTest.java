package com.github.heaweavy.common.components.flux.client;

import com.github.heaweavy.common.components.shared.entity.User;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * @author caimb
 * @date Created in 2017/9/15 16:55
 * @modefier
 */
public class SmsApiTest {
    private UserApi userApi;
    @Before
    public void init() {
        userApi = new UserApi( "http://localhost:9091" );
    }

    @Test
    public void getUsers() throws ExecutionException, InterruptedException {
        userApi.getUsers();
    }

    @Test
    public void getUser() {
        User user = userApi.getUser( "1" );
        assert user != null;
    }
}
