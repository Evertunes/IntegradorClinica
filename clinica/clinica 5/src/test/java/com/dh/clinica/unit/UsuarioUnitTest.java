package com.dh.clinica.unit;

import com.dh.clinica.controller.dto.PacienteResponse;
import com.dh.clinica.controller.dto.UsuarioRequest;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.model.dto.UsuarioDTO;
import com.dh.clinica.service.impl.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class UsuarioUnitTest {
    // Injentando as dependências do service
    @Autowired
    UsuarioServiceImpl service;

    @BeforeEach
    void criaUsuario() throws InvalidDataException {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setLogin("usertest");
        usuario.setNome("Test Usuario Before Each");
        usuario.setEmail("testbeforeeach@mail.com");
        usuario.setSenha("1234");
        usuario.setNivelAcesso("USER");

        UsuarioDTO usuarioDTO = toUsuarioDTO(Optional.of(usuario));
        service.salvar(usuarioDTO);
    }

    //Teste do método Salvar
    @Test
    void salvar() throws ResourceNotFoundException, InvalidDataException {
        log.info("Criando um novo Usuario para salvar no Banco");
        //Criando um novo dentistaDTO para salvar no Banco
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setLogin("usertest");
        usuario.setNome("Test Usuario");
        usuario.setEmail("test@mail.com");
        usuario.setSenha("1234");
        usuario.setNivelAcesso("USER");

        UsuarioDTO usuarioDTO = toUsuarioDTO(Optional.of(usuario));


        //Salvando o dentistaDTO utilizando o metodo salvar do Service
        UsuarioDTO usuarioCriado = service.salvar(usuarioDTO);

        //Confirmando que o dentista foi criado dentro do Banco de dados
        Assertions.assertTrue(service.buscarPorId(usuarioCriado.getId()) != null);

    }

//  Testando o Buscar completo
    @Test
    void buscar() throws ResourceNotFoundException, InvalidDataException {

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setLogin("usertest");
        usuario.setNome("Test Usuario");
        usuario.setEmail("test@mail.com");
        usuario.setSenha("1234");
        usuario.setNivelAcesso("USER");

        UsuarioDTO usuarioDTO = toUsuarioDTO(Optional.of(usuario));


        //Salvando o dentistaDTO utilizando o metodo salvar do Service
        UsuarioDTO usuarioCriado = service.salvar(usuarioDTO);
        //Confirmando que o dentista foi criado dentro do Banco de dados
        Assertions.assertFalse(service.buscarTodos().isEmpty());

    }

//  Teste do método buscarPorId
    @Test
    void buscarPorId() throws ResourceNotFoundException, InvalidDataException {

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setLogin("usertest");
        usuario.setNome("Test Usuario");
        usuario.setEmail("test@mail.com");
        usuario.setSenha("1234");
        usuario.setNivelAcesso("USER");

        UsuarioDTO usuarioDTO = toUsuarioDTO(Optional.of(usuario));


        //Salvando o dentistaDTO utilizando o metodo salvar do Service
        UsuarioDTO usuarioCriado = service.salvar(usuarioDTO);
        //Confirmando que o dentista foi criado dentro do Banco de dados
        Assertions.assertTrue(service.buscarPorId(usuarioCriado.getId()) != null);
        log.info("Buscando o dentista criado para o teste e verificando se ele foi encontrado (Status code 200)");
        //Buscando o dentista criado para o teste e verificando se ele foi encontrado (Status code 200)
        UsuarioDTO responseEntity = service.buscarPorId(1);
        Assertions.assertTrue(responseEntity.getNome().equals("Test Usuario"));
    }

//    private UsuarioResponse toUsuarioResponse(Optional<Usuario> usuario){
//
//        return UsuarioResponse.builder()
//                .email(usuario.get().getEmail())
//                .nome(usuario.get().getNome())
//                .nivelAcesso(usuario.get().getNivelAcesso())
//                .build();
//    }

    private UsuarioDTO toUsuarioDTO(Optional<Usuario> usuario){

        return UsuarioDTO.builder()
                .id(usuario.get().getId())
                .login(usuario.get().getLogin())
                .email(usuario.get().getEmail())
                .nome(usuario.get().getNome())
                .senha(usuario.get().getSenha())
                .nivelAcesso(usuario.get().getNivelAcesso())
                .build();
    }


}

