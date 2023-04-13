package com.dh.clinica.service;

import java.util.List;
import java.util.Optional;
import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;

public interface IDentistaService {
    DentistaResponse salvar(DentistaRequest dentistaRequest);
    List<DentistaResponse> buscarTodos();
    void excluir (Integer id);
    Optional<DentistaResponse> buscarPorId(Integer id);
    String atualizar(Integer id, DentistaRequest request);
      
}
