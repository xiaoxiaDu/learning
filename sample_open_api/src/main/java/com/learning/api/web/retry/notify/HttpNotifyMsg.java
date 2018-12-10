package com.learning.api.web.retry.notify;

import java.io.Serializable;
import java.util.Map;

/**
 * 通知类
 * Created by Administrator on 2018/10/15.
 */
public class HttpNotifyMsg implements Serializable {
    private static final long serialVersionUID = 1566807113989212480L;

    private String callbackUrl;
    private Map<String, String> data;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
