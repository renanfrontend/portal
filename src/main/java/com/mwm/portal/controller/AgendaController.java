package com.mwm.portal.controller;

import com.mwm.portal.model.Agenda;
import com.mwm.portal.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agenda") // O front chama /api/agenda
@RequiredArgsConstructor
public class AgendaController {
    private final AgendaRepository repository;

    @GetMapping
    public List<Agenda> listar() {
        return repository.findAll();
    }
}