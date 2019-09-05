package com.github.heaweavy.common.components.wechat.message.framework.filter;

import com.github.heaweavy.common.components.wechat.message.framework.router.RequestContext;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public abstract class AbstractFilter implements Filter {
    protected Filter successor;

    /**
     * 过滤处理
     * 可以继承此类，重写此方法，定制处理方式。
     * @param context RequestContext
     * @return true or false, 返回true则可以继续处理；false则表示被拦截
     */
    @Override
    public abstract boolean check(RequestContext context);

    /**
     * 全局过滤器应为最后一个过滤器
     *
     * @param successor 下一个过滤器
     * @return next filter
     */
    @Override
    public Filter setSuccessor(Filter successor) throws Exception{
        if(null == successor){
            throw new IllegalArgumentException("Filter is null!");
        }
        this.successor = successor;
        return this.successor;
    }

    public Filter getSuccessor(){
        return this.successor;
    }
}
