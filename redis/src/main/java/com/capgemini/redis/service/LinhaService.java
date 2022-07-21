package com.capgemini.redis.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public Linha save(RequestData body) {

        Linha linha = Linha.builder()
                .id(UUID.randomUUID().toString())
                .documentId(body.getDocumentId())
                .status(body.getStatus())
                .data(body.getData())
                .number((int) (Math.random() * 50 + 1))
                .build();

        repository.save(linha);

        return linha;

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


    public List<Linha> findByDocumentIdAndStatus(String documentId, String status) {
        return repository.findByDocumentIdAndStatus(documentId, status);
    }

    public long countByDocumentAndStatus(String documentId, String status) {
        Set<String> keys = new HashSet<>();
        keys.add("linha:documentId:" + documentId);
        keys.add("linha:status:" + status);
        // Long total = redisTemplate.opsForSet().getOperations().countExistingKeys(keys);
        int total = redisTemplate.opsForSet().intersect(keys).size();
        // int total2 = repository.findByDocumentIdAndStatus(documentId, status).size();
        return total;
    }

}
