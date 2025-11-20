package com.mwm.portal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "coletas")
public class Coleta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String cooperado;
    private String motorista;
    private String tipoVeiculo;
    private String placa;
    private Double odometro;

    private LocalDate dataPrevisao;
    private LocalTime horaPrevisao;

    private String status; // "Pendente", "Entregue", etc.
}