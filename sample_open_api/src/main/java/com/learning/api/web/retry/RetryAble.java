package com.learning.api.web.retry;

/**
 * 重试接口
 * Created by Administrator on 2018/10/15.
 */
public interface RetryAble {

    /**
     * 重试
     * @param param
     * @return
     * @throws Exception
     */
    boolean retry(String param)throws Exception;
}
