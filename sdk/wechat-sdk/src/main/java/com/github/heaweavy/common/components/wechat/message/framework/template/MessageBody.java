package com.github.heaweavy.common.components.wechat.message.framework.template;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public interface MessageBody {
    
    public abstract JsonNode toJson();
}
