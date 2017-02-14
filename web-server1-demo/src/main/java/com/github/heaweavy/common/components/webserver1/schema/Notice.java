package com.github.heaweavy.common.components.webserver1.schema;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Rogers on 15-4-14.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notice extends AbstractMessage {
    protected String content;
    protected MediaType mediaType;
    protected String mediaUrl;

    public Notice() {
        super();
    }

    /**
     * 普通消息
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
     * @param content
     */
    public Notice(Long msgId, Integer schoolId,
                  String schoolName, Integer teacherId,
                  String teacherName, String teacherAvatar, Integer classId,
                  String className, Integer studentId,
                  String studentName,
                  Integer guardianId, String guardianMobile, String guardianWeixin,
                  String signature, Date createTime,
                  Date scheduleTime, String content) {
        super(msgId, schoolId, schoolName, teacherId, teacherName, teacherAvatar, classId, className,
                studentId, studentName, guardianId, guardianMobile, guardianWeixin,
                signature, createTime, scheduleTime);
        this.content = checkNotNull(content);
        this.mediaType = null;
        this.mediaUrl = null;
    }

    /**
     * 多媒体消息
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
     * @param mediaType
     * @param mediaUrl
     */
    public Notice(Long msgId, Integer schoolId,
                  String schoolName, Integer teacherId,
                  String teacherName, String teacherAvatar, Integer classId,
                  String className, Integer studentId,
                  String studentName,
                  Integer guardianId, String guardianMobile, String guardianWeixin,
                  String signature, Date createTime,
                  Date scheduleTime,
                  MediaType mediaType, String mediaUrl, String content) {
        super(msgId, schoolId, schoolName, teacherId, teacherName, teacherAvatar, classId, className,
                studentId, studentName, guardianId, guardianMobile, guardianWeixin,
                signature, createTime, scheduleTime);
        this.content = content;
        this.mediaType = checkNotNull(mediaType);
        this.mediaUrl = checkNotNull(mediaUrl);
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}
