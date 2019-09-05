package com.github.heaweavy.common.components.webserver2.helper;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 适用Http Basic认证来对请求做访问限制，以增加部分接口访问安全性。
 *
 * {@link Roles Roles} 里面定义了几种角色（或者叫做接口安全级别）。
 * 开发Resource接口时，可以用{@link PermitAll PermitAll}来表示
 * 某一个接口是公共接口，可以随意访问；可以用{@link DenyAll DenyAll}
 * 来表示接口被禁用，禁止访问；用{@link RolesAllowed RolesAllowed}
 * 来表示该接口只接受特定角色的用户访问.
 *
 * Created by Rogers on 15-3-13.
 */
@Provider
public class SecurityInterceptor implements ContainerRequestFilter{
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());;
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<Object>());;

    @Inject
    @Named("security.accounts")
    private List<String> accountList;

    @Override
    public void filter(ContainerRequestContext requestContext){
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();

        if(method.isAnnotationPresent(PermitAll.class)){
            return;
        } else{
            if(method.isAnnotationPresent(DenyAll.class)){
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //检查authorization http header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //If no authorization information present; block access
            if(authorization == null || authorization.isEmpty())
            {
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }

            //从header 里取编码的username和password
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            //Decode username and password
            String usernameAndPassword = null;
            try {
                usernameAndPassword = new String(Base64.decode(encodedUserPassword));
            } catch (IOException e) {
                requestContext.abortWith(SERVER_ERROR);
                return;
            }

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            //检查账号是否存在
            if(method.isAnnotationPresent(RolesAllowed.class)){
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                if(!authorizeUser(username, password, rolesSet)){
                    requestContext.abortWith(ACCESS_DENIED);
                    return;
                }
            }

        }
    }

    private boolean authorizeUser(String username, String password, Set<String> roleSet){
        for(String role : roleSet){
            String in = username.trim() + ":" + password.trim() + ":" + role;
            if(accountList.contains(in)){
                return true;
            }
            continue;
        }

        return false;
    }

}
