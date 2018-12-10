package com.learning.test.rpc.impl;

import com.learning.test.rpc.HelloService;

/**
 * hello 服务实现
 * Created by Administrator on 2018/10/17.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return "Hi, " + name;
    }
}
