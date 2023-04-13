package com.dh.clinica.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
    T salvar(T t);
    List<T> buscarTodos();
    void excluir(Integer id);
    Optional<T> buscarPorId(Integer id);
    //Optional<T> buscarPorNome(String nome);
    String atualizar (T t);
//    Optional<T> buscarPorNome(String nome) {
//      return IService.buscarPorNome(nome);
// }
    }
