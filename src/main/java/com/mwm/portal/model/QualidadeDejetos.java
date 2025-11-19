package com.mwm.portal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Entity
@Table(name = "qualidade_dejetos")
public class QualidadeDejetos {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate dataColeta;
    private String cooperado;
    private String placa;
    
    private Double ph;
    private Double densidade;
    private String entregaReferencia;

    @JsonProperty("id_recipiente_amostra")
    private String idRecipienteAmostra;
    
    @JsonProperty("peso_recip_amostra")
    private String pesoRecipAmostra;
    
    @JsonProperty("pesagem_p2_amostra")
    private String pesagemP2Amostra;
    
    @JsonProperty("pesagem_p3_amostra")
    private String pesagemP3Amostra;
    
    @JsonProperty("pesagem_p4_amostra")
    private String pesagemP4Amostra;

    @JsonProperty("id_recipiente_duplicata")
    private String idRecipienteDuplicata;
    
    @JsonProperty("peso_recip_duplicata")
    private String pesoRecipDuplicata;
    
    @JsonProperty("pesagem_p2_duplicata")
    private String pesagemP2Duplicata;
    
    @JsonProperty("recip_st_duplicata")
    private String recipStDuplicata;
    
    @JsonProperty("recip_sf_duplicata")
    private String recipSfDuplicata;
}