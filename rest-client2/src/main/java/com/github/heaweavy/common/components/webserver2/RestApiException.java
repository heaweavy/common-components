package com.github.heaweavy.common.components.webserver2;

/**
 * Created by Rogers on 15-3-30.
 */
public class RestApiException extends Exception{
    private int status;
    private String message;

    public RestApiException(Throwable t){
        super(t);
        this.status = 500;
        this.message = t.getMessage();
    }

    public RestApiException(int status, String message){
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage(){
        return String.format("status: %s, message: %s", this.status, this.message);
    }
    public String getOnlyMessage(){
        return this.message;
    }

    public int getStatus(){
        return this.status;
    }
}
