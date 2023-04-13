package com.dh.clinica.unit;

import com.dh.clinica.controller.dto.EnderecoRequest;
import com.dh.clinica.controller.dto.EnderecoResponse;
import com.dh.clinica.model.Endereco;
import com.dh.clinica.service.impl.EnderecoServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class EnderecoUnitTest {

    // Injentando as dependências do service
    @Autowired
    EnderecoServiceImpl service;

    @BeforeEach
    void criaUsuario(){
        Endereco endereco = new Endereco();
        endereco.setRua("Rua End");
        endereco.setNumero("Numero End");
        endereco.setCidade("Cidade End");
        endereco.setEstado("Estado End");

        EnderecoRequest enderecoRequest = toEnderecoRequest(Optional.of(endereco));
        service.salvar(enderecoRequest);
    }

    //Teste do método Salvar
    @Test
    void salvar(){
        log.info("Criando um novo Usuario para salvar no Banco");
        //Criando um novo endereco para salvar no Banco
        Endereco endereco = new Endereco();
        endereco.setRua("Rua End");
        endereco.setNumero("Numero End");
        endereco.setCidade("Cidade End");
        endereco.setEstado("Estado End");

        EnderecoRequest enderecoRequest = toEnderecoRequest(Optional.of(endereco));

        //Salvando o endereçoDTO utilizando o metodo salvar do Service
        service.salvar(enderecoRequest);

        //Confirmando que o endereço foi criado dentro do Banco de dados
        Assertions.assertTrue(service.buscarPorId(2).isPresent());

    }

    //  Testando o Buscar completo
    @Test
    void buscar(){
        log.info("Buscando por todas as entradas e validando a da posição 0");
        //Buscando por todas as entradas e validando se dentro dos resultados endereco criado está presente
        List<EnderecoResponse> enderecos = service.buscarTodos();
        Assertions.assertTrue(enderecos.get(0).getCidade().equals("Cidade End"));
    }

    //  Teste do método buscarPorId
    @Test
    void buscarPorId(){
        log.info("Buscando o endereco criado para o teste e verificando se ele foi encontrado (Status code 200)");
        //Buscando o endereco criado para o teste e verificando se ele foi encontrado (Status code 200)
        Optional<EnderecoResponse> responseEntity = service.buscarPorId(1);
        Assertions.assertTrue(responseEntity.get().getRua().equals("Rua End"));
    }

    private EnderecoResponse toEnderecoResponse(Optional<Endereco> endereco){

        return EnderecoResponse.builder()
                .rua(endereco.get().getRua())
                .numero(endereco.get().getNumero())
                .cidade(endereco.get().getCidade())
                .estado(endereco.get().getEstado())
                .build();
    }

    private EnderecoRequest toEnderecoRequest(Optional<Endereco> endereco){

        return EnderecoRequest.builder()
                .rua(endereco.get().getRua())
                .numero(endereco.get().getNumero())
                .cidade(endereco.get().getCidade())
                .estado(endereco.get().getEstado())
                .build();
    }
}
