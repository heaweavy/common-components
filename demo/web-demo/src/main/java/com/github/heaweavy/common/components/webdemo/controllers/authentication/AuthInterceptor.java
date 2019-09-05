package com.github.heaweavy.common.components.webdemo.controllers.authentication;

import com.github.heaweavy.common.components.datasource.admin.entity.Role;
import com.github.heaweavy.common.components.webdemo.config.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * Authentication and authorization
 *
 * Created by Rogers on 15-3-23.
 */
@Component("authInterceptor")
public class AuthInterceptor extends HandlerInterceptorAdapter{
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(AppConstants.USER_SESSION_KEY) == null) {
            logger.info("user not logged in");
            response.sendRedirect("/login");
            return false;
        }
        logger.debug("user already logged in");

        // Authorization test:
        Credential credential = (Credential) session.getAttribute(AppConstants.USER_SESSION_KEY);

        //超级管理员可以访问所有页面
        if(credential.getRole().equals(Role.ADMIN)){
            return true;
        }

        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            if(method.getMethod().isAnnotationPresent(RolesAllowed.class)){
                String[] roles = method.getMethodAnnotation(RolesAllowed.class).value();
                if(Arrays.asList(roles).contains(credential.getRole())){
                    return true;
                } else {
                    response.sendRedirect("/index");
                    return false;
                }
            } else {
                // 未用RolesAllowed标注的默认可以访问
                return true;
            }
        } else{
            throw new RuntimeException("handler is not instance of HandlerMethod: " + handler.toString());
        }

    }


}
