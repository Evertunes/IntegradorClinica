package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.PacienteRequest;
import com.dh.clinica.controller.dto.PacienteResponse;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    //SerializationFeature.FAIL_ON_EMPTY_BEANS
    private static Logger log = Logger.getLogger(PacienteController.class);

    private PacienteServiceImpl pacienteService;

    @Autowired
    public PacienteController(PacienteServiceImpl pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteResponse> cadastrar(@RequestBody PacienteRequest paciente) throws ResourceNotFoundException{
        log.info("Inciando método de cadastro...");
        ResponseEntity response = null;
        if (!paciente.getRg().isEmpty()) {
            log.info("Cadastrando Paciente: " + paciente.toString());
            response = ResponseEntity.ok(pacienteService.salvar(paciente));
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar o Paciente, pois não foram informados todos os atributos");
            throw new ResourceNotFoundException("Não foi possível cadastrar o Paciente");
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Iniciando método de busca por ID...");
        PacienteResponse paciente = pacienteService.buscarPorId(id).orElse(null);
        if(Objects.nonNull(paciente)){
            log.info("Paciente encontrado para o id informado: "+ paciente.toString());
            return ResponseEntity.ok(paciente);
        }else{
            log.info("Nenhum Paciente foi encontrado para o ID: "+id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Integer id,  @RequestBody PacienteRequest paciente) throws ResourceNotFoundException {
        log.info("Iniciando método de atualização...");
        ResponseEntity response = null;
        if (pacienteService.buscarPorId(id).isPresent()) {
            log.info("Atualizando paciente com o id: "+ id);
            response = ResponseEntity.ok(pacienteService.atualizar(id, paciente));
        }
        else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum paciente com o id: " + id);
            throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) throws ResourceNotFoundException{
        log.info("Iniciando o metodo excluir");
        ResponseEntity<String> response = null;
        if(pacienteService.buscarPorId(id).isPresent()){
            pacienteService.excluir(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Paciente excluido");
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente com esse Id nao encontrado: " + id);
            throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");

        }
        return response;
    }


    @GetMapping
    public ResponseEntity <List<PacienteResponse>> buscarTodos () throws ResourceNotFoundException{
        log.info("Iniciando metodo de busca de todos os registros...");
        ResponseEntity response = null;
        List<PacienteResponse> pacientes = pacienteService.buscarTodos();
        if(!pacientes.isEmpty()){
            log.info("Lista de Pacientes encontrada: " + pacientes.toString());
            return ResponseEntity.ok(pacientes);
        } else{
            throw new ResourceNotFoundException("Não há Pacientes cadastrados");
        }
    }

}
