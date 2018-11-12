package com.github.heaweavy.common.components.server;

import com.github.heaweavy.common.components.server.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author caimb
 * @date Created at 2018/10/10 17:36
 * @modefier
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();//服务端启动必备
            bootstrap.group( group ).channel( NioServerSocketChannel.class )//nio进行网络通讯
                    .localAddress( new InetSocketAddress( port ) )//指定服务器端口
                    // 接收到连接请求，新启一个socket通信，也就是channel，
                    // 每个channel有自己的时间的handler
                    .childHandler( new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast( serverHandler );
                        }
                    } );
            ChannelFuture f = bootstrap.bind().sync();//绑定到端口，阻塞等待直到连接完成
            f.channel().closeFuture().sync();//阻塞，直到channel关闭
        }finally {
            group.shutdownGracefully().sync();
        }
    }

}
