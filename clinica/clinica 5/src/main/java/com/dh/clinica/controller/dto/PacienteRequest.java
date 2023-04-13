package com.dh.clinica.controller.dto;

import java.time.LocalDate;
import com.dh.clinica.controller.dto.PacienteResponse.PacienteResponseBuilder;
import com.dh.clinica.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PacienteRequest {

    private String nome;
    private String sobrenome;
    private String rg;
    private LocalDate dataCadastro = LocalDate.now();
    private Endereco endereco;
    
    /* 
    public static PacienteResponseBuilder builder() {
        return null;
    } 
    */
}
