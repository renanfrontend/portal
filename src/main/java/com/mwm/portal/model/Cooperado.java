package com.mwm.portal.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cooperados")
public class Cooperado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long matricula;
    private String filial;
    private String motorista;
    private String tipoVeiculo;
    private String placa;
    private String certificado;
    private String doamDejetos;
    private String fase;
}