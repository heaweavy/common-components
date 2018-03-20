package com.github.heaweavy.common.components.mysql_test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Rogers on 15-3-25.
 */

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread thread = new Thread(){

            @Override
            public void run() {
                super.run();
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
}
