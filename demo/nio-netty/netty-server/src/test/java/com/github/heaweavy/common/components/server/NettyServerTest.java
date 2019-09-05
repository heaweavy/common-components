package com.github.heaweavy.common.components.server;

import org.junit.Before;
import org.junit.Test;

/**
 * @author caimb
 * @date Created at 2018/10/10 17:59
 * @modefier
 */
public class NettyServerTest {
    private EchoServer echoServer;
    @Before
    public void init() {
        echoServer = new EchoServer( 9999 );
    }

    @Test
    public void start() throws InterruptedException {
        System.out.println( "正在启动服务" );
        echoServer.start();
        System.out.println( "服务关闭" );
    }
}
