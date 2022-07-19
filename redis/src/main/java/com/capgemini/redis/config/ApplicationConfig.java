package com.capgemini.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import io.lettuce.core.ReadFrom;

@Configuration
@EnableRedisRepositories
public class ApplicationConfig {

    @Value("${app.redis.sentinel.host}")
    private String host;

    @Value("${app.redis.sentinel.port}")
    private Integer port;

    @Value("${app.redis.sentinel.master}")
    private String master;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        // RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        // config.setHostName("localhost");
        // config.setPort(26379);
        // config.setPassword("q1w2e3");
        // config.setDatabase(0);

        RedisSentinelConfiguration config = new RedisSentinelConfiguration()
                .master(master)
                .sentinel(host, port);

        LettuceClientConfiguration client = LettuceClientConfiguration.builder()
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();

        return new LettuceConnectionFactory(config, client);
    }

    @Bean
    @Primary
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
