package com.learning.cache.dao;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonCache;
import org.redisson.spring.data.connection.RedissonConnection;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Administrator on 2018/12/20.
 */
@Service
public class CacheInterfaceImpl implements CacheInterface {

//    @Autowired
//    private RedissonClient redissonClient;
    @Autowired
    private RedissonConnectionFactory redissonConnectionFactory;
    private RedissonConnection redisConnection;

    public void test(){
        RedissonConnection redisConnection = (RedissonConnection) redissonConnectionFactory.getConnection();

    }
}
