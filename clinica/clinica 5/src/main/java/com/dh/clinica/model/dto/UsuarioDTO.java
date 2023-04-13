package com.dh.clinica.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsuarioDTO {
    private Integer id;
    private String login;
    private String nome;
    private String email;
    private String senha;
    private String nivelAcesso;
}
