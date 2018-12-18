package com.learning.mq.listener;

import com.learning.mq.TestMQApplication;
import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * 测试
 * Created by Administrator on 2018/12/18.
 */
public class MyListenerTest extends TestMQApplication{

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    public void test() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-rabbitmq.xml");
        rabbitTemplate = applicationContext.getBean("rabbitTemplate", RabbitTemplate.class);


        String sendMsg = "this is direct test message";
        sendMsg = "这是一条发送自测试的消息";
        Message message = MessageBuilder.withBody(sendMsg.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        String routingKey = "direct_queue_test_key";
        rabbitTemplate.send(routingKey, message);
        System.out.println("send ok");
    }

    @Test
    public void testr(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-rabbitmq.xml");
        rabbitTemplate = applicationContext.getBean("rabbitTemplate", RabbitTemplate.class);

        String routingKey = "direct_queue_test_key";
        Message receive = rabbitTemplate.receive();
        System.out.println(new String(receive.getBody(), Charset.forName("UTF-8")));

    }
}
