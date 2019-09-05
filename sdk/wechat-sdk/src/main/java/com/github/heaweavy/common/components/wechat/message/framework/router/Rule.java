package com.github.heaweavy.common.components.wechat.message.framework.router;

import com.github.heaweavy.common.components.wechat.message.framework.MessageType;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public interface Rule {
    public String asString();
    public boolean matchWith(MessageType type);
}
