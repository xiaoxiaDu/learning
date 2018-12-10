package com.learning.api.web.retry.notify;

/**
 * 异步通知客户端
 * Created by Administrator on 2018/10/15.
 */
public interface HttpNotifyClient {

    /**
     * http 异步通知
     * @param msg
     * @return
     */
    void httpNotify(HttpNotifyMsg msg);
}
