package com.learning.mq.listener;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitConfig 未经测试
 * Created by Administrator on 2018/12/18.
 */
//@Configuration
public class RabbitConfig {

//    @Bean
    public Queue queue(){
        Queue queue = new Queue("direct_queue_test", true);
        return queue;
    }

//    @Bean
    public DirectExchange directExchange(){
        DirectExchange exchange = new DirectExchange("direct_test_exchange", true, false);
        return exchange;
    }

//    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange){
        Binding binding = BindingBuilder.bind(queue).to(directExchange).with("direct_queue_test_key");
        return binding;
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        return rabbitTemplate;
//    }
}
