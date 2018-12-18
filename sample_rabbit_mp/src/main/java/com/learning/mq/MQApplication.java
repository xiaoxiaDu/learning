package com.learning.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * mq 启动类
 * Created by Administrator on 2018/12/17.
 */
@SpringBootApplication
@ImportResource(value = "classpath*:spring-rabbitmq.xml")
public class MQApplication {
    private static final Logger logger = LoggerFactory.getLogger(MQApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MQApplication.class, args);
        logger.info("MQApplication 项目启动成功");
    }
}
