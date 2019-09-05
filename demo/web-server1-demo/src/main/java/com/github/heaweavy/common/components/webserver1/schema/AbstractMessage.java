package com.github.heaweavy.common.components.webserver1.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.*;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Rogers on 15-4-16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractMessage implements Serializable {
    protected static final long serialVersionUID = 643439877123L;
    protected String id;
    protected Long msgId;

    protected Date createTime;

    protected String signature;

    protected Status status;

    public AbstractMessage() {
        super();
    }

    /**
     * constructor
     *
     * @param msgId
     * @param schoolId
     * @param schoolName
     * @param teacherId
     * @param teacherName
     * @param classId
     * @param className
     * @param studentId
     * @param studentName
     * @param guardianMobile
     * @param guardianWeixin
     * @param signature
     * @param createTime
     * @param scheduleTime
     */
    public AbstractMessage(Long msgId, Integer schoolId,
                           String schoolName, Integer teacherId,
                           String teacherName, String teacherAvatar, Integer classId,
                           String className, Integer studentId,
                           String studentName,
                           Integer guardianId, String guardianMobile, String guardianWeixin,
                           String signature, Date createTime,
                           Date scheduleTime) {
        this.msgId = checkNotNull(msgId);
        this.createTime = checkNotNull(createTime);
        this.signature = checkNotNull(signature);
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMsgId() {
        return msgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getSignature() {
        return signature;
    }

    public Status getStatus() {
        return status;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

//    public abstract Map<String, Object> toMap();

    public byte[] toBytes(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try{
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            return bos.toByteArray();
        } catch (IOException e){
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e){ }

            try{
                bos.close();
            } catch (IOException e){}
        }
    }
}
