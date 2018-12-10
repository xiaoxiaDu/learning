package com.learning.api.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * Created by Administrator on 2018/12/10.
 */
@SpringBootApplication
public class OpenApiApplication {
    private static final Logger logger = LoggerFactory.getLogger(OpenApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OpenApiApplication.class, args);
        logger.info("OpenApiApplication 启动成功!");
    }
}
