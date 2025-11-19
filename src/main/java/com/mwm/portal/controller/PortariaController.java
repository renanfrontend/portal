package com.mwm.portal.controller;

import com.mwm.portal.model.PortariaRegistro;
import com.mwm.portal.repository.PortariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portaria")
@RequiredArgsConstructor
public class PortariaController {

    private final PortariaRepository repository;

    @GetMapping
    public List<PortariaRegistro> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public PortariaRegistro create(@RequestBody PortariaRegistro registro) {
        registro.setStatus(PortariaRegistro.Status.Pendente);
        return repository.save(registro);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PortariaRegistro> updateStatus(
            @PathVariable UUID id, 
            @RequestBody PortariaRegistro updates) {
        
        return repository.findById(id).map(record -> {
            if (updates.getStatus() != null) record.setStatus(updates.getStatus());
            if (updates.getBalancaEntrada() != null) record.setBalancaEntrada(updates.getBalancaEntrada());
            if (updates.getBalancaSaida() != null) record.setBalancaSaida(updates.getBalancaSaida());
            
            if (record.getStatus() == PortariaRegistro.Status.Em_processo && record.getHorarioCheckIn() == null) {
                record.setHorarioCheckIn(LocalTime.now());
            }
            if (record.getStatus() == PortariaRegistro.Status.Concluido) {
                record.setHorarioCheckOut(LocalTime.now());
            }

            return ResponseEntity.ok(repository.save(record));
        }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}