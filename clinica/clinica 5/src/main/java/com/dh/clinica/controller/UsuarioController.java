package com.dh.clinica.controller;

import com.dh.clinica.controller.dto.UsuarioRequest;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.model.dto.UsuarioDTO;
import com.dh.clinica.service.impl.UsuarioServiceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

   private static Logger log = Logger.getLogger(UsuarioController.class);

   private UsuarioServiceImpl usuarioService;
   
   @Autowired
   public UsuarioController(UsuarioServiceImpl usuarioService){
        this.usuarioService = usuarioService;
   }
   
   @PostMapping
   public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody UsuarioDTO usuarioDTO) throws InvalidDataException{
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(usuarioDTO));
   }
   
   
   @GetMapping
   public ResponseEntity<List<UsuarioDTO>> buscarTodos() throws ResourceNotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarTodos());
   }
   
   @GetMapping("/{id}")
   public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarPorId(id));
   }
   
   
   @DeleteMapping("/{id}")
   public ResponseEntity excluir(@PathVariable Integer id) throws ResourceNotFoundException{
        usuarioService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }
   
   

    /* 
   @PostMapping
   public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioRequest usuario) throws ResourceNotFoundException{
       log.info("Iniciando o cadastro do usuario");

       ResponseEntity response = null;

       if(!usuario.getEmail().isEmpty()){
           log.info("Cadastrando Usuario..." + usuario.toString());
           response = ResponseEntity.ok(usuarioService.salvar(usuario));
       }else{
           ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível cadastrar o usuario, pois não foram informados todos os atributos necessarios");
           throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");
       }
       return response;
   }

   @GetMapping
   public ResponseEntity<List<UsuarioResponse>> buscarTodos() throws ResourceNotFoundException{
       log.info("Iniciando o metodo para buscar todos  os usuarios");
       ResponseEntity response = null;

       List<UsuarioResponse> usuarios = usuarioService.buscarTodos();

       if(!usuarios.isEmpty()){
           log.info("Lista de usuarios encontrados: " + usuarios.toString());
           return ResponseEntity.ok(usuarios);
       }else{
           throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro");
       }
   }
   @GetMapping("/{id}")
   public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Integer id) throws ResourceNotFoundException{
       log.info("Iniciando metodo buscarPorId");

       UsuarioResponse usuario = usuarioService.buscarPorId(id).orElse(null);
       if (Objects.nonNull(usuario)) {
           log.info("Usuario presente para o id informado: " + usuario.toString());
           return ResponseEntity.ok(usuario);
       }else {
           ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario com esse ID nao foi encontrado: " + id);
           throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");
       }
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<String> excluir(@PathVariable Integer id) throws ResourceNotFoundException {
       log.info("Iniciando o metodo excluir");
       ResponseEntity<String> response = null;
       if(usuarioService.buscarPorId(id).isPresent()){
           usuarioService.excluir(id);
           response = ResponseEntity.status(HttpStatus.OK).body("Usuario excluido");
       }else {
           ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario com esse ID nao foi encontrado: " + id);
           throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");
       }
       return  response;
   }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody UsuarioRequest usuario) throws ResourceNotFoundException{
        log.info("Iniciando o metodo atualizar");
        ResponseEntity response = null;
        if(usuarioService.buscarPorId(id).isPresent()){
            log.info("Atualizando o usuario com o id: " + id);
            response = ResponseEntity.ok(usuarioService.atualizar(id, usuario));
        }else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuario com esse id foi encontrado: " + id);
            throw new ResourceNotFoundException("Não foi encontrado nenhum cadastro com esse id");
        }
        return response;
    }
    */
}
