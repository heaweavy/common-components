package com.github.heaweavy.common.components.webserver1.schema;

/**
 * Created by Rogers on 15-8-24.
 */
public enum MsgType {
    SMS("短信"), WEIXIN("微信");

    private String name;

    MsgType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MsgType fromName(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Empty name");
        }

        for(MsgType m: MsgType.values()){
            if(m.getName().equals(name)){
                return m;
            }
        }
        throw new IllegalArgumentException("No matching MsgType");
    }
}
