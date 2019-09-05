package com.github.heaweavy.common.components.wechat.exception;

/**
 * API call exception
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class WeiXinApiException extends Exception{
    private int errorCode;
    private String errorMessage;
    
    public WeiXinApiException() {
        super();
    }

    public WeiXinApiException(String message, int code) {
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }

    @Override
    public String getMessage(){
       return String.format("[errcode: %d, errmsg: %s]", this.errorCode, this.errorMessage);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    
}
