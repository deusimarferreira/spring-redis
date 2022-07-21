package com.capgemini.redis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.redis.entity.Linha;
import com.capgemini.redis.service.LinhaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/linhas")
public class LinhaRestController {

    private final LinhaService service;

    @PostMapping
    public ResponseEntity<Linha> salvar(@RequestBody RequestData body) {
        return ResponseEntity.ok(service.save(body));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Linha> get(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<String> updateStatus(
            @PathVariable String id,
            @PathVariable String status
    ) {
        service.updateStatus(id, status);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{status}/all")
    public ResponseEntity<List<Linha>> getAllByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.getAllByStatus(status));
    }

    @GetMapping("/{status}/count")
    public ResponseEntity<Long> countByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.countByStatus(status));
    }

    @GetMapping("/{documentId}/all/{status}")
    public ResponseEntity<List<Linha>> buscarLinhasDeArquivoComStatus(
            @PathVariable String documentId,
            @PathVariable String status
    ) {
        return ResponseEntity.ok(service.findByDocumentIdAndStatus(documentId, status));
    }

    @GetMapping("/{documentId}/count/{status}")
    public ResponseEntity<Long> buscarQuantidadeLinhasDeArquivoComStatus(
            @PathVariable String documentId,
            @PathVariable String status
    ) {
        return ResponseEntity.ok(service.countByDocumentAndStatus(documentId, status));
    }
    
}
