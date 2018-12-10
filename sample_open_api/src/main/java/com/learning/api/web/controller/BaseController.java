package com.learning.api.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 抽象基础控制类
 * Created by Administrator on 2018/12/10.
 */
@Controller
public abstract class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @RequestMapping("/hi")
    public String helloWorld(HttpServletRequest request){
        logger.info("Hello World! test 123!");
        return "hi!";
    }
}
