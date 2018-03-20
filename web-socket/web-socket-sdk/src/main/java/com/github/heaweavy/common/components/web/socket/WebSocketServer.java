package com.github.heaweavy.common.components.web.socket;

import java.net.InetSocketAddress;



import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
/**
 * @author caimb
 * @date Created in 2017/9/28 16:17
 * @modefier
 */
public class WebSocketServer {
    private final int port;
    public static ChannelHandlerContext ctx = null;
    public WebSocketServer(int port){
        this.port = port;
    }
    public void run(){
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new WebSocketServerPipelineFactory());
        System.out.println();
        bootstrap.bind(new InetSocketAddress(port));
        System.out.println("Web socket server started at port " + port + '.');
        System.out.println("Open your browser and navigate to http://localhost:" + port + '/');
    }
    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8081;
        }
        new WebSocketServer(port).run();
    }
}