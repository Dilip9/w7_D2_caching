package com.example.cache.manager.config;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RedisConfiguration {

    @Bean
    RedisCacheManagerBuilderCustomizer customizer(){
        return builder -> builder.withCacheConfiguration("userCache",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(2)));
    }


    /**
     *
     * Create the bean of ConnectionFactory
     */
//    @Bean
//    public LettuceConnectionFactory lettuceConnectionFactory(){
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setPort(6379);
//        return new LettuceConnectionFactory(configuration);
//    }

    /**
     *
     * <K, HashMap></>
     *
     * @return
     */

//    @Bean
//    public RedisTemplate<String , Object> redisTemplate(){
//        RedisTemplate<String , Object> template = new RedisTemplate<>();
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new JdkSerializationRedisSerializer());
//        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
//        template.setConnectionFactory(lettuceConnectionFactory());
//        return template;
//    }




}
