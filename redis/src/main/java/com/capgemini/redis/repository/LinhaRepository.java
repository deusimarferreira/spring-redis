package com.capgemini.redis.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.redis.entity.Linha;

@Repository
public interface LinhaRepository extends CrudRepository<Linha, String> {

    public List<Linha> findByStatus(String status);
    
}
