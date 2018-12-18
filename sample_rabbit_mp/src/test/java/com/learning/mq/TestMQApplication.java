package com.learning.mq;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类
 * Created by Administrator on 2018/12/17.
 */
@SpringBootTest(classes = MQApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMQApplication {
    private static final Logger loggre = LoggerFactory.getLogger(TestMQApplication.class);

    @Before
    public void before(){
        loggre.info("TestMQApplication before");
    }

    @Test
    public void test(){
        loggre.info("哈哈哈");
    }

    @After
    public void after(){
        loggre.info("TestMQApplication after");
    }
}
