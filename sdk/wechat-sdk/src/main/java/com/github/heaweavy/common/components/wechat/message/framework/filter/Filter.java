package com.github.heaweavy.common.components.wechat.message.framework.filter;

import com.github.heaweavy.common.components.wechat.message.framework.router.RequestContext;

/**
 * 消息过滤器，全局起作用。
 *
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public interface Filter {
    /**
     * 调用Filter，处理消息
     *
     * <p>Filter采用职责链模式设计，声明Filter时，应将全局Filter放置于链条末端。</p>
     * <p>返回true表示可以继续处理，否则不需要继续处理</p>
     * @param context RequestContext
     * @return boolean
     */
    public boolean check(RequestContext context);

    /**
     * 设置下一个filter
     *
     * <p>此处传入的是Filter实例，因此Filter应该被设计成无状态的对象。</p>
     * @param successor 下一个Filter
     * @return Filter 返回下一个Filter
     * @throws java.lang.Exception any exception
     */
    public Filter setSuccessor(Filter successor) throws Exception;

    public Filter getSuccessor();
}
