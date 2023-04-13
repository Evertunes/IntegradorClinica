package com.dh.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dh.clinica.controller.dto.EnderecoRequest;
import com.dh.clinica.controller.dto.EnderecoResponse;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Endereco;
import com.dh.clinica.service.impl.EnderecoServiceImpl;

import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private static Logger log = Logger.getLogger(EnderecoController.class);

    private EnderecoServiceImpl enderecoService;

    @Autowired
    public EnderecoController(EnderecoServiceImpl enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    public ResponseEntity<EnderecoResponse> salvar(@RequestBody EnderecoRequest endereco) {
        log.info("Inciando método de cadastro...");
        ResponseEntity response = null;
        if(!endereco.getCidade().isEmpty()) {
            log.info("Cadastrando Endereco" + endereco.toString());
            response = ResponseEntity.ok(enderecoService.salvar(endereco));
        }else {
            log.info("Não foi possível cadastrar o Endereco, pois não foram informados todos os atributos");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @GetMapping("/{id}")
    public  ResponseEntity<EnderecoResponse> buscarPorId(@PathVariable Integer id){
        log.info("Iniciando método de busca por ID...");
        EnderecoResponse endereco = enderecoService.buscarPorId(id).orElse(null);
        if(Objects.nonNull(endereco)){
            log.info("Endereco encontrado para o id informado: " +id);
            return ResponseEntity.ok(endereco);
        }else {
            log.info("Nenhum endereco foi  encontrado para o id informado: " +id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable Integer id,  @RequestBody EnderecoRequest endereco) throws Exception {
        log.info("Iniciando método de atualização...");
        ResponseEntity response = null;

        if(enderecoService.buscarPorId(id).isPresent()){
            log.info("Atualizando endereco com o id: " + id);
            response = ResponseEntity.ok(enderecoService.atualizar(id, endereco));
        }else {
            log.info("Nao foi encontrado nenhum endereco com o id: " + id);
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir (@PathVariable Integer id){
        ResponseEntity<String> response = null;
        if(enderecoService.buscarPorId(id).isPresent()){
            enderecoService.excluir(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Endereco excluido");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @GetMapping
    public ResponseEntity <List<EnderecoResponse>> buscarTodos() throws ResourceNotFoundException{
        log.info("Iniciando metodo de busca de todos os registros...");
        ResponseEntity response = null;
        List<EnderecoResponse>enderecos = enderecoService.buscarTodos();
        if(!enderecos.isEmpty()){
            log.info("Lista de enderecos encontrada: " + enderecos.toString());
            return ResponseEntity.ok(enderecos);
        }else {
            throw new ResourceNotFoundException("Nao há enderecos cadastrados");
        }
    }
    
}
