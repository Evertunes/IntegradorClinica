package com.dh.clinica.service;

import java.util.List;
import java.util.Optional;

import com.dh.clinica.controller.dto.PacienteRequest;
import com.dh.clinica.controller.dto.PacienteResponse;


public interface IPacienteService {

    PacienteResponse salvar(PacienteRequest pacienteRequest);
    List<PacienteResponse> buscarTodos();
    void excluir(Integer id);
    Optional<PacienteResponse> buscarPorId(Integer id);
    String atualizar(Integer id, PacienteRequest request);
    
}
