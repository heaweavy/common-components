package com.github.heaweavy.common.components.wechat.message;

import com.github.heaweavy.common.components.wechat.message.framework.Event;
import com.github.heaweavy.common.components.wechat.message.framework.MessageType;
import com.github.heaweavy.common.components.wechat.message.framework.router.Rule;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class WxRule implements Rule {
    private String type;
    private String user;
    private long msgId;
    private Event event;
    private long createTime;

    public WxRule(String type, String user, long msgId) {
        this.type = type;
        this.user = user;
        this.msgId = msgId;
    }

    public WxRule(String type, String event, String user, long createTime) {
        this.type = type;
        this.user = user;
        this.event = Event.valueOf(event.toUpperCase());
        this.createTime = createTime;
    }
    public WxRule(String type, Event event, String user, long createTime) {
        this.type = type;
        this.user = user;
        this.event = event;
        this.createTime = createTime;
    }

    public WxRule(String type, Event event, String user, long msgId, long createTime) {
        this.type = type;
        this.user = user;
        this.event = event;
        this.msgId = msgId;
        this.createTime = createTime;
    }

    @Override
    public String asString(){
        if(type.equals("event")){
            return "/type/" + type + "/event/"+ event.toString().toLowerCase() + "/user/" + user + "/time/" + createTime;
        }else{
            return "/type/" + type + "/user/" + user + "/msgid" + msgId;
        }
    }

    @Override
    public boolean matchWith(MessageType type1){
        return type1.equals(MessageType.valueOf(type.toUpperCase()));
    }

    public String getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public long getMsgId() {
        return msgId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public Event getEvent() {
        return event;
    }
}
