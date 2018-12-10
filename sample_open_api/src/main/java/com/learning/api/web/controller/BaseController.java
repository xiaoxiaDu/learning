package com.learning.api.web.controller;

import org.springframework.stereotype.Controller;

/**
 * 抽象基础控制类
 * Created by Administrator on 2018/12/10.
 */
@Controller
public abstract class BaseController {
  @RequestMapping("/hi")
    public String helloWorld(HttpServletRequest request){
        logger.info("Hello World! test 123!");
        return "hi!";
    }
}
