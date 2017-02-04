package com.github.heaweavy.common.components.webdemo.controllers.authentication;

/**
 * Created by Rogers on 15-3-24.
 */
public class AuthenticationException extends Exception {

    public AuthenticationException(){
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

}
