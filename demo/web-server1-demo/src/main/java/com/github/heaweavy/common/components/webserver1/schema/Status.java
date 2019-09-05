package com.github.heaweavy.common.components.webserver1.schema;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rogers on 15-4-15.
 */
public class Status implements Serializable{
    protected static final long serialVersionUID = 78278378473284L;
    //是否发送成功
    protected boolean success;
    //消息发送时间
    protected Date sendTime;
    //提示消息
    protected String message;

    //发送次数计数
    protected int counter;

    protected boolean isRead;
    public Status() {
    }

    public Status(boolean success, String message){
        this.success = success;
        this.sendTime = new Date();
        this.message = (message == null ? "" : message);
        this.counter = 0;
        this.isRead = false;
    }

    public int getCounter(){
        return this.counter;
    }

    public void setCounter(int counter){
        this.counter = counter;
    }

    public boolean isSuccess() {
        return success;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public void setMessage(String message) {
        this.message = (message == null ? "" : message);
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
