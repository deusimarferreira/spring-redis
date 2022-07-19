package com.capgemini.redis.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@RedisHash("linha")
@Getter
@Setter
@Builder
public class Linha {
    
    @Id
    private String id;
    @Indexed
    private String status;
    private Data data;
    private Integer number;

}
