package com.leanring.api.web;

import com.learning.api.web.OpenApiApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类
 * Created by Administrator on 2018/12/10.
 */
@SpringBootTest(classes = OpenApiApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestOpenApiApplication {

    @Before
    public void before(){
        System.out.println("before");
    }

    @Test
    public void test(){
        System.out.println("................");
    }

    @After
    public void after(){
        System.out.println("after");
    }

}
