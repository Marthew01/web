/*
package com.web.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.service.redis
 * @date 2021/1/15/015 14:08
 *//*

@Service
public class RedisService<T> {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    public void setObj(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public T getObj(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }



    private Map<String,Object> redis;

    public RedisService() {
        redis=new HashMap<>();
    }
    */
/***
     * 读取缓存
     * *//*

    public  Object getCache(String key){
        if (isCached(key)){
            Object bean=redis.get(key);
            System.out.println("【redis】读取缓存成功 key="+key+"\t value="+bean);
            return bean;
        }else {
            System.out.println("【redis】--key 不存在");
            return  null;
        }
    }
    */
/**
     * 写入缓存
     * *//*

    public boolean writeCache(String key,Object bean){
        if (!isCached(key)){
            redis.put(key,bean);
            System.out.println("【redis】写入缓存成功:key="+key+"\t value="+bean);
            return true;
        }else {
            System.out.println("【redis】--key 已存在");
            return  false;
        }
    }
    */
/**
     * 判断是否存在缓存
     * *//*

    public  boolean isCached(String key){
        return redisTemplate.hasKey(key);
    }
    */
/***
     * 修改缓存
     * *//*

    public boolean setCache(String key,Object bean){
        if (isCached(key)){
            redis.put(key,bean);
            return true;
        }else {
            System.out.println("key 不存在");
            return  false;
        }
    }


}
*/
