package com.kay.demo.config;

import com.kay.demo.listener.KeyExpirationListener;
import com.kay.demo.listener.RedisKeyExpirationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

@Configuration
public class RedisListenerConfig {

    @Autowired
    private KeyExpirationListener keyExpirationListener;

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        //下面这种方式是灵活配置，针对每个库(0-15)的失效key做处理
        // 配置监听器监听的主题关联
        Topic topic = new PatternTopic("__keyevent@0__:expired");
        container.addMessageListener(keyExpirationListener, topic);

        return container;
    }


}
