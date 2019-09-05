package com.github.heaweavy.common.components.wechat.api.js;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.heaweavy.common.components.wechat.api.WeiXinApiInvoker;
import com.github.heaweavy.common.components.wechat.exception.WeiXinApiException;

import java.io.IOException;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class JsTicket {
    private String ticket;
    private int expiryTime; // in seconds

    private static String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket"
            + "?access_token=%s&type=jsapi";

    public JsTicket(String ticket, int expiryTime) {
        this.ticket = ticket;
        this.expiryTime = expiryTime;
    }

    public String getTicket() {
        return ticket;
    }

    public int getExpiryTime() {
        return expiryTime;
    }

    private static String getUrl(String accessToken){
        return String.format(url, accessToken);
    }

    public static JsTicket build(String accessToken) throws WeiXinApiException{
        JsonNode jsonNode;
        try {
            WeiXinApiInvoker invoker = new WeiXinApiInvoker();
            jsonNode = invoker.doRequest(getUrl(accessToken));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        JsTicket response = new JsTicket(
                jsonNode.get("ticket").asText(),
                jsonNode.get("expires_in").asInt());

        return response;
    }
}
