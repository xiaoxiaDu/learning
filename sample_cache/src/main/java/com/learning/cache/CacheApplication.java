package com.learning.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * cache 项目
 * Created by Administrator on 2018/12/20.
 */
@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
        System.out.println("CacheApplication 启动成功!");
    }
}
