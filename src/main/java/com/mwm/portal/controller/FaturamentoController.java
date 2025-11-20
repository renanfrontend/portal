package com.mwm.portal.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/faturamentos")
public class FaturamentoController {

    @GetMapping
    public List<FaturamentoItem> listar() {
        // Retorna os dados simulados para o gráfico de faturamento
        return Arrays.asList(
            new FaturamentoItem("Janeiro", 2774.38, "3.50"),
            new FaturamentoItem("Fevereiro", 2637.99, "3.72"),
            new FaturamentoItem("Março", 5027.0, "3.70"),
            new FaturamentoItem("Abril", 3847.0, "3.60"),
            new FaturamentoItem("Maio", 5122.71, "3.50"),
            new FaturamentoItem("Junho", 18231.53, "3.46"),
            new FaturamentoItem("Julho", 26145.7, "3.46"),
            new FaturamentoItem("Agosto", 30948.37, "3.47"),
            new FaturamentoItem("Setembro", 0.0, "0.0"),
            new FaturamentoItem("Outubro", 0.0, "0.0"),
            new FaturamentoItem("Novembro", 0.0, "0.0"),
            new FaturamentoItem("Dezembro", 0.0, "0.0")
        );
    }

    @Data
    static class FaturamentoItem {
        private final String name;
        private final Double faturamento;
        private final String label;
    }
}