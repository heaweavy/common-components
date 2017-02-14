package com.github.heaweavy.common.components.webserver1.rest;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rogers on 15-3-8.
 */
public class OnError {
    private int status;
    private String message;

    public OnError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static List<OnError> buildError(int status, String message){
        LinkedList error = new LinkedList<OnError>();
        error.add(new OnError(status,message));
        return error;
    }
}
