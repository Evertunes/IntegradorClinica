package com.dh.clinica.service;

import com.dh.clinica.controller.dto.UsuarioRequest;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.dto.UsuarioDTO;

import javax.sound.midi.InvalidMidiDataException;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    UsuarioDTO salvar(UsuarioDTO usuario) throws InvalidDataException;
    List<UsuarioDTO> buscarTodos() throws ResourceNotFoundException;
    void excluir(Integer id) throws ResourceNotFoundException;
    UsuarioDTO buscarPorId(Integer id) throws ResourceNotFoundException;
    String atualizar(Integer id, UsuarioDTO usuario);
}
