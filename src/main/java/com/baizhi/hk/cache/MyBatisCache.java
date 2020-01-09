package com.baizhi.hk.cache;

import com.baizhi.hk.util.MyWebWare;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MyBatisCache implements Cache {

    private final String id;

    public MyBatisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        redisTemplate.opsForHash().put(this.id,key.toString(),value);
    }

    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        Object o = redisTemplate.opsForHash().get(this.id, key.toString());
        return o;
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public void clear() {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        redisTemplate.delete(this.id);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
