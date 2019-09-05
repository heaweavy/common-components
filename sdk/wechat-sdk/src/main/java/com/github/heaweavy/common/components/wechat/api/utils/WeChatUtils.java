package com.github.heaweavy.common.components.wechat.api.utils;


public class WeChatUtils {

	//微信小程序中获取到session_key和openid的接口模板
	public static String SESSION_KEY_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

	//微信公众号网页授权获取access_token和openid的接口模板
	public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	//微信公众号网页授权获取用户信息的接口模板
	public static String USER_INFO_UNION_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	//获取微信小程序二维码接口模板
	public static String MINI_QR_CODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";

	public static String MINI_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/*
	* 获取到微信小程序获取session_key的URL
	* */
	public static String getSessionKeyURL(String appId,String appSecret,String code){

		String url = SESSION_KEY_URL.replaceAll("APPID",
				appId).replaceAll("SECRET",
				appSecret).replaceAll("JSCODE", code);
		return url;
	}

	/*
	* 获取到微信公众号获取access_token的URL
	* */
	public static String getAccessTokenURL(String appId,String appSecret,String code){

		String url = ACCESS_TOKEN_URL.replaceAll("APPID",
				appId).replaceAll("SECRET",
				appSecret).replaceAll("CODE", code);
		return url;
	}


	/*
	* 获取到微信公众号获取union的URL
	* */
	public static String getUserInfoUnionURL(String accessToken,String openId){
		String url = USER_INFO_UNION_URL.replaceAll("ACCESS_TOKEN",accessToken).replaceAll("OPENID",openId);
		return url;
	}

	/**
	 * 获取微信小程序二维码
	 * @param accessToken
	 * @return
	 */
	public static String getMiniQrCodeUrl(String accessToken){
		String url = MINI_QR_CODE_URL.replaceAll("ACCESS_TOKEN",accessToken);
		return url;
	}

	/**
	 * 获取微信小程序access_token
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String getMiniAccessTokenUrl(String appId, String appSecret){
		String url = MINI_ACCESS_TOKEN_URL.replaceAll("APPID",appId).replaceAll("APPSECRET",appSecret);
		return url;
	}
}
