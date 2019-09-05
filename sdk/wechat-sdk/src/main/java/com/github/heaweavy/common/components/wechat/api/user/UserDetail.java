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
public class UserDetail {
    private String openid;
    private String nickname;
    private int sex;
    private String province;
    private String city;
    private String country;
    private String avatar;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private static String apiUrl =
            "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    private static String getUrl(String accessToken, String openid){
        return String.format(apiUrl, accessToken, openid);
    }

    public static UserDetail build(String accessToken, String openid) throws WeiXinApiException{
        JsonNode jsonResult;
        try {
            WeiXinApiInvoker invoker = new WeiXinApiInvoker();
            jsonResult = invoker.doRequest(getUrl(accessToken, openid));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        UserDetail userDetail = new UserDetail();
        userDetail.setOpenid(openid);
        userDetail.setAvatar(jsonResult.get("headimgurl").asText());
        userDetail.setCity(jsonResult.get("city").asText());
        userDetail.setProvince(jsonResult.get("province").asText());
        userDetail.setCountry(jsonResult.get("country").asText());
        userDetail.setNickname(jsonResult.get("nickname").asText());
        userDetail.setSex(jsonResult.get("sex").asInt());

        return userDetail;
    }

}
