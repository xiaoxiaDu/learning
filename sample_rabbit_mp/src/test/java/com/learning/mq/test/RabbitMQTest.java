package com.learning.mq.test;

import org.junit.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * mq test
 * Created by Administrator on 2018/12/17.
 */
public class RabbitMQTest {

    @Test
    public void testSendAndReceive() {
        //创建ConnectionFactory //注意: guest的用户只能够在localhost 127.0.0.1进行测试
        String hostname = "127.0.0.1";// 5672
        String username = "guest";
        String password = "guest";
        String virtualHost = "/";
        CachingConnectionFactory cf = new CachingConnectionFactory(hostname);
        cf.setUsername(username);
        cf.setPassword(password);
        cf.setVirtualHost(virtualHost);

        RabbitAdmin admin = new RabbitAdmin(cf);

        //创建Exchange
        String exchangeName = "direct.test.exchange";
        exchangeName = "direct_test_exchange";
        DirectExchange exchange = new DirectExchange(exchangeName);
        admin.declareExchange(exchange);

        //创建Queue
        String queueName = "direct.test.queue";
        queueName = "direct_queue_test";
        Queue queue = new Queue(queueName, true, false, false, null);
        admin.declareQueue(queue);

        //创建Binding
        String routingKey = "direct.test.queue";
        routingKey = "direct_queue_test_key";
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));

        //创建RabbitTemplate
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cf);
        rabbitTemplate.setExchange(exchangeName);
        rabbitTemplate.setQueue(queueName);

        //创建Message
        String messageStr = "this is direct message";
        Message message = MessageBuilder.withBody(messageStr.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build();
        //根据routingKey发送消息
        System.out.println("message=" + message);
//        rabbitTemplate.send(routingKey, message);

        //接收消息
        Message resultMessage = rabbitTemplate.receive();
        System.out.println("resultMessage=" + resultMessage);
        if (resultMessage != null) {
            System.out.println("receive massage=" + new String(resultMessage.getBody()));
        }
    }
}
