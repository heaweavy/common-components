package com.github.heaweavy.common.components.wechat.exception;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class WeiXinException extends Exception {

    public WeiXinException(){
        super();
    }

    public WeiXinException(String message){
        super(message);
    }
}
