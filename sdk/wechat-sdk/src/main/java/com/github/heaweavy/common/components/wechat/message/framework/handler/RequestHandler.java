package com.github.heaweavy.common.components.wechat.message.framework.handler;

import com.github.heaweavy.common.components.wechat.message.framework.router.RequestContext;

import java.util.concurrent.Callable;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public interface RequestHandler<V> {
    public void setContext(RequestContext context);
    public RequestContext context();
    public Callable<V> getCallable();

    public void registerInterceptors(Interceptor<V> interceptor);

}
