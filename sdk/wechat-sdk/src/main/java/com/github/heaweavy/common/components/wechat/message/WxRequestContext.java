package com.github.heaweavy.common.components.wechat.message;

import com.github.heaweavy.common.components.wechat.message.framework.Event;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.github.heaweavy.common.components.wechat.message.framework.router.BasicRequestContext;
import com.github.heaweavy.common.components.wechat.message.framework.router.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class WxRequestContext extends BasicRequestContext {
    private static final Logger logger = LoggerFactory.getLogger(WxRequestContext.class);

    public WxRequestContext(InputStream payload) {
        super(payload);
        logger.debug("Request Context created: " + creationTime);
    }

    @Override
    public RequestContext build(){
        try {
            parsePayload();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        if(dataMap.get("MsgType").equals("event")){
            Event event = Event.valueOf(dataMap.get("Event").toString().toUpperCase());
            if ( event == Event.TEMPLATESENDJOBFINISH ) {
                rule = new WxRule( (String) dataMap.get( "MsgType" ), event, (String) dataMap.get( "FromUserName" ),
                        Long.parseLong( (String) dataMap.get( "MsgId" ) ), Long.parseLong( (String) dataMap.get( "CreateTime" ) ) );
            }else {
                rule = new WxRule( (String) dataMap.get( "MsgType" ), event, (String) dataMap.get( "FromUserName" ),
                        Long.parseLong( (String) dataMap.get( "CreateTime" ) ) );
            }
        }else{
            rule = new WxRule((String)dataMap.get("MsgType"), (String) dataMap.get("FromUserName"), Long.parseLong((String)dataMap.get("MsgId")));
        }

        return this;
    }

    /**
     * 解析微信消息XML，只能处理无嵌套的扁平结构, 否则抛出异常
     * @throws Exception XmlStreamException
     */
    @Override
    public void parsePayload() throws Exception{
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(payload);

        String currentElement = null;
        while (reader.hasNext()){
            int eventType = reader.next();
            switch (eventType){
                case XMLStreamReader.START_ELEMENT:
                    String elementName = reader.getName().getLocalPart();
                    if(Strings.isNullOrEmpty(elementName) || elementName.equals("xml")){
                        break;
                    }
                    currentElement = elementName;
                    logger.debug(currentElement);
                    dataMap.put(currentElement, reader.getElementText());
                    break;

                case XMLStreamReader.END_DOCUMENT:
                    logger.debug("文档处理结束");
                    break;
            }
        }

        logger.debug(dataMap.toString());
        reader.close();
        validateData();
    }

    private void validateData(){
        Preconditions.checkNotNull(dataMap.containsKey("ToUserName"), "Xml缺少ToUserName元素");
        Preconditions.checkNotNull(dataMap.containsKey("FromUserName"), "Xml缺少FromUserName元素");
        Preconditions.checkNotNull(dataMap.containsKey("CreateTime"), "Xml缺少CreateTime元素");
        Preconditions.checkNotNull(dataMap.containsKey("MsgType"), "Xml缺少MsgType元素");
    }
}
