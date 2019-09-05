package com.github.heaweavy.common.components.wechat.message.framework;


import com.github.heaweavy.common.components.wechat.exception.WeiXinApiException;
import com.github.heaweavy.common.components.wechat.message.framework.template.TemplateMessage;
import com.github.heaweavy.common.components.wechat.message.framework.template.TemplateMessageData;
import com.github.heaweavy.common.components.wechat.message.framework.template.WXOpenTemplateMessage;
import com.github.heaweavy.common.components.wechat.message.framework.template.WXOpenTemplateMessageData;

import java.io.IOException;
import java.util.Map;

/**
* @author caimb
* @date Created in 2018/6/9 12:26
* @modifier 
 */
public class MessageSender {
    private String accessToken;

    public MessageSender(String accessToken) {
        this.accessToken = accessToken;
    }

    public void send(String templateId, String openid, String msgUrl,
                     String miniProgramAppId, String miniProgramPagePath,
                     Map<String, String> dataMap) throws WeiXinApiException {
        TemplateMessageData data = new TemplateMessageData( openid, msgUrl, miniProgramAppId, miniProgramPagePath, templateId, dataMap );
        TemplateMessage msg = new TemplateMessage(data, accessToken);
        try {
            msg.sendMessage();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    //小程序模板消息
    public void sendWXOpen(String templateId, String openid, String page,
                           String formId, Map<String, String> dataMap,
                           String emphasisKeyword) throws WeiXinApiException {
        WXOpenTemplateMessageData data = new WXOpenTemplateMessageData( openid, templateId, page, formId, dataMap, emphasisKeyword );
        WXOpenTemplateMessage msg = new WXOpenTemplateMessage( data, accessToken );
        try {
            msg.sendMessage();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
    }
}
