package com.github.heaweavy.common.components.webserver2;

/**
 * Created by Rogers on 15/10/14.
 */
public class App {



    public static void main(final String[] args) throws Exception {
        // server config
        String bindingHost = System.getProperty("host", "0.0.0.0");
        int bindingPort = Integer.valueOf(System.getProperty("port", "8081"));
        new HttpServer().start(bindingHost, bindingPort);
    }


}
