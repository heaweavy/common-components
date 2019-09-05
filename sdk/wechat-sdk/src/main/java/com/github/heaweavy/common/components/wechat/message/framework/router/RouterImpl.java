package com.github.heaweavy.common.components.wechat.message.framework.router;

import com.github.heaweavy.common.components.wechat.message.framework.MessageType;
import com.google.common.base.Preconditions;
import com.github.heaweavy.common.components.wechat.message.framework.handler.RequestHandler;
import com.github.heaweavy.common.components.wechat.message.framework.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class RouterImpl implements Router<String> {
    private static final Logger logger = LoggerFactory.getLogger(RouterImpl.class);

    private long timeout = 4000;   // in milliseconds
    // HashMap也是足够安全的
    private ConcurrentMap<MessageType, RequestHandler<String>> handlers = new ConcurrentHashMap<>();

    private Filter topFilter = null;

    // async handler executing
    private ExecutorService executorService;

    @Override
    public void registerHandler(RequestHandler<String> handler) {
        Rules rulesAnnotation = null;
        try {
            rulesAnnotation = handler.getClass().getAnnotation(Rules.class);
        }catch (NullPointerException e){
            throw new IllegalArgumentException("未找到router rule,无效的RequestHandler！");
        }

        if(rulesAnnotation == null){
            throw new IllegalArgumentException("未找到router rule,无效的RequestHandler！");
        }
        this.handlers.put(rulesAnnotation.value(), handler);
    }

    @Override
    public List<RequestHandler> allHandlers() {
        return null;
    }

    @Override
    public void registerFilter(Filter filter) {
        Rules rulesAnnotation;
        if(filter == null){
            throw new IllegalArgumentException("You are registering a null filter!");
        }
        this.topFilter = filter;
    }

    @Override
    public List<Filter> allFilters() {
        if(topFilter == null) {
            throw new IllegalStateException("Filter还没有注册, 请先注册");
        }
        List<Filter> filters = new ArrayList<>();
        Filter next = topFilter;
        while (next.getSuccessor() != null){
            filters.add(next);
            next = next.getSuccessor();
        }
        return filters;
    }


    @Override
    public String process(RequestContext context) {
        Preconditions.checkArgument(null !=context.currentRule(), "未设置消息Rule");

        // Filters先行
        if(! adoptFilters(context)){
            logger.debug("消息被过滤");
            return onError();
        }

        RequestHandler<String> handler = findHandler(context.currentRule());
        if(handler == null){
            logger.error("不能处理此消息, 未找到对应的消息处理器");
            return onError();
        }
        try {
            handler.setContext(context);
            Future<String> future = executorService.submit(handler.getCallable());
            return future.get(timeout, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            logger.error("消息处理时发生异常：", e);
            return onError();
        }
    }

    @Override
    public boolean adoptFilters(RequestContext context){
        return topFilter.check(context);
    }

    @Override
    public String onError(){
        logger.debug("消息处理异常或超时");
        return "";
    }

    @Override
    public RequestHandler<String> findHandler(Rule rule) {
        for(Map.Entry<MessageType, RequestHandler<String>> entry: handlers.entrySet()){
            RequestHandler<String> handler = entry.getValue();
            MessageType t = entry.getKey();
            if(rule.matchWith(t)){
                return handler;
            }
        }
        return null;
    }

    @Override
    public void initialize() {
        if (! handlers.isEmpty()){
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1);
        }else {
            throw new RuntimeException("未找到注册的Request handler");
        }
    }

    @Override
    public void destroy(){
        handlers.clear();
        topFilter = null;
        if(executorService != null){
            executorService.shutdown();
        }
        logger.info("Router destroyed");
    }
}
