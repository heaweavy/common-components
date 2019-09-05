package com.github.heaweavy.common.components.wechat.api.menu;

import com.github.heaweavy.common.components.wechat.api.WeiXinApiInvoker;
import com.github.heaweavy.common.components.wechat.exception.WeiXinApiException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author caimb
 * @date Created at 2018/3/29 21:45
 * @modefier
 */
public class MenuConfig {
    private String menuCreatUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    public void createMenu(String accessToken, String data) throws IOException, WeiXinApiException {
        WeiXinApiInvoker invoker = new WeiXinApiInvoker();
        ObjectMapper mapper = new ObjectMapper();
        invoker.doPost(this.menuCreatUrl + accessToken,mapper.readTree(data));
    }
}
