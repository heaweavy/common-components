package com.schoolguard.commander.helper;

/**
 * Created by Rogers on 15-6-11.
 */
public class JsonResponse {
    private int status;
    private String message;

    public JsonResponse() {
    }

    public JsonResponse(int status, String message) {
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
}
