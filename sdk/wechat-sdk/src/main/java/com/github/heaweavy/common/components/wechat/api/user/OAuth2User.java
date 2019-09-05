package com.github.heaweavy.common.components.wechat.api.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.heaweavy.common.components.wechat.api.WeiXinApiInvoker;
import com.github.heaweavy.common.components.wechat.exception.WeiXinApiException;

import java.io.IOException;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class OAuth2User {
    private String accessToken;
    private String openId;
    private int expireTime;
    private String scope;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    private static final String ACCESS_TOKEN_REQUEST_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token?"
                    + "appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private static String getUrl(String appId, String appSecret, String code){
        return String.format(ACCESS_TOKEN_REQUEST_URL, appId, appSecret, code);
    }

    public static OAuth2User build(String appId, String appSecret, String code) throws WeiXinApiException{
        JsonNode jsonResult;
        try {
            WeiXinApiInvoker invoker = new WeiXinApiInvoker();
            jsonResult = invoker.doRequest(getUrl(appId, appSecret, code));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        OAuth2User o = new OAuth2User();
        o.setAccessToken(jsonResult.get("access_token").asText());
        o.setExpireTime(jsonResult.get("expires_in").asInt());
        o.setOpenId(jsonResult.get("openid").asText());
        o.setRefreshToken(jsonResult.get("refresh_token").asText());
        o.setScope(jsonResult.get("scope").asText());

        return o;
    }
}
