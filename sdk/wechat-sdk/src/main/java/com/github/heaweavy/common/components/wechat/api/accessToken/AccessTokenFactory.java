package com.github.heaweavy.common.components.wechat.api.accessToken;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * AccessToken工厂
 *
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class AccessTokenFactory {
    //以appId为cache key
    private static final Cache<String, AccessToken> cache =
            CacheBuilder.newBuilder()
                .maximumSize(1000).expireAfterWrite(7200, TimeUnit.SECONDS)
                .build();
    
    //Token server主动获取token
    public static AccessToken getAccessToken(String appId, String appSecret){
        return cachedToken(appId, appSecret);
    }
    
    //客户端主动刷新token
    public static AccessToken refreshToken(String appId, String appSecret){
        cache.invalidate(appId);
        return getAccessToken(appId, appSecret);
    }

    private static AccessToken cachedToken(final String appId, final String appSecret){
        //缓存7200秒
        try{
            return cache.get(appId, new Callable<AccessToken>() {
                @Override
                public AccessToken call() throws Exception {
                    return AccessToken.build(appId,
                        appSecret);
                }
            });
        }catch (ExecutionException e){
            throw new RuntimeException(e);
        }
    }
    
}
