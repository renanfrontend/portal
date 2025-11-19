package com.mwm.portal.controller;

import com.mwm.portal.model.QualidadeDejetos;
import com.mwm.portal.repository.QualidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qualidade-dejetos")
@RequiredArgsConstructor
public class QualidadeController {

    private final QualidadeRepository repository;

    @GetMapping
    public List<QualidadeDejetos> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public QualidadeDejetos create(@RequestBody QualidadeDejetos analise) {
        return repository.save(analise);
    }
}