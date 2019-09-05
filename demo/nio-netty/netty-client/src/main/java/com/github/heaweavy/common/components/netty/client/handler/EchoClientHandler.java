package com.github.heaweavy.common.components.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author caimb
 * @date Created at 2018/10/10 17:14
 * @modefier
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static Logger logger = LoggerFactory.getLogger( EchoClientHandler.class );
    /**
     * 客户端读取到数据后对数据处理
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        System.out.println( "client accept: " + msg.toString( CharsetUtil.UTF_8 ) );
    }

    /**
     * 通道建立以后，发送数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush( Unpooled.copiedBuffer( "Hello,Netty", CharsetUtil.UTF_8 ) );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error( "服务出错", cause );
        ctx.close();
    }
}
