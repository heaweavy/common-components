package com.github.heaweavy.common.components.wechat.message.framework.template;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class TemplateMessageData implements MessageBody {

    protected static final String topColor = "#4A8BF5";
    protected static final String valueColor = "#173177";
    protected static final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

    protected String toUser;
    protected String url;
    protected String miniProgramAppId;
    protected String miniProgramPagePath;
    private final String templateId;

    /*
     {{first.DATA}}
     部门：{{keyword1.DATA}}
     通知人：{{keyword2.DATA}}
     时间：{{keyword3.DATA}}
     通知内容：{{keyword4.DATA}}
     {{remark.DATA}}
     */
    protected Map<String, String> dataMap;


    public TemplateMessageData(String toUser, String url, String miniProgramAppId, String miniProgramPagePath, String templateId, Map<String, String> data) {
        this.toUser = toUser;
        this.url = url;
        this.miniProgramAppId = miniProgramAppId;
        this.miniProgramPagePath = miniProgramPagePath;
        this.templateId = templateId;
        this.dataMap = data;
    }

    @Override
    public JsonNode toJson() {
        ObjectNode rootNode = nodeFactory.objectNode();
        rootNode.put("touser", this.toUser);
        rootNode.put("template_id", this.templateId);
        rootNode.put("url", this.url);
        ObjectNode miniProgram = null;
        if ( miniProgramAppId != null ) {
            miniProgram = nodeFactory.objectNode();
            miniProgram.put( "appid", miniProgramAppId );
            miniProgram.put( "pagepath", miniProgramPagePath );
        }
        rootNode.set( "miniprogram", miniProgram );
        rootNode.put("topcolor", TemplateMessageData.topColor);
        rootNode.set("data", getMessageData());
        return rootNode;
    }

    protected JsonNode getMessageData() {
        ObjectNode dataNode = nodeFactory.objectNode();
        for (String key : this.dataMap.keySet()) {
            setDataField(dataNode, key, this.dataMap.get(key));
        }

        return dataNode;
    }

    protected ObjectNode setDataField(ObjectNode dataNode, String fieldName, String value) {
        ObjectNode valueNode = nodeFactory.objectNode();
        valueNode.put("color", TemplateMessageData.valueColor);
        valueNode.put("value", value);
        dataNode.set(fieldName, valueNode);

        return dataNode;
    }
}
