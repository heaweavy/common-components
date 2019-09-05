package com.github.heaweavy.common.components.wechat.message.wxHandler;

import com.github.heaweavy.common.components.wechat.message.framework.handler.Interceptor;
import com.github.heaweavy.common.components.wechat.message.framework.handler.RequestHandler;
import com.github.heaweavy.common.components.wechat.message.framework.router.RequestContext;

import java.util.concurrent.Callable;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
//@Rules(MessageType.TEXT)
public abstract class AbstractWxHandler implements RequestHandler<String>{
    private RequestContext context;
    private Interceptor<String> interceptor;

    public void setContext(RequestContext context){
        this.context = context;
    }

    public RequestContext context(){
        return context;
    }


    public Callable<String> getCallable(){
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
//                WxRule rule = (WxRule) context.currentRule();
                return interceptor.start(context);
            }
        };
    }

    @Override
    public void registerInterceptors(Interceptor<String> interceptor){
        if(interceptor == null){
            throw new IllegalArgumentException("Interceptor is null!");
        }

        if(this.interceptor == null){
            this.interceptor = interceptor;
        }else{
            Interceptor<String> current = this.interceptor;
            while (current.getSuccessor() != null) {
                current = current.getSuccessor();
            }
            current.setSuccessor(interceptor);
        }

    }
}
