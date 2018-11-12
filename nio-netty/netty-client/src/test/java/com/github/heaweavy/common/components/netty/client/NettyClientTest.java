package com.github.heaweavy.common.components.netty.client;

import org.junit.Before;
import org.junit.Test;

/**
 * @author caimb
 * @date Created at 2018/10/10 18:01
 * @modefier
 */
public class NettyClientTest {
    private EchoClient client;
    @Before
    public void init() {
        client = new EchoClient( "localhost", 9999 );
    }

    @Test
    public void start() throws InterruptedException {
        System.out.println( "客户端启动" );
        client.start();
        System.out.println( "客户端关闭" );
    }
}
