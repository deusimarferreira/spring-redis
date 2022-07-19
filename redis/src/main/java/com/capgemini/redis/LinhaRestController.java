package com.capgemini.redis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.redis.entity.Linha;
import com.capgemini.redis.service.LinhaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LinhaRestController {

    private final LinhaService service;

    @PostMapping("/linha")
    public ResponseEntity<String> salvar(@RequestBody RequestData body) {
        return ResponseEntity.ok(service.save(body));
    }

    @GetMapping("/linha/{id}")
    public ResponseEntity<Linha> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/linha/{id}/{status}")
    public ResponseEntity<String> updateStatus(
            @PathVariable String id,
            @PathVariable String status
    ) {
        service.updateStatus(id, status);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/linha/{status}/all")
    public ResponseEntity<List<Linha>> getAllByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.getAllByStatus(status));
    }

    @GetMapping("/linha/{status}/count")
    public ResponseEntity<Long> countByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.countByStatus(status));
    }
    
}
