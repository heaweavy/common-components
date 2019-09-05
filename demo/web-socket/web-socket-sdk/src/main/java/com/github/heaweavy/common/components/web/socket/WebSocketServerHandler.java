package com.github.heaweavy.common.components.web.socket;

import java.util.Iterator;
import java.util.Map;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.websocketx.*;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;
/**
 * @author caimb
 * @date Created in 2017/9/28 16:17
 * @modefier
 */
public class WebSocketServerHandler  extends SimpleChannelUpstreamHandler{
    private static final String WEBSOCKET_PATH = "/websocket";
    private WebSocketServerHandshaker handshaker;
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
        ChannelGlobal.ctxs.put(ctx.getChannel().getId(),ctx.getChannel());
        System.out.println("hash code :" + this.hashCode());
        System.out.println("id : "+ctx.getChannel().getId() +" open channel");
        System.out.println(ctx.getChannel()+"s");
    }
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
        ChannelGlobal.ctxs.remove(ctx.getChannel());
    }
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Object msg = e.getMessage();
        if ( msg instanceof PongWebSocketFrame ) {
            //System.out.println("id : " + ctx.getChannel().getId() + " pong");
            //客户端发送心跳包
            return;
        } else if ( msg instanceof BinaryWebSocketFrame ) {
            //TODO 二进制流上传;
//            BinaryWebSocketFrame bwf = (BinaryWebSocketFrame) msg;
//            ChannelBuffer buffer = bwf.getBinaryData();
//            int size=1024,length,index;
//            byte[] bytes = new byte[size];
//            while ( buffer.readable() ) {
//                index = buffer.readerIndex();
//                length = buffer.readableBytes();
//                if ( length < size ) {
//                    size = length;
//                }
//                buffer.readBytes( bytes, index, size );
//            }
        } else if ( msg instanceof CloseWebSocketFrame ) {
            handshaker.close(ctx.getChannel(), (CloseWebSocketFrame) msg);
            WebSocketServer.ctx = null;
        } else if(msg instanceof TextWebSocketFrame){
            String receivedMsg = msg.toString();
            receivedMsg = receivedMsg.substring(receivedMsg.indexOf('(')+1, receivedMsg.indexOf( ')'));
            String[] RealMsg = receivedMsg.split(":");
            if(!RealMsg[1].trim().equals("")){
                System.out.println( ctx.getChannel().getId() + " : " + RealMsg[1] );
                Iterator<Map.Entry<Integer,Channel>> MyCtx = ChannelGlobal.ctxs.entrySet().iterator();
                while(MyCtx.hasNext()){
                    MyCtx.next().getValue().write(new TextWebSocketFrame(RealMsg[1]));
                }
//                handleWebSocketFrame(ctx, (WebSocketFrame) msg);
            }
        } else if ( msg instanceof HttpRequest ) {
            handleHttpRequest(ctx, (HttpRequest) msg);
        }
        /*    String receivedMsg = msg.toString();
        receivedMsg = receivedMsg.substring(receivedMsg.indexOf('(')+1, receivedMsg.indexOf( ')'));
        String[] RealMsg = receivedMsg.split(":");
        if(!RealMsg[1].trim().equals("")){
            System.out.println( ctx.getChannel().getId() + " : " + RealMsg[1] );
            if (msg instanceof HttpRequest) {
                handleHttpRequest(ctx, (HttpRequest) msg);
            } else if (msg instanceof WebSocketFrame) {
                Iterator<Map.Entry<Integer,Channel>> MyCtx = Globle.ctxs.entrySet().iterator();
                while(MyCtx.hasNext()){
                    MyCtx.next().getValue().write(new TextWebSocketFrame(RealMsg[1]));
                }
                handleWebSocketFrame(ctx, (WebSocketFrame) msg);
            }
        }*/
    }
    private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest req) throws Exception {
        System.out.println("URI : " + req.getUri());
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                getWebSocketLocation(req), null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            wsFactory.sendUnsupportedWebSocketVersionResponse(ctx.getChannel());
        } else {
            handshaker.handshake(ctx.getChannel(), req).addListener(WebSocketServerHandshaker.HANDSHAKE_LISTENER);
        }
        WebSocketServer.ctx = ctx;
    }
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // Check for closing frame
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.getChannel(), (CloseWebSocketFrame) frame);

            WebSocketServer.ctx = null;
            return;
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
        WebSocketServer.ctx = null;
    }

    private static String getWebSocketLocation(HttpRequest req) {
        return "ws://" + req.getHeader(HOST) + WEBSOCKET_PATH;
    }
}