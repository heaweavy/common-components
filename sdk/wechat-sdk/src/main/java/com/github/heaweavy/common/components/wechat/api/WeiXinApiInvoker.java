package com.github.heaweavy.common.components.wechat.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.heaweavy.common.components.wechat.Helper;
import com.github.heaweavy.common.components.wechat.exception.WeiXinApiException;
import com.google.common.io.CharStreams;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WeiXin API请求，失败后会重试
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class WeiXinApiInvoker {
    private static int retryInternalInMillis = 1000; // 1000 milliseconds
    private static int maxRetryTimes = 5;

    protected static final Logger logger = LoggerFactory.getLogger(WeiXinApiInvoker.class);

    public WeiXinApiInvoker() {
    }

    /**
     * 请求微信API
     *
     * 请求失败后，会根据接口返回状态，判断是否需要尝试新的请求，尝试次数为5次。
     * @param url 微信接口URL
     * @return T
     * @throws java.io.IOException HTTP请求异常
     * @throws com.github.heaweavy.common.components.wechat.exception.WeiXinApiException 微信API异常
     */
    public JsonNode doRequest(String url) throws IOException, WeiXinApiException {
        CloseableHttpClient httpClient = Helper.getHttpClient();
        HttpGet request = new HttpGet(url);
        int retryTimes = 0;
        do {
            try {
                CloseableHttpResponse response = httpClient.execute(request);
                HttpEntity rspEntity = response.getEntity();
                logger.debug("Wx API Request successfully executed");
                return parseResult(rspEntity);
            } catch (WeiXinApiException e) {
                if (e.getErrorCode() == -1) {
                    // 系统繁忙
                    int sleepMillis = retryInternalInMillis * (1 << retryTimes);
                    try {
                        logger.debug("微信系统繁忙，{}ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                }
            }
        } while (++retryTimes < maxRetryTimes);

        httpClient.close();
        throw new RuntimeException("微信API异常，超出重试次数!");
    }

    public JsonNode doPost(String url, JsonNode data) throws IOException, WeiXinApiException{
        CloseableHttpClient httpClient = Helper.getHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(data.toString(), ContentType.APPLICATION_JSON);
        post.setEntity(entity);

        int retryTimes = 0;
        do {
            try {
                CloseableHttpResponse response = httpClient.execute(post);
                HttpEntity rspEntity = response.getEntity();
                logger.debug("Post to Weixin API successfully executed");
                return parseResult(rspEntity);
            } catch (WeiXinApiException e) {
                if (e.getErrorCode() == -1) {
                    // 系统繁忙
                    int sleepMillis = retryInternalInMillis * (1 << retryTimes);
                    try {
                        logger.debug("微信系统繁忙，{}ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                }
            }
        } while (++retryTimes < maxRetryTimes);

        httpClient.close();
        throw new RuntimeException("微信API异常，超出重试次数!");
    }

    private JsonNode parseResult(final HttpEntity entity) throws WeiXinApiException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = null;


        String content = null;
        try {
            content = CharStreams.toString(new InputStreamReader(entity.getContent(), "UTF-8"));
            jsonObject = mapper.readTree(content);
        } catch (IOException e) {
            logger.error("Parsing json failed, raw data is\n:" + content);
            throw new RuntimeException(e);
        }
        // API error to exception
        WeiXinApiException exception = toException(jsonObject);
        if (exception != null) {
            throw exception;
        }
        return jsonObject;
    }

    private WeiXinApiException toException(JsonNode json) {
        if (json.isNull()) {
            return null;
        }
        ObjectNode node = (ObjectNode) json;
        if (node.hasNonNull("errcode") && node.hasNonNull("errmsg")) {
            if (node.get("errcode").asInt() != 0) {
                return new WeiXinApiException(node.get("errmsg").textValue(),
                        node.get("errcode").asInt());
            }
        }

        return null;
    }
}
