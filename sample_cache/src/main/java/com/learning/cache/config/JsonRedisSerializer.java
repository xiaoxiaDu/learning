package com.learning.cache.config;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;


/**
 * 序列化
 * Created by Administrator on 2018/12/20.
 */
@Deprecated
public class JsonRedisSerializer implements RedisSerializer {
    private static final Logger logger = LoggerFactory.getLogger(JsonRedisSerializer.class);

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if(object == null){
            return new byte[0];
        }
        String jsonString = JSONObject.toJSONString(object);
        byte[] bytes = jsonString.getBytes(Charset.forName("UTF-8"));
        logger.debug(bytes.toString());
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        String string = new String(bytes, Charset.forName("UTF-8"));
        Object obj = JSONObject.parse(string);
        logger.debug(obj != null ? obj.toString() : null);
        return obj;
    }
}
