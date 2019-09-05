package com.github.heaweavy.common.components.wechat.api.js;

import com.github.heaweavy.common.components.wechat.api.accessToken.AccessTokenFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.github.heaweavy.common.components.wechat.api.accessToken.AccessToken;
import com.github.heaweavy.common.components.wechat.exception.WeiXinApiException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class JSTicketFactory {
    
    private static final Cache<String, String> cache =
            CacheBuilder.newBuilder()
                .maximumSize(10).expireAfterWrite(7100, TimeUnit.SECONDS)
                .build();
    
    public static String getJsTicket(String appId, String appSecret){
        return ticketGetter(appId, appSecret);
    }
    
    //客户端主动刷新js-ticket
    public static String refreshTicket(String appId, String appSecret){
        cache.invalidate(appId);
        return getJsTicket(appId, appSecret);
    }

    private static String ticketGetter(final String appId, final String appSecret){
        //缓存7200秒
        try{
            return cache.get(appId, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    AccessToken at = null;
                    JsTicket jr = null;
                    try{
                        at = AccessTokenFactory.getAccessToken(appId, appSecret);
                        jr = JsTicket.build(at.getToken());
                    }catch(WeiXinApiException e){
                        if(e.getErrorCode() == 42001 || e.getErrorCode() == 4001){
                            // access token 超时或无效
                            at = AccessTokenFactory.refreshToken(appId, appSecret);
                            jr = JsTicket.build(at.getToken());
                        }
                    }
                    
                    return jr.getTicket();
                }
            });
        }catch (ExecutionException e){
            throw new RuntimeException(e);
        }
    }
}
