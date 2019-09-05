package com.github.heaweavy.common.components.wechat.api.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class Oauth2RequestCache {
    private static final Cache<String, String> cache =
            CacheBuilder.newBuilder()
                .maximumSize(10000).expireAfterWrite(300, TimeUnit.SECONDS)
                .build();
    
    /**
     * Before redirect set request url:
     * @param state random string
     * @param url 需要授权的页面的URL
     */
    public static void setUrl(String state, String url){
        cache.put(state, url);
    }
    
    /**
     * After redirect, get the original url.
     * @param state random string set before redirect
     * @return 需要授权的页面的URL
     */
    public static String getOriginalUrl(String state){
        return cache.getIfPresent(state);
    }
    
}
