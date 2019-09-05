package com.github.heaweavy.common.components.wechat.api.user;

import com.github.heaweavy.common.components.wechat.api.WeiXinApiInvoker;
import com.github.heaweavy.common.components.wechat.api.accessToken.AccessToken;
import com.github.heaweavy.common.components.wechat.exception.WeiXinApiException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Map;

/**
 * 微信公众号用户列表
 * @author caimb
 * @date Created at 2018/9/21 14:34
 * @modefier
 */
public class WxUserList {
    private Long total;
    private Long count;
    private JsonNode data;
    private String next_openid;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public String getNext_openid() {
        return next_openid;
    }

    public void setNext_openid(String next_openid) {
        this.next_openid = next_openid;
    }


    private static final String USER_LIST_REQUEST_URL =
            "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";

    private static String getUrl(String accessToken, String next_openid){
        String url = USER_LIST_REQUEST_URL + accessToken;
        if ( next_openid != null ) {
            url += "&next_openid=" + next_openid;
        }
        return url;
    }

    public static WxUserList build(String appId, String appSecret, String next_openid) throws WeiXinApiException {
        AccessToken accessToken = AccessToken.build( appId, appSecret );
        JsonNode jsonResult;
        try {
            WeiXinApiInvoker invoker = new WeiXinApiInvoker();
            jsonResult = invoker.doRequest( getUrl( accessToken.getToken(), next_openid ) );
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        WxUserList wxUserList = new WxUserList();
        wxUserList.setTotal( jsonResult.get( "total" ).asLong() );
        wxUserList.setCount( jsonResult.get( "count" ).asLong() );
        wxUserList.setData( jsonResult.get( "count" ) );
        wxUserList.setNext_openid( jsonResult.get( "next_openid" ).asText() );
        return wxUserList;
    }

}
