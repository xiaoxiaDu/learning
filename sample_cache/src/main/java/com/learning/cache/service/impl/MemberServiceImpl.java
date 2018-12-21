package com.learning.cache.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.learning.cache.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Administrator on 2018/12/21.
 */
@Service
public class MemberServiceImpl implements MemberService{
    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Override
    @Cacheable(value = "CACHE:member", key = "#userId")
    public String getMember(Long userId) {

        JSONObject member = new JSONObject();
        member.put("userName", "测试");
        member.put("userId", "111");
        String json = JSONObject.toJSONString(member);
        logger.info("json={}", json);
        return json;
    }

    @Override
    @CacheEvict(value = "CACHE:member", key = "#userId")
    public int updateMember(Long userId) {
        logger.info("updateMember, userId={}", userId);
        return 1;
    }
}
