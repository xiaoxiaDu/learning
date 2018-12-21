package com.learning.cache.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 *  cacheService
 * Created by Administrator on 2018/12/20.
 */
@Component(value = "cacheService")
public class CacheServiceImpl implements CacheService {
    private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
    private static final String REDIS_PRE_KEY = "CACHE:";

    @Autowired
    private RedisTemplate redisTemplate;

}
