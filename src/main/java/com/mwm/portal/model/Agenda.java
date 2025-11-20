package com.mwm.portal.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "agendas")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cooperado;
    private String transportadora;
    private Integer seg;
    private Integer ter;
    private Integer qua;
    private Integer qui;
    private Integer sex;
    private Integer qtd;
    private Double km;
    private String status;
}