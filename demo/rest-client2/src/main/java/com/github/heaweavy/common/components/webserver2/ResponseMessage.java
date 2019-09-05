package com.github.heaweavy.common.components.webserver2;


/**
 * Created by Rogers on 15-3-8.
 */
public class ResponseMessage {
    public ResponseMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseMessage() {
    }

    private int status; // http status code
    private String message;

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
}
