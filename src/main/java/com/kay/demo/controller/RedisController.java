package com.kay.demo.controller;

import com.kay.demo.utils.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource(name="redisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource(name="redisTemplate1")
    private RedisTemplate<Object, Object> redisTemplate1;

    @GetMapping("/{db}/{key}")
    public String get(@PathVariable Integer db, @PathVariable String key) {
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        if(db == 1){
            redisUtil = new RedisUtil(redisTemplate1);
        }
        return (String) redisUtil.get(key);
    }

    @GetMapping("/{db}/{key}/{value}")
    public String set(@PathVariable Integer db,@PathVariable String key, @PathVariable String value) {
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        if(db == 1){
            redisUtil = new RedisUtil(redisTemplate1);
        }
        redisUtil.set(key, value);
        return "OK";
    }
}
