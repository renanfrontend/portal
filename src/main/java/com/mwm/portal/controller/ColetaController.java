package com.mwm.portal.controller;

import com.mwm.portal.model.Coleta;
import com.mwm.portal.repository.ColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/coletas")
@RequiredArgsConstructor
public class ColetaController {

    private final ColetaRepository repository;

    @GetMapping
    public List<Coleta> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Coleta criar(@RequestBody Coleta item) {
        return repository.save(item);
    }
    
    @PutMapping("/{id}")
    public Coleta atualizar(@PathVariable UUID id, @RequestBody Coleta item) {
        item.setId(id);
        return repository.save(item);
    }
}