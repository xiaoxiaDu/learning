package com.learning.api.web.retry.notify.impl;

import com.alibaba.fastjson.JSON;
import com.learning.api.web.retry.RetryAble;
import com.learning.api.web.retry.notify.HttpNotifyClient;
import com.learning.api.web.retry.notify.HttpNotifyMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 通知服务实现
 * Created by Administrator on 2018/10/15.
 */
@Component
public class HttpNotifyClientImpl implements HttpNotifyClient, RetryAble {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 通知频率
     */
    private final static int[] INTERVALS = new int[]{15, 15, 30, 180, 1800, 1800, 1800, 1800, 3600};
//    private final static int[] INTERVALS = new int[]{1 * 60, 5 * 60, 10 * 60, 30 * 60, 60 * 60, 2 * 3600, 5 * 3600, 10 * 3600, 15 * 3600, 24 * 3600};

    @Autowired
    private RetryService retryService;

    /**
     * 重试服务
     *
     * @return
     */
    @Bean
    public RetryService getRetryService() {
        logger.info("初始化, RetryService");
        RetryService service = new RetryService(INTERVALS);
        service.start();
        logger.info("已启动, RetryService");
        return service;
    }

    /**
     * @param msg
     * @return
     */
    @Override
    public void httpNotify(HttpNotifyMsg msg) {
//        String callbackUrl = msg.getCallbackUrl();
//        Map<String, String> data = msg.getData();
        try {
            // 保存数据, msg 中将会有id
            String param = JSON.toJSONString(msg);
            logger.info("HttpNotifyMsg={}", param);
            boolean flag = retry(param);
            if (!flag) {
                retryService.addNewTask(this.getClass(), param);
            }
        } catch (Exception e) {
            logger.error("异步通知异常: ", e);
        }
    }

    /**
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public boolean retry(String param) throws Exception {
        logger.info("retry, param={}", param);
        HttpNotifyMsg notifyMsg = JSON.parseObject(param, HttpNotifyMsg.class);
        String callbackUrl = notifyMsg.getCallbackUrl();
        Map<String, String> data = notifyMsg.getData();
        // http 远程调用
        String result = "fail";
        if ("success".equals(result)) {
            // 更新保存数据(根据id 判断, 如果有就更新, 如果没有就保存)

            logger.info("通知成功");
            return true;
        }
        logger.info("通知失败, 等待重试");
        return false;
    }

}
