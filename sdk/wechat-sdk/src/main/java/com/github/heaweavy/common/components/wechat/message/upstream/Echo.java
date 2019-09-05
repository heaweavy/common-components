package com.github.heaweavy.common.components.wechat.message.upstream;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public final class Echo {

    static String textTpl =
            "<xml><ToUserName><![CDATA[{0}]]></ToUserName>" +
            "<FromUserName><![CDATA[{1}]]></FromUserName>" +
            "<CreateTime>{2}</CreateTime>" +
            "<MsgType><![CDATA[text]]></MsgType>" +
            "<Content><![CDATA[{3}]]></Content>" +
            "</xml>";

    public static String returnText(String toUser, String fromUser, String content){
        return MessageFormat.format(textTpl, toUser, fromUser, System.currentTimeMillis(), content);
    }

    static String picTpl =
            "<xml><ToUserName><![CDATA[{0}]]></ToUserName>" +
            "<FromUserName><![CDATA[{1}]]></FromUserName>" +
            "<CreateTime>{2}</CreateTime>" +
            "<MsgType><![CDATA[image]]></MsgType>" +
            "<Image><MediaId><![CDATA[{3}]]></MediaId></Image></xml>";

    public static String returnPicture(String toUser, String fromUser, String mediaId){
        return MessageFormat.format(picTpl, toUser, fromUser, System.currentTimeMillis(), mediaId);
    }

    static String voiceTpl =
            "<xml><ToUserName><![CDATA[{0}]]></ToUserName>" +
            "<FromUserName><![CDATA[{1}]]></FromUserName>" +
            "<CreateTime>{2}</CreateTime>" +
            "<MsgType><![CDATA[voice]]></MsgType>" +
            "<Voice><MediaId><![CDATA[{3}]]></MediaId></Voice></xml>";

    public static String returnVoice(String toUser, String fromUser, String mediaId){
        return MessageFormat.format(voiceTpl, toUser, fromUser, System.currentTimeMillis(), mediaId);
    }

    static String videoTpl =
            "<xml><ToUserName><![CDATA[{0}]]></ToUserName>" +
            "<FromUserName><![CDATA[{1}]]></FromUserName>" +
            "<CreateTime>{2}</CreateTime>" +
            "<MsgType><![CDATA[video]]></MsgType>" +
            "<Video>" +
            "<MediaId><![CDATA[{3}]]></MediaId>" +
            "<Title><![CDATA[{4}]]></Title>" +
            "<Description><![CDATA[{5}]]></Description></Video></xml>";

    public static String returnVideo(String toUser, String fromUser, String mediaId, String title, String description){
       return MessageFormat.format(videoTpl, toUser, fromUser, System.currentTimeMillis(), mediaId, title, description);
    }

    static String musicTpl =
            "<xml><ToUserName><![CDATA[{0}]]></ToUserName>" +
            "<FromUserName><![CDATA[{1}]]></FromUserName>" +
            "<CreateTime>{2}</CreateTime>" +
            "<MsgType><![CDATA[music]]></MsgType>" +
            "<Music>" +
            "<Title><![CDATA[{3}]]></Title>" +
            "<Description><![CDATA[{4}]]></Description>" +
            "<MusicUrl><![CDATA[{5}]]></MusicUrl>" +
            "<HQMusicUrl><![CDATA[{6}]]></HQMusicUrl>" +
            "<ThumbMediaId><![CDATA[{7}]]></ThumbMediaId>" +
            "</Music></xml>";

    public static String returnMusic(String toUser,
                                     String fromUser,
                                     String title,
                                     String description,
                                     String musicUrl,
                                     String hqMusicUrl,
                                     String thumbMediaId){
        return MessageFormat.format(musicTpl, toUser, fromUser, System.currentTimeMillis(),
                title, description, musicUrl, hqMusicUrl, thumbMediaId);
    }

    static String articleTpl1 =
            "<xml><ToUserName><![CDATA[{0}]]></ToUserName>" +
            "<FromUserName><![CDATA[{1}]]></FromUserName>" +
            "<CreateTime>{2}</CreateTime>" +
            "<MsgType><![CDATA[news]]></MsgType>" +
            "<ArticleCount>{3}</ArticleCount>" +
            "<Articles>";

    static String articleTpl2 =
            "<item><Title><![CDATA[{0}]]></Title>" +
            "<Description><![CDATA[{1}]]></Description>" +
            "<PicUrl><![CDATA[{2}]]></PicUrl>" +
            "<Url><![CDATA[{3}]]></Url></item>";

    public static String returnArticles(String toUser, String fromUser,
                                 List<Article> articles){
        int articleNum = articles.size();
        if(articleNum == 0){
            throw new IllegalArgumentException("No Article is provided!");
        }
        if(articleNum > 0){
            throw new IllegalArgumentException("Too Many Articles(Should be no more than 10)!");
        }

        String part1 = MessageFormat.format(articleTpl1, toUser, fromUser, System.currentTimeMillis(), articleNum);

        StringBuilder builder = new StringBuilder();
        for(Article article: articles){
            String f = MessageFormat.format(articleTpl2, article.getTitle(), article.getDescription(), article.getPicUrl(), article.getUrl());
            builder.append(f);
        }

        return part1 + builder.toString() + "</Articles></xml>";
    }
}
