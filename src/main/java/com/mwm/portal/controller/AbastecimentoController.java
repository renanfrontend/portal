package com.mwm.portal.controller;

import com.mwm.portal.model.Abastecimento;
import com.mwm.portal.repository.AbastecimentoRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/abastecimentos")
@RequiredArgsConstructor
public class AbastecimentoController {

    private final AbastecimentoRepository repository;

    // 1. Relatório Principal (Lista)
    @GetMapping("/report")
    public List<Abastecimento> relatorio() {
        return repository.findAll();
    }

    // 2. Adicionar Abastecimento
    @PostMapping("/report")
    public Abastecimento adicionar(@RequestBody Abastecimento item) {
        if (item.getStatus() == null) item.setStatus("Concluído");
        return repository.save(item);
    }

    // 3. Gráfico: Volume por Dia
    @GetMapping("/volume-por-dia")
    public List<VolumePorDia> volumePorDia() {
        Map<String, Double> agrupado = repository.findAll().stream()
            .collect(Collectors.groupingBy(
                a -> a.getData().toString(),
                Collectors.summingDouble(a -> a.getVolume().doubleValue())
            ));

        return agrupado.entrySet().stream()
            .map(e -> new VolumePorDia(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(VolumePorDia::getData))
            .collect(Collectors.toList());
    }

    // 4. Gráfico: Volume Agregado (Dia/Semana/Mês) - Simplificado para Mês
    @GetMapping("/aggregated-volume")
    public List<VolumeItem> volumeAgregado(@RequestParam(defaultValue = "month") String period) {
        // Lógica simplificada: Agrupa sempre por data ou mês para exibir no gráfico
        Map<String, Double> agrupado = repository.findAll().stream()
            .collect(Collectors.groupingBy(
                a -> period.equals("day") ? a.getData().toString() : 
                     a.getData().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")),
                Collectors.summingDouble(a -> a.getVolume().doubleValue())
            ));

        return agrupado.entrySet().stream()
            .map(e -> new VolumeItem(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }
    
    // 5. Volume por Mês (para Faturamento)
    @GetMapping("/volume-por-mes")
    public List<VolumeItem> volumePorMes() {
        return volumeAgregado("month");
    }

    // 6. Sumário por Veículo
    @GetMapping("/summary")
    public List<SummaryItem> summary() {
        Map<String, Double> agrupado = repository.findAll().stream()
            .collect(Collectors.groupingBy(
                a -> a.getVeiculo() + " - " + a.getPlaca(),
                Collectors.summingDouble(a -> a.getVolume().doubleValue())
            ));

        return agrupado.entrySet().stream()
            .map(e -> {
                String[] parts = e.getKey().split(" - ");
                return new SummaryItem(parts[0], parts.length > 1 ? parts[1] : "", e.getValue());
            })
            .collect(Collectors.toList());
    }

    // DTOs Auxiliares para resposta JSON
    @Data
    static class VolumePorDia {
        private final String data;
        private final Double volumeTotal;
    }
    
    @Data
    static class VolumeItem {
        private final String name;
        private final Double volume;
    }
    
    @Data
    static class SummaryItem {
        private final String veiculo;
        private final String placa;
        private final Double volumeTotal;
    }
}