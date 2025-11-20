package com.mwm.portal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "abastecimentos")
public class Abastecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String cliente;
    private String veiculo;
    private String placa;
    private String produto;

    private BigDecimal volume;
    private BigDecimal odometro;

    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaTermino;

    private String status;
    private String usuario;
}