package com.capgemini.redis.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.capgemini.redis.RequestData;
import com.capgemini.redis.entity.Linha;
import com.capgemini.redis.repository.LinhaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LinhaService {
    
    private final LinhaRepository repository;
    private final RedisTemplate<String, String> redisTemplate;

    public String save(RequestData body) {

        Linha linha = Linha.builder()
                .id(UUID.randomUUID().toString())
                .status(body.getStatus())
                .data(body.getData())
                .number(body.getNumber())
                .build();

        repository.save(linha);

        return linha.getId();

    }

    public Linha getById(String id) {
        Optional<Linha> result = repository.findById(id);

        if (result.isPresent())
            return result.get();

        return Linha.builder().build();
    }

    public List<Linha> getAllByStatus(String status) {
        return repository.findByStatus(status);
    }

    public void updateStatus(String id, String status) {

        Optional<Linha> result = repository.findById(id);

        if (result.isPresent()) {
            Linha linha = result.get();
            linha.setStatus(status);
            repository.save(linha);
        }
    }

    public long countByStatus(String status) {
        numMax();
        return redisTemplate.opsForSet().size("linha:status:" + status);
    }

    public Linha numMax() {

        redisTemplate.opsForZSet().range("linha:numero", 0, -1);

        return null;
    }

}
