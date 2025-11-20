package com.mwm.portal.controller;

import com.mwm.portal.model.Cooperado;
import com.mwm.portal.repository.CooperadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cooperados")
@RequiredArgsConstructor
public class CooperadoController {

    private final CooperadoRepository repository;

    @GetMapping
    public List<Cooperado> listar() {
        return repository.findAll();
    }
}