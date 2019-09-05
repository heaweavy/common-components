package com.github.heaweavy.common.components.wechat.message;

import com.github.heaweavy.common.components.wechat.message.framework.router.RequestContext;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class WxRequestContextTest {
    RequestContext context;

    @Before
    public void setUp() throws Exception {
        String data = "<xml>" +
                " <ToUserName><![CDATA[toUser]]></ToUserName>" +
                " <FromUserName><![CDATA[fromUser]]></FromUserName> " +
                " <CreateTime>1348831860</CreateTime>" +
                " <MsgType><![CDATA[text]]></MsgType>" +
                " <Content><![CDATA[this is a test]]></Content>" +
                " <MsgId>1234567890123456</MsgId>" +
                " </xml>";

        data = "<xml>" +
                "<ToUserName><![CDATA[toUser]]></ToUserName>" +
                "<FromUserName><![CDATA[fromUser]]></FromUserName>" +
                "<CreateTime>123456789</CreateTime>" +
                "<MsgType><![CDATA[event]]></MsgType>" +
                "<Event><![CDATA[LOCATION]]></Event>" +
                "<Latitude>23.137466</Latitude>" +
                "<Longitude>113.352425</Longitude>" +
                "<Precision>119.385040</Precision>" +
                "</xml>";
        InputStream input = new ByteArrayInputStream(data.getBytes());
        context = new WxRequestContext(input);
    }

    @Test
    public void testBuild() throws Exception {
        context.build();
        System.out.println(context.getParsedData());
        System.out.println(context.currentRule());
    }

}