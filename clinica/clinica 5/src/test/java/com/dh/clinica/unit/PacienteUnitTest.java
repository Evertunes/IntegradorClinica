package com.dh.clinica.unit;

import com.dh.clinica.controller.dto.PacienteRequest;
import com.dh.clinica.controller.dto.PacienteResponse;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@Log4j2
public class PacienteUnitTest {

    // Injentando as dependências do service
    @Autowired
    PacienteServiceImpl service;

    @BeforeEach
    void criaUsuario(){
        Paciente paciente = new Paciente();
        paciente.setNome("Test Nome Paciente");
        paciente.setSobrenome("Test Sobrenome Paciente");
        paciente.setRg("153154534");
        paciente.setDataCadastro(LocalDate.parse("2022-04-05"));

        PacienteRequest pacienteRequest = toPacienteRequest(Optional.of(paciente));
        service.salvar(pacienteRequest);
    }

    //Teste do método Salvar
    @Test
    void salvar(){
        log.info("Criando um novo Usuario para salvar no Banco");
        //Criando um novo pacienteDTO para salvar no Banco
        Paciente paciente = new Paciente();
        paciente.setNome("Test Nome Paciente");
        paciente.setSobrenome("Test Sobrenome Paciente");
        paciente.setRg("153154534");
        paciente.setDataCadastro(LocalDate.parse("2022-04-05"));

        PacienteRequest pacienteRequest = toPacienteRequest(Optional.of(paciente));

        //Salvando o pacienteDTO utilizando o metodo salvar do Service
        service.salvar(pacienteRequest);

        //Confirmando que o paciente foi criado dentro do Banco de dados
        Assertions.assertTrue(service.buscarPorId(2).isPresent());

    }

    //  Testando o Buscar completo
    @Test
    void buscar(){
        log.info("Buscando por todas as entradas e validando a da posição 0");
        //Buscando por todas as entradas e validando se dentro dos resultados o RG do paciente criado está presente
        List<PacienteResponse> pacientes = service.buscarTodos();
        Assertions.assertTrue(pacientes.get(0).getRg().equals("153154534"));
    }

    //  Teste do método buscarPorId
    @Test
    void buscarPorId(){
        log.info("Buscando o paciente criado para o teste e verificando se ele foi encontrado (Status code 200)");
        //Buscando o paciente criado para o teste e verificando se ele foi encontrado (Status code 200)
        Optional<PacienteResponse> responseEntity = service.buscarPorId(1);
        Assertions.assertTrue(responseEntity.get().getNome().equals("Test Nome Paciente"));
    }

    private PacienteResponse toPacienteResponse(Optional<Paciente> paciente){

        return PacienteResponse.builder()
                .nome(paciente.get().getNome())
                .sobrenome(paciente.get().getSobrenome())
                .rg(paciente.get().getRg())
                .dataCadastro(paciente.get().getDataCadastro())
                .build();
    }
    
    private PacienteRequest toPacienteRequest(Optional<Paciente> paciente){
            return PacienteRequest.builder()
            .nome(paciente.get().getNome())
            .sobrenome(paciente.get().getSobrenome())
            .rg(paciente.get().getRg())
            .dataCadastro(paciente.get().getDataCadastro())
            .endereco(paciente.get().getEndereco())
            .build();
            
    }

}