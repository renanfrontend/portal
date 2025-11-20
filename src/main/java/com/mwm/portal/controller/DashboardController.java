package com.mwm.portal.controller;

import com.mwm.portal.repository.AbastecimentoRepository;
import com.mwm.portal.repository.CooperadoRepository;
import com.mwm.portal.repository.PortariaRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final PortariaRepository portariaRepo;
    private final AbastecimentoRepository abastecimentoRepo;
    private final CooperadoRepository cooperadoRepo;

    @GetMapping
    public DashboardData getDashboard() {
        DashboardData data = new DashboardData();

        // 1. Métricas (Calculadas do Banco)
        long totalCooperados = cooperadoRepo.count();
        long veiculosNoPatio = portariaRepo.findAll().stream()
                .filter(p -> "Em processo".equals(p.getStatus()) || "Pesagem".equals(p.getStatus()))
                .count();
        
        // Exemplo: Calculando volume total de abastecimentos
        double volumeTotal = abastecimentoRepo.findAll().stream()
                .mapToDouble(a -> a.getVolume().doubleValue())
                .sum();

        data.setMetrics(Arrays.asList(
            new Metric(1, "density_medium", "Total Cooperados", totalCooperados, "neutral", ""),
            new Metric(2, "water_drop", "Volume Abastecido", volumeTotal, "up", "M³"),
            new Metric(3, "timer", "TMO diário", "16:00:00", "up", ""),
            new Metric(4, "power_settings_new", "Status Operacional", "Operando", "up", "")
        ));

        // 2. Estoque (Simulado para manter o layout, já que não temos tabela de estoque no PDF)
        data.setStock(Arrays.asList(
            new StockItem(1, "Fertilizantes", 74480, 78400, "t", "is-link"),
            new StockItem(2, "Bio Metano", 65000, 100000, "M³", "is-success"),
            new StockItem(3, "CO₂", 38000, 100000, "M³", "is-warning")
        ));

        // 3. Gráfico de Cooperados (Simulado ou Agregado)
        data.setCooperativeAnalysis(Arrays.asList(
            new AnalysisItem("Ademir E.", 2.5, "#334bff"),
            new AnalysisItem("Renato I.", 3.5, "#334bff"),
            new AnalysisItem("Carlos P.", 1.5, "#ef4444")
        ));
        
        // 4. Dados do Gráfico de Abastecimento (Vindo do Banco)
        // Mapeia os abastecimentos reais para o gráfico do dashboard
        List<AbastecimentoItem> absItems = abastecimentoRepo.findAll().stream()
            .limit(5) // Pega os 5 primeiros para não lotar o gráfico
            .map(a -> new AbastecimentoItem(a.getVeiculo(), a.getVolume().doubleValue()))
            .toList();
            
        data.setAbastecimentos(absItems);

        return data;
    }

    // --- DTOs Internos para responder o JSON exato que o Frontend pede ---
    
    @Data
    static class DashboardData {
        private List<Metric> metrics;
        private List<StockItem> stock;
        private List<AnalysisItem> cooperativeAnalysis;
        private List<AbastecimentoItem> abastecimentos;
    }

    @Data
    static class Metric {
        private int id;
        private String icon;
        private String label;
        private Object value; // Pode ser String ou Number
        private String trend;
        private String unit;

        public Metric(int id, String icon, String label, Object value, String trend, String unit) {
            this.id = id; this.icon = icon; this.label = label; this.value = value; this.trend = trend; this.unit = unit;
        }
    }

    @Data
    static class StockItem {
        private int id;
        private String label;
        private double value;
        private double capacity;
        private String unit;
        private String color;

        public StockItem(int id, String label, double value, double capacity, String unit, String color) {
            this.id = id; this.label = label; this.value = value; this.capacity = capacity; this.unit = unit; this.color = color;
        }
    }

    @Data
    static class AnalysisItem {
        private String name;
        private double value;
        private String color;

        public AnalysisItem(String name, double value, String color) {
            this.name = name; this.value = value; this.color = color;
        }
    }
    
    @Data
    static class AbastecimentoItem {
        private String veiculo;
        private double m3;
        
        public AbastecimentoItem(String veiculo, double m3) {
            this.veiculo = veiculo; this.m3 = m3;
        }
    }
}