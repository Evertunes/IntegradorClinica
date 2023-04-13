package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.service.impl.DentistaServiceImp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/dentistas")
public class DentistaController {

    final static Logger log = Logger.getLogger(DentistaController.class);

    private DentistaServiceImp dentistaServiceImp;

    @Autowired
    public DentistaController(DentistaServiceImp dentistaServiceImp) {
        this.dentistaServiceImp = dentistaServiceImp;
    }


    @PostMapping
    public ResponseEntity<DentistaResponse> cadastrar(@RequestBody DentistaRequest dentista) throws ResourceNotFoundException {
        log.info("Inciando método cadastrar...");
        ResponseEntity response = null;
        if (!dentista.getMatricula().isEmpty()) {
            log.info("Cadastrando Dentista: " + dentista.toString());
            response = ResponseEntity.ok(dentistaServiceImp.salvar(dentista));
        } else {
            log.info("Não foi possível cadastrar o Dentista, pois não foram informados todos os atributos");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar o Dentista, pois não foram informados todos os atributos");
            throw new ResourceNotFoundException("Não foi criar um novo Dentista");

        }
        return response;
    }
    @GetMapping
    public ResponseEntity<List<DentistaResponse>> buscarTodos() throws ResourceNotFoundException{
        log.info("Iniciando método para buscar todos os dentistas");
        ResponseEntity response = null;
        List<DentistaResponse> dentistas = dentistaServiceImp.buscarTodos();
        if(!dentistas.isEmpty()) {
            log.info("Lista de dentistas encontrados: " + dentistas.toString());
            return ResponseEntity.ok(dentistas);
        }else {
            throw new ResourceNotFoundException("Nao foi encontrado nenhum cadastro");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistaResponse> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException{
        log.info("iniciando metodo buscarPorId");
        DentistaResponse dentista = dentistaServiceImp.buscarPorId(id).orElse(null);
        if(Objects.nonNull(dentista)){
            log.info("Detista presente para o id informado: " + dentista.toString());
            return ResponseEntity.ok(dentista);
        }else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum Dentista foi encontrado para o id: " +id);
            throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Integer id) throws ResourceNotFoundException{
        log.info("Iniciando o metodo excluir");
        ResponseEntity<String> response = null;
        if(dentistaServiceImp.buscarPorId(id).isPresent()){
            dentistaServiceImp.excluir(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Dentista excluido");
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dentista com esse Id nao encontrado: " + id);
            throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");

        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dentista> atualizar(@PathVariable Integer id, @RequestBody DentistaRequest dentista) throws ResourceNotFoundException{
        log.info("iniciando o metodo atualizar ");
        ResponseEntity response =null;
        if(dentistaServiceImp.buscarPorId(id).isPresent()){
            log.info("Atualizando o dentista com o id: " + id);
            response = ResponseEntity.ok(dentistaServiceImp.atualizar(id, dentista));
        } else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum dentista com esse id foi encontrado: " + id);
            throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");
        }
        return response;
    }
}
