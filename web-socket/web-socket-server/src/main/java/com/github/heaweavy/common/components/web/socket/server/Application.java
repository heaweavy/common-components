package com.github.heaweavy.common.components.web.socket.server;

import com.github.heaweavy.common.components.web.socket.WebSocketServer;

/**
 * @author caimb
 * @date Created in 2017/9/29 9:36
 * @modefier
 */
public class Application {
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
