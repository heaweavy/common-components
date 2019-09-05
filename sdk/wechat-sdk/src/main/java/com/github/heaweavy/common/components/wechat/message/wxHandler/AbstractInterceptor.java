package com.github.heaweavy.common.components.wechat.message.wxHandler;

import com.github.heaweavy.common.components.wechat.message.framework.handler.Interceptor;
import com.github.heaweavy.common.components.wechat.message.framework.router.RequestContext;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public abstract class AbstractInterceptor implements Interceptor<String> {
    protected Interceptor<String> successor;

    @Override
    public abstract String doWork(RequestContext context);

    @Override
    public Interceptor<String> setSuccessor(Interceptor<String> successor){
        this.successor = successor;
        return this.successor;
    }

    @Override
    public Interceptor<String> getSuccessor() {
        return this.successor;
    }

    @Override
    public String start(RequestContext context) {
        String result = doWork(context);
        if (null == result) {
            throw new RuntimeException("Interceptor#doWork should not return null");
        }

        if (!result.isEmpty()) {
            return result;
        } else if (this.getSuccessor() != null) {
            return this.getSuccessor().doWork(context);
        } else {
            // 不能处理此事件。
            return "";
        }
    }
}
