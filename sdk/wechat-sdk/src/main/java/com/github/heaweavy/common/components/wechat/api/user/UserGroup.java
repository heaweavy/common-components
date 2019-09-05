package com.github.heaweavy.common.components.wechat.api.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.heaweavy.common.components.wechat.api.WeiXinApiInvoker;
import com.github.heaweavy.common.components.wechat.exception.WeiXinApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class UserGroup {
    private long id;
    private String name;
    private int count;

    public UserGroup() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    private static final String api = "https://api.weixin.qq.com/cgi-bin/groups/get";

    private static String getUrl(String accessToken){
        return api + "?access_token=" + accessToken;
    }

    public static List<UserGroup> build(String accessToken) throws WeiXinApiException{
        List<UserGroup> groups = new ArrayList<>();
        JsonNode jsonNode;
        try {
            WeiXinApiInvoker invoker = new WeiXinApiInvoker();
            jsonNode = invoker.doRequest(getUrl(accessToken));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        ArrayNode node = (ArrayNode) jsonNode.get("groups");
        for(JsonNode n: node){
            n = (ObjectNode) n;
            UserGroup group = new UserGroup();
            group.setId(n.get("id").asLong());
            group.setName(n.get("name").asText());
            group.setCount(n.get("count").asInt());

            groups.add(group);
        }

        return groups;
    }
}
