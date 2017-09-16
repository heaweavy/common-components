package com.github.heaweavy.common.components.flux.client;

import com.dxtech.common.component.common.helper.ThreadUtils;
import com.github.heaweavy.common.components.shared.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author caimb
 * @date Created in 2017/9/15 15:57
 * @modefier
 */
public class UserApi extends BaseApi {
    public UserApi(String rootUrl) {
        super( rootUrl.endsWith("/") ? rootUrl + "api"
                : rootUrl + "/api" );
    }

    //TODO 经测试多线程调用怎样都会有危险，待解决
    public void getUsers() throws InterruptedException {
        ExecutorService executorService = ThreadUtils.getExecutorService();
//        reactor.ipc.netty.http.client.HttpClient
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        new Thread (()->{
            int sum =0;
            while ( true ) {
                System.out.println(Thread.currentThread().getName()+" 第 "+ ++sum+" 次循环");
                List<Future<List<User>>> futures = new LinkedList<>();
                for ( int i = 0; i < availableProcessors; i++ ) {
                    futures.add( executorService.submit( () -> {
                        ClientResponse response = client.get().uri( "/user" ).accept( MediaType.APPLICATION_JSON )
                                .exchange().block();
                        assert response.statusCode().value() == 200;
                        List<User> smsRequests = response.bodyToFlux( User.class ).collectList().block();
                        return smsRequests;
                    } ) );
                }
                int count = 0;
                for ( Future<List<User>> future : futures ) {
                    List<User> smsRequests = null;
                    try {
                        smsRequests = future.get();
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName()+" InterruptedException" );
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        System.out.println(Thread.currentThread().getName()+" ExecutionException" );
                        e.printStackTrace();
                    }
                    assert smsRequests.size() == 2;
                    ++count;
                }
                System.out.println(Thread.currentThread().getName()+"共操作 " +count+" 次");
                try {
                    Thread.sleep( 1000 );
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()+" InterruptedException" );
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread (()->{
            int sum =0;
            while ( true ) {
                System.out.println(Thread.currentThread().getName()+" 第 "+ ++sum+" 次循环");
                List<Future<List<User>>> futures = new LinkedList<>();
                for ( int i = 0; i < availableProcessors; i++ ) {
                    futures.add( executorService.submit( () -> {
                        ClientResponse response = client.get().uri( "/user" ).accept( MediaType.APPLICATION_JSON )
                                .exchange().block();
                        assert response.statusCode().value() == 200;
                        List<User> smsRequests = response.bodyToFlux( User.class ).collectList().block();
                        return smsRequests;
                    } ) );
                }
                int count = 0;
                for ( Future<List<User>> future : futures ) {
                    List<User> smsRequests = null;
                    try {
                        smsRequests = future.get();
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName()+" InterruptedException" );
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        System.out.println(Thread.currentThread().getName()+" ExecutionException" );
                        e.printStackTrace();
                    }
                    assert smsRequests.size() == 2;
                    ++count;
                }
                System.out.println(Thread.currentThread().getName()+"共操作 " +count+" 次");
                try {
                    Thread.sleep( 1000 );
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()+" InterruptedException" );
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized ( this ){
            this.wait();
        }
    }

    public User getUser(String userId) {
        return client.get().uri( "/user/" + userId )
                .accept( MediaType.APPLICATION_JSON ).exchange().flatMap( resp -> resp.bodyToMono( User.class ) ).block();
    }
}
