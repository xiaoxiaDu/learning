package com.leanring.api.web.retry;

import com.leanring.api.web.TestOpenApiApplication;
import com.learning.api.web.retry.notify.HttpNotifyClient;
import com.learning.api.web.retry.notify.HttpNotifyMsg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by Administrator on 2018/12/4.
 */
public class HttpNotifyClientTest extends TestOpenApiApplication {

    @Autowired
    private HttpNotifyClient httpNotifyClient;


    @Test
    public void test(){
        Map<String, String> data = new HashMap<>();
        data.put("key1", "1");
        data.put("key2", "2");
        data.put("key3", "3");

        HttpNotifyMsg notifyMsg = new HttpNotifyMsg();
        notifyMsg.setCallbackUrl("http://127.0.0.1:8080/");
        notifyMsg.setData(data);

        httpNotifyClient.httpNotify(notifyMsg);


        boolean flag = true;
        synchronized (HttpNotifyClientTest.class) {
            while (flag) {
                try {
                    HttpNotifyClientTest.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
