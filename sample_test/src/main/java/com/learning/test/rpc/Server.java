package com.learning.test.rpc;

import java.io.IOException;

/**
 * 服务接口
 * Created by Administrator on 2018/10/17.
 */
public interface Server {

    public void stop();

    public void start() throws IOException;

    public void register(Class serviceInterface, Class impl);

    public boolean isRunning();

    public int getPort();
}
