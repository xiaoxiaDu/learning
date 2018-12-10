package com.learning.test.rpc;

import com.learning.test.rpc.impl.HelloServiceImpl;
import com.learning.test.rpc.impl.ServiceCenter;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * rpc 测试
 * Created by Administrator on 2018/10/17.
 */
public class RPCTest {

    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Server serviceServer = new ServiceCenter(8088);
                    serviceServer.register(HelloService.class, HelloServiceImpl.class);
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        HelloService service = RPCClient.getRemoteProxyObj(HelloService.class, new InetSocketAddress("localhost", 8088));
        System.out.println(service.sayHi("test"));
    }

    @Test
    public void testRunnable(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("呵呵呵");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
