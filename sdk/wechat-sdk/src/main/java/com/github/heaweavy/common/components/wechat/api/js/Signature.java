package com.github.heaweavy.common.components.wechat.api.js;

import com.github.heaweavy.common.components.wechat.api.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Hex;

/**
 * JS API 签名
 *
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class Signature {

    private static String signatureTemplate = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
    private String url;
    private String jsApiTicket;
    private String nonceStr;
    private long timestamp;

    public Signature(String jsApiTicket, String url) {
        this.jsApiTicket = jsApiTicket;
        this.url = decodeUrl(url);
        this.nonceStr = Utils.randomString(16);
        this.timestamp = System.currentTimeMillis()/1000;
    }

    public Signature() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = decodeUrl(url);
    }

    public void setJsApiTicket(String jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String sign() {
        String finalString = String.format(signatureTemplate, this.jsApiTicket,
                this.nonceStr, this.timestamp, this.url);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            byte[] sign = digest.digest(finalString.getBytes());
            return Hex.encodeHexString(sign);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Map<String, Object> getSignConfig(){
        Map<String, Object> rv = new HashMap<>();
        rv.put("signature", sign());
        rv.put("timestamp", timestamp);
        rv.put("nonceStr", this.nonceStr);
        
        return rv;
    }

    public String decodeUrl(String url) {
        try {
            String decodedUrl = URLDecoder.decode(url, "utf-8");
            if (decodedUrl.equals(url)) {
                // Url is not encoded
                return url;
            }
            return decodedUrl;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
