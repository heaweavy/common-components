package com.github.heaweavy.common.components.webserver2.admin;

import com.github.heaweavy.common.components.webserver2.BaseApp;
import com.github.heaweavy.common.components.webserver2.RestApiException;
import com.github.heaweavy.common.components.webserver2.domain.admin.User;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * @author Roma
 * @datetime 2016/1/27 - 12:08
 */
public class AdminAPI extends BaseApp {

    private String baseUrl;

    public AdminAPI(String rootUrl, String username, String password) {
        super(rootUrl, username, password);
        this.baseUrl = this.rootUrl.endsWith("/") ? this.rootUrl + "admin"
                : this.rootUrl + "/admin";
    }

    /**
     * 验证管理员（包括代理商）登录
     * @return
     */
    public User validateLogin(String account, String password) throws Exception {

        if (account == null || password == null) {
            throw new IllegalArgumentException("非法参数");
        }

        MultivaluedHashMap<String, String> formParams = new MultivaluedHashMap<>();
        formParams.add("account", account);
        formParams.add("password", password);
        Form form = new Form(formParams);

        Response response = client.target(baseUrl).path("/validateLogin")
                .request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        try {
            return response.readEntity(User.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RestApiException(e);
        } finally {
            response.close();
        }
    }

    public User validateLoginGet(String account, String password) throws Exception {

        if (account == null || password == null) {
            throw new IllegalArgumentException("非法参数");
        }

        MultivaluedHashMap<String, String> formParams = new MultivaluedHashMap<>();
        formParams.add("account", account);
        formParams.add("password", password);
        Form form = new Form(formParams);

        Response response = client.target(baseUrl).path("/validateLogin/{account}/{password}")
                .resolveTemplate("account", account)
                .resolveTemplate("password", password)
                .request().get();
        try {
            return response.readEntity(User.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RestApiException(e);
        } finally {
            response.close();
        }
    }


    public User validateLoginPostEntity(User example) throws Exception {

        if (example == null || example.getAccount() == null || example.getPassword() == null) {
            throw new IllegalArgumentException("非法参数");
        }


        Response response = client.target(baseUrl).path("/validateLogin/post/entity")
                .request().post(Entity.entity(example, MediaType.APPLICATION_JSON));

        try {
            return response.readEntity(User.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RestApiException(e);
        } finally {
            response.close();
        }
    }

}
