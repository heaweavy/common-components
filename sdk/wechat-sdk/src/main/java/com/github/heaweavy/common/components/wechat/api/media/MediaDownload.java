package com.github.heaweavy.common.components.wechat.api.media;

import com.github.heaweavy.common.components.wechat.Helper;
import com.github.heaweavy.common.components.wechat.exception.WeiXinApiException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class MediaDownload {
    private static final Logger logger = LoggerFactory.getLogger(MediaDownload.class);
    private static String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    private String accessToken;
    private String mediaId;
    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;

    /**
     * 多媒体文件下载
     *
     * @param accessToken 接口token
     * @param mediaId 微信多媒体文件ID(参见微信文档)
     */
    public MediaDownload(String accessToken, String mediaId) {
        this.accessToken = accessToken;
        this.mediaId = mediaId;
    }

    /**
     *  文件请求URL
      * @return url
     */
    public String getUrl(){
        return String.format(url, accessToken, mediaId);
    }

    /**
     * 发起请求
     * @throws IOException http请求异常
     * @throws WeiXinApiException 微信接口异常
     */
    public HttpResponse doRequest() throws IOException, WeiXinApiException {
        this.httpClient = Helper.getHttpClient();
        HttpGet request = new HttpGet(getUrl());

        try {
            this.response = httpClient.execute(request);
            return this.response;
        }catch (Exception e) {
            logger.error("Downloading file failed: ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 保存到本地文件
     * @param basePath 保存文件的目录
     * @return fileName
     * @throws Exception
     */
    public String saveToLocalDisk(String basePath) throws Exception{
        basePath = basePath.endsWith("/") ? basePath : basePath + "/";
        HttpEntity entity = response.getEntity();
        Header header = response.getFirstHeader("Content-Disposition");
        String filename = parseContentDisposition(header.getValue());
        BufferedInputStream bis = new BufferedInputStream(entity.getContent());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(basePath + filename));
        
        int inByte;
        while(-1 != (inByte = bis.read())){
            bos.write(inByte);
        }
        
        bis.close();
        bos.close();
        
        return filename;
    }

    /**
     * 从HTTP头获取文件名
     * @return
     */
    public String getFilename(){
        Header header = response.getFirstHeader("Content-Disposition");
        return parseContentDisposition(header.getValue());
    }

    /**
     * 关闭HTTPClient, 需要保证被调用
     */
    public void close(){
        try {
            this.httpClient.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static final Pattern CONTENT_DISPOSITION_PATTERN =
            Pattern.compile("attachment;\\s*filename\\s*=\\s*\"([^\"]*)\"");

    private static String parseContentDisposition(String contentDisposition){
        try {
            Matcher m = CONTENT_DISPOSITION_PATTERN.matcher(contentDisposition);
            if (m.find()) {
                return m.group(1);
            }
        } catch (IllegalStateException ex) {
            return null;
        }
        return null;
    }
}
