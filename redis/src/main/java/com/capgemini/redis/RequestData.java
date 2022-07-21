package com.capgemini.redis;

import com.capgemini.redis.entity.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestData {
    private String status;
    private Data data;
    private String documentId;
}
