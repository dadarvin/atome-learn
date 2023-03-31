package com.atome.atomelearn.config;

import com.atome.atomelearn.model.Customer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisTemplate<Integer, Customer> customerRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Integer, Customer> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        return template;
    }
}
