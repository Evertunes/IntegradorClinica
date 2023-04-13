package com.dh.clinica.controller.dto;

import java.time.LocalDate;

import com.dh.clinica.model.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class PacienteResponse {

    private String nome;
    private String sobrenome;
    private String rg;
    private LocalDate dataCadastro = LocalDate.now();
    private Endereco endereco;

    
}
