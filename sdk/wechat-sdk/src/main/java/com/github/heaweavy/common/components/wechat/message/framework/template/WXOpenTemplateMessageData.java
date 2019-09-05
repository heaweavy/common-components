package com.github.heaweavy.common.components.wechat.message.framework.template;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;

/**
 * 小程序模板消息
 * @author caimb
 * @date Created at 2018/10/8 11:21
 * @modefier
 */
public class WXOpenTemplateMessageData implements MessageBody {

    protected static final String topColor = "#4A8BF5";
    protected static final String valueColor = "#173177";
    protected static final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
    protected String toUser;
    private final String templateId;
    protected String page;
    protected String formId;
    /*
     部门：{{keyword1.DATA}}
     通知人：{{keyword2.DATA}}
     时间：{{keyword3.DATA}}
     通知内容：{{keyword4.DATA}}
     */
    protected Map<String, String> dataMap;
    //模板需要放大的关键词，不填则默认无放大;  "emphasis_keyword": "keyword1.DATA"
    protected String emphasisKeyword;

    public WXOpenTemplateMessageData(String toUser, String templateId, String page, String formId, Map<String, String> data, String emphasisKeyword) {
        this.toUser = toUser;
        this.templateId = templateId;
        this.dataMap = data;
        this.page = page;
        this.formId = formId;
        this.emphasisKeyword = emphasisKeyword;
    }
    @Override
    public JsonNode toJson() {
        ObjectNode rootNode = nodeFactory.objectNode();
        rootNode.put( "touser", this.toUser );
        rootNode.put( "template_id", this.templateId );
        rootNode.put( "page", this.page );
        rootNode.put( "form_id", this.formId );
        rootNode.set("data", getMessageData());
        if ( emphasisKeyword != null ) {
            rootNode.put("emphasis_keyword", this.emphasisKeyword);
        }
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
