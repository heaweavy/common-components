package com.github.heaweavy.common.components.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author caimb
 * @date Created at 2018/10/10 17:36
 * @modefier
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger( EchoServerHandler.class );

    /**
     * 服务端读取到网络数据后的处理
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("server accept: " + in.toString( CharsetUtil.UTF_8 ));
        ctx.write( msg );
    }
    /**
     * 服务端读取完网络数据后的处理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush( Unpooled.EMPTY_BUFFER )
                .addListener( ChannelFutureListener.CLOSE );
    }

    /**
     * 发生异常后的处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error( "服务端出错", cause );
        ctx.close();
    }
}
