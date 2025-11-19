package com.mwm.portal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "portaria_registros")
public class PortariaRegistro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private LocalDate data;
    private LocalTime horario;
    
    private String empresa;
    private String motorista;
    private String tipoVeiculo;
    private String placa;
    private String atividade;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String cpfCnpj;
    private String balancaEntrada;
    private String balancaSaida;
    private LocalTime horarioCheckIn;
    private LocalTime horarioCheckOut;

    public enum Categoria {
        Entregas, Abastecimentos, Coletas, Visitas
    }

    public enum Status {
        Pendente, Em_processo, Pesagem, Concluido
    }
}