package com.github.heaweavy.common.components.wechat.message.framework.router;

import com.github.heaweavy.common.components.wechat.message.framework.MessageType;

import java.lang.annotation.*;

/**
 * value: {@link com.github.heaweavy.common.components.wechat.message.framework.MessageType}
 *
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Rules {
    MessageType value();
}
