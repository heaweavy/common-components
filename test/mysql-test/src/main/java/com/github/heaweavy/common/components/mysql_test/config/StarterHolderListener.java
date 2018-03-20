package com.github.heaweavy.common.components.mysql_test.config;

import com.github.heaweavy.common.components.mysql_test.entity.Children;
import com.github.heaweavy.common.components.mysql_test.entity.Person;
import com.github.heaweavy.common.components.mysql_test.entity.User;
import com.github.heaweavy.common.components.mysql_test.genetator.UserPersonGenerator;
import com.github.heaweavy.common.components.mysql_test.genetator.WordGenerator;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import com.github.heaweavy.common.components.mysql_test.mapper.*;

import java.util.Random;
import java.util.concurrent.ExecutorService;

public class StarterHolderListener implements ApplicationListener {

    private static Thread holdThread;
    private static Boolean running = Boolean.FALSE;

    public void onApplicationEvent(ApplicationEvent event)  {
        if (event instanceof ApplicationPreparedEvent) {
            if (running == Boolean.FALSE)
                running = Boolean.TRUE;
            if (holdThread == null) {
                holdThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName());
                        while (running && !Thread.currentThread().isInterrupted()) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }, "Starter Holder");
                holdThread.setDaemon(false);
                holdThread.start();
            }
        }
        if (event instanceof ContextClosedEvent) {
            running = Boolean.FALSE;
            if (null != holdThread) {
                holdThread.interrupt();
                holdThread = null;
            }
        }
        if ( event instanceof ApplicationReadyEvent ) {
            System.out.println("开始插入数据");
            ApplicationReadyEvent appEvent = (ApplicationReadyEvent) event;
            ExecutorService executorService = appEvent.getApplicationContext().getBean( ExecutorService.class );
            Random random = appEvent.getApplicationContext().getBean( Random.class );
            int treadNum = Runtime.getRuntime().availableProcessors();
            while ( treadNum-- > 0 ) {
                executorService.submit( ()->{
                    UserPersonGenerator.generate( appEvent, random );
                } );
            }
        }
    }
}
