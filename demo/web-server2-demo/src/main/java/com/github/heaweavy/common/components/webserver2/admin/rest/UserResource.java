package com.github.heaweavy.common.components.webserver2.admin.rest;

import com.github.heaweavy.common.components.datasource.admin.entity.User;
import com.github.heaweavy.common.components.webserver2.admin.service.UserService;
import com.github.heaweavy.common.components.webserver2.helper.ResponseMessage;
import com.github.heaweavy.common.components.webserver2.helper.Roles;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Roma
 * @datetime 2016/1/27 - 11:26
 */
@Path("/admin")
public class UserResource {
    private static Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserService userService;

    /**
     * 管理员 验证登录
     * @param account
     * @param password
     * @return
     */
    @RolesAllowed(Roles.APP)
    @POST
    @Path("/validateLogin")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateLogin(@FormParam("account") String account,
                                  @FormParam("password") String password) {
        User user = userService.login(account, password);
        if (user != null) { // 登录成功
            if (!user.getEnabled()) { // 用户被禁用
                logger.info("账号被禁用");
                return Response.status(Response.Status.FORBIDDEN)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(new ResponseMessage(411, "账号被禁用"))
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(user)
                    .build();
        } else {
            logger.info("账号或密码错误");
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ResponseMessage(410, "账号或密码错误"))
                    .build();
        }
    }
    @RolesAllowed({Roles.APP})
    @GET
    @Path("/validateLogin/{account}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGuardian(@PathParam("account") @NotNull String account,@PathParam("password") @NotNull String password) {
        User user = userService.login(account, password);
        if (user != null) { // 登录成功
            if (!user.getEnabled()) { // 用户被禁用
                logger.info("账号被禁用");
                return Response.status(Response.Status.FORBIDDEN)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(new ResponseMessage(411, "账号被禁用"))
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(user)
                    .build();
        } else {
            logger.info("账号或密码错误");
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ResponseMessage(410, "账号或密码错误"))
                    .build();
        }
    }

    @RolesAllowed({Roles.APP})
    @POST
    @Path("/validateLogin/post/entity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGuardian(@Valid User example) {
        User user = userService.login(example.getAccount(), example.getPassword());
        if (user != null) { // 登录成功
            if (!user.getEnabled()) { // 用户被禁用
                logger.info("账号被禁用");
                return Response.status(Response.Status.FORBIDDEN)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(new ResponseMessage(411, "账号被禁用"))
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(user)
                    .build();
        } else {
            logger.info("账号或密码错误");
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ResponseMessage(410, "账号或密码错误"))
                    .build();
        }

    }

}
