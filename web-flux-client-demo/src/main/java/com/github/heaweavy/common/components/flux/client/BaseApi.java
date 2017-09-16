package com.github.heaweavy.common.components.flux.client;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author caimb
 * @date Created in 2017/9/15 15:42
 * @modefier
 */
public class BaseApi {
    protected String rootUrl;
    protected WebClient client;
    public BaseApi(String rootUrl) {
        this.rootUrl = rootUrl;
        this.client = WebClient.create( rootUrl );
    }

    public WebClient create() {
        return WebClient.create( rootUrl );
    }
}
