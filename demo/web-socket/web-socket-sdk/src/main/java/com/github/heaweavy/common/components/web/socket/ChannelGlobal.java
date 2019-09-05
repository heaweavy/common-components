package com.github.heaweavy.common.components.web.socket;

import org.jboss.netty.channel.Channel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author caimb
 * @date Created in 2017/9/28 16:15
 * @modefier
 */
public class ChannelGlobal {
    public static Map<Integer,Channel> ctxs= new HashMap<>();
}
