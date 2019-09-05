package com.github.heaweavy.common.components.wechat.message.framework.handler;

import com.github.heaweavy.common.components.wechat.message.framework.router.RequestContext;

/**
 * 消息拦截器(处理单元)，注册在Handler上, 采用链式处理。
 *
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public interface Interceptor<V> {
    /**
     * 调用Interceptor，处理消息, 勿直接调用此方法。
     *
     * @param context RequestContext
     * @return V return value of handler
     */
    public V doWork(RequestContext context);

    public V start(RequestContext context);
    /**
     * 设置下一个Interceptor
     *
     * <p>此处传入的是Interceptor实例，因此Interceptor应该被设计成无状态的对象。</p>
     * @param successor next interceptor
     * @return Interceptor 返回successor
     */
    public Interceptor<V> setSuccessor(Interceptor<V> successor);

    public Interceptor<V> getSuccessor();
}
