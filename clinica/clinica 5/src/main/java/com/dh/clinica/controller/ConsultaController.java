package com.dh.clinica.controller;

import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Consulta;
import com.dh.clinica.service.impl.ConsultaServiceImpl;
import com.dh.clinica.service.impl.DentistaServiceImp;
import com.dh.clinica.service.impl.PacienteServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    final static Logger log = Logger.getLogger(DentistaController.class);

    private ConsultaServiceImpl consultaServiceImpl;
    private PacienteServiceImpl pacienteServiceImpl;
    private DentistaServiceImp dentistaServiceImpl;


    @Autowired
    public ConsultaController(ConsultaServiceImpl consultaServiceImpl) {
        this.consultaServiceImpl = consultaServiceImpl;
    }

    @Autowired
    public void PacienteController(PacienteServiceImpl pacienteServiceImpl) {
        this.pacienteServiceImpl = pacienteServiceImpl;
    }

    @Autowired
    public void DentistaController(DentistaServiceImp dentistaServiceImpl) {
        this.dentistaServiceImpl = dentistaServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Consulta> cadastrar(@RequestBody Consulta consulta) throws ResourceNotFoundException{
        log.info("Inciando método cadastrar...");
        ResponseEntity response = null;
        if (pacienteServiceImpl.buscarPorId(consulta.getPaciente().getId()).isPresent()
                &&
             dentistaServiceImpl.buscarPorId(consulta.getDentista().getId()).isPresent()) {
            log.info("Cadastrando Consulta: " + consulta.toString());
            response = ResponseEntity.ok(consultaServiceImpl.salvar(consulta));
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar a Consulta, pois não foram informados todos os atributos");
            throw new ResourceNotFoundException("Não foi possível cadastrar a Consulta");
        }
        return response;
    }
    @GetMapping
    public ResponseEntity<List<Consulta>> buscarTodos() throws ResourceNotFoundException{
        log.info("Iniciando método para buscar todos as consultas");
        ResponseEntity response = null;
        List<Consulta> consultas = consultaServiceImpl.buscarTodos();
        if(!consultas.isEmpty()) {
            log.info("Lista de consultas encontradas: " + consultas.toString());
            return ResponseEntity.ok(consultas);
        }else {
            ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não foi encontrado nenhum cadastrado");
            throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro na lista");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException{
        log.info("iniciando metodo buscarPorId");
        Consulta consulta = consultaServiceImpl.buscarPorId(id).orElse(null);
        if(Objects.nonNull(consulta)){
            log.info("Consulta presente para o id informado: " + consulta.toString());
            return ResponseEntity.ok(consulta);
        }else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma Consulta foi encontrado para o id: " + id);
            throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) throws ResourceNotFoundException{
        log.info("Iniciando o metodo excluir");
        ResponseEntity<String> response = null;
        if(consultaServiceImpl.buscarPorId(id).isPresent()){
            consultaServiceImpl.excluir(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Consulta excluida");
        }else
        {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta com esse Id nao encontrado: " + id);
            throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<Consulta> atualizar(@RequestBody Consulta consulta) throws ResourceNotFoundException {
        log.info("iniciando o metodo atualizar ");
        ResponseEntity response =null;
        if(consulta.getId() != null && consultaServiceImpl.buscarPorId(consulta.getId()).isPresent()){
            log.info("Atualizando a consulta com o id: " + consulta.getId());
            response = ResponseEntity.ok(consultaServiceImpl.atualizar(consulta));
        }else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma consulta com esse id foi encontrada: " + consulta.getId());
            throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");
        }
        return response;
    }

}
