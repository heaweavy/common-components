package com.github.heaweavy.common.components.wechat.message.framework.router;

import java.util.Map;

/**
 * 请求上下文
 *
 * 存储请求的元数据与业务数据, 此对象将被用作请求数据的主要载体。
 *
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public interface RequestContext {
    public boolean isEmpty();

    public long getCreationTime();

    public Object getAttribute(String key);

    public void setAttribute(String key, Object value);

    public Map<String, Object> getParsedData();

    public RequestContext build();

    public Rule currentRule();
}
