package com.github.heaweavy.common.components.webdemo.controllers.authentication;

import com.github.heaweavy.common.components.webdemo.config.AppConstants;

import javax.servlet.http.HttpSession;

/**
 * Created by Rogers on 15-3-24.
 */
public class LoginHelper {


    public static boolean login(HttpSession session, Credential credential)
        throws AuthenticationException {
        credential.setAuthenticated(true);
        session.setAttribute(AppConstants.USER_SESSION_KEY, credential);
        return true;
    }

    public static void logout(HttpSession session){
        session.removeAttribute(AppConstants.USER_SESSION_KEY);
    }
}
