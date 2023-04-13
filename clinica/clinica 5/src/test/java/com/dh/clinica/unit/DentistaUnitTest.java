package com.dh.clinica.unit;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.service.impl.DentistaServiceImp;
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
public class DentistaUnitTest {
    // Injentando as dependências do service
    @Autowired
    DentistaServiceImp service;

    @BeforeEach
    void criaUsuario(){
        Dentista dentista = new Dentista();
        dentista.setNome("Test Nome Dentista");
        dentista.setSobrenome("Test Sobrenome Dentista");
        dentista.setMatricula("123456");

        DentistaRequest dentistaRequest = toDentistaRequest(Optional.of(dentista));
        service.salvar(dentistaRequest);
    }

    //Teste do método Salvar
    @Test
    void salvar(){
        log.info("Criando um novo Usuario para salvar no Banco");
        //Criando um novo dentistaDTO para salvar no Banco
        Dentista dentista = new Dentista();
        dentista.setNome("Test Nome Dentista");
        dentista.setSobrenome("Test Sobrenome Dentista");
        dentista.setMatricula("123456");

        DentistaRequest dentistaRequest = toDentistaRequest(Optional.of(dentista));

        //Salvando o dentistaDTO utilizando o metodo salvar do Service
        service.salvar(dentistaRequest);

        //Confirmando que o dentista foi criado dentro do Banco de dados
        Assertions.assertTrue(service.buscarPorId(2).isPresent());

    }

    //  Testando o Buscar completo
    @Test
    void buscar(){
        log.info("Buscando por todas as entradas e validando a da posição 0");
        //Buscando por todas as entradas e validando se dentro dos resultados o cro do dentista criado está presente
        List<DentistaResponse> dentistas = service.buscarTodos();
        Assertions.assertTrue(dentistas.get(0).getMatricula().equals("123456"));
    }

    //  Teste do método buscarPorId
    @Test
    void buscarPorId(){
        log.info("Buscando o dentista criado para o teste e verificando se ele foi encontrado (Status code 200)");
        //Buscando o dentista criado para o teste e verificando se ele foi encontrado (Status code 200)
        Optional<DentistaResponse> responseEntity = service.buscarPorId(1);
        Assertions.assertTrue(responseEntity.get().getNome().equals("Test Nome Dentista"));
    }

    private DentistaResponse toDentistaResponse(Optional<Dentista> dentista){

        return DentistaResponse.builder()
                .nome(dentista.get().getNome())
                .sobrenome(dentista.get().getSobrenome())
                .matricula(dentista.get().getMatricula())
                .build();
    }

    private DentistaRequest toDentistaRequest(Optional<Dentista> dentista){

        return DentistaRequest.builder()
                .nome(dentista.get().getNome())
                .sobrenome(dentista.get().getSobrenome())
                .matricula(dentista.get().getMatricula())
                .build();
    }
}
