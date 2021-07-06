package com.kay.demo.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author Akay
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        String expiredKey = message.toString(); // 获取过期的key
        if (expiredKey.contains("TIME_KEY")) { // 判断是否是想要监听的过期key
            System.out.println("监听到 redis key过期：" + expiredKey);
            // TODO 业务逻辑
        }

    }
}
