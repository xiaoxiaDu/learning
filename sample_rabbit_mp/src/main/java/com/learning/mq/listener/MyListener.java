package com.learning.mq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.nio.charset.Charset;

/**
 * 自定义消息监听器
 * Created by Administrator on 2018/12/18.
 */
public class MyListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MyListener.class);

    @Override
    public void onMessage(Message message) {
        logger.info("收到mq消息, message={}", new String(message.getBody(), Charset.forName("UTF-8")));

    }
}
