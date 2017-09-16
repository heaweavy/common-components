package com.github.heaweavy.common.components.webserver2;


import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rogers on 15-3-9.
 */
public class BaseApp {
    protected static final Logger logger = LoggerFactory.getLogger("rest-api");
    protected String username;
    protected String password;
    protected Client client;
    protected String rootUrl;


    public BaseApp(String rootUrl, String username, String password){
        //默认每个API类连接池大小设置为10， TTL为300s
        this(rootUrl, username, password, 10, 5 * 60);
    }

    public BaseApp(String rootUrl, String username, String password, int poolSize, long ttlInSeconds){
        this.rootUrl = rootUrl;
        this.username = username;
        this.password = password;
        // 配置HTTP连接池
        ResteasyClientBuilder builder = new ResteasyClientBuilder()
                .connectionPoolSize(poolSize).connectionTTL(ttlInSeconds, TimeUnit.SECONDS);

        this.client = builder.build().register(new AuthFilter(username, password));

    }

    public void close(){
        this.client.close();
    }

    public <T> T getEntity(Response response, GenericType<T> genericType) throws RestApiException {
        if (200 == response.getStatus()) {
            try {
                return response.readEntity(genericType);
            } catch (Exception e) {
                throw new RestApiException(e);
            } finally {
                response.close();
            }
        } else {
            getError(response);
            return null;
        }
    }
    public <T> T getEntity(Response response, Class<T> clazz) throws RestApiException {
        if (200 == response.getStatus()) {
            try {
                return response.readEntity(clazz);
            } catch (Exception e) {
                throw new RestApiException(e);
            } finally {
                response.close();
            }
        } else {
            getError(response);
            return null;
        }
    }
    public void getError(Response response) throws RestApiException {
        try {
            ResponseMessage error = response.readEntity(ResponseMessage.class);
            throw new RestApiException(error.getStatus(), error.getMessage());
        } catch (ProcessingException e) {
            logger.error(e.getMessage(), e);
            if(response != null ){
                throw new RestApiException(response.getStatus(),response.getStatusInfo().getReasonPhrase());
            }
            throw new RestApiException(e);
        } finally {
            response.close();
        }
    }
    public ResponseMessage getResponseMessage(Response response,Logger logger) throws RestApiException {
        try {
            return response.readEntity(ResponseMessage.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if(response != null ){
                throw new RestApiException(response.getStatus(),response.getStatusInfo().getReasonPhrase());
            }
            throw new RestApiException(e);
        } finally {
            response.close();
        }
    }
}
