package com.github.heaweavy.common.components.netty.client;

import com.github.heaweavy.common.components.netty.client.handler.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author caimb
 * @date Created at 2018/10/10 17:14
 * @modefier
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();//线程组
        try {
            Bootstrap bootstrap = new Bootstrap();//客户端启动必备
            bootstrap.group( group ).channel( NioSocketChannel.class )
                    .remoteAddress( host, port )
                    .handler( new EchoClientHandler() );
            ChannelFuture f = bootstrap.connect().sync();//连接远程服务器，阻塞等待直到连接
            f.channel().closeFuture().sync();//阻塞，直到channel关闭
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
