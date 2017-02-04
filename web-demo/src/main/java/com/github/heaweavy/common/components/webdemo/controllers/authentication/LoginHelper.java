package com.schoolguard.commander.controllers.authentication;

import com.schoolguard.commander.config.AppConstants;

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
