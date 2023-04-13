package com.dh.clinica.service;

import java.util.List;
import java.util.Optional;

import com.dh.clinica.controller.dto.EnderecoRequest;
import com.dh.clinica.controller.dto.EnderecoResponse;

public interface IEnderecoService {

    
    EnderecoResponse salvar(EnderecoRequest enderecoRequest);
    List<EnderecoResponse> buscarTodos();
    void excluir(Integer id);
    Optional<EnderecoResponse> buscarPorId(Integer id);
    String atualizar(Integer id, EnderecoRequest request);
}
