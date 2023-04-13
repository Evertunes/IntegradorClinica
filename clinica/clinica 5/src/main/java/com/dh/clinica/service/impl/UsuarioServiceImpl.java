package com.dh.clinica.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import com.dh.clinica.exception.InvalidDataException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.model.dto.UsuarioDTO;
import com.dh.clinica.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    
    
    @Autowired
    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) throws InvalidDataException {
        if(validaUsuario(usuarioDTO)){
            Usuario usuarioEntity = toUsuario(usuarioDTO);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String senhaCriptografada = encoder.encode(usuarioEntity.getSenha());
            usuarioEntity.setSenha(senhaCriptografada);
            Usuario usuarioSalvo = usuarioRepository.save(usuarioEntity);
            return toUsuarioDTO(usuarioSalvo);
        } else{
            throw new InvalidDataException("Não foi possível cadastra o usuário!");
        }
//        ObjectMapper mapper = new ObjectMapper();
//        Usuario usuario = mapper.convertValue(request, Usuario.class);
//        Usuario usuarioSalvo = usuarioRepository.save(usuario);
//        UsuarioResponse usuarioResponse = mapper.convertValue(usuarioSalvo, UsuarioResponse.class);
//        return usuarioResponse;
    }

    @Override
    public List<UsuarioDTO> buscarTodos() throws ResourceNotFoundException {
        List<Usuario> listaUsuarioEntity = usuarioRepository.findAll();
        List<UsuarioDTO> listaUsuarioDTO = new ArrayList<>();
        if(!listaUsuarioEntity.isEmpty()){
            for(Usuario usuario : listaUsuarioEntity){
                listaUsuarioDTO.add(toUsuarioDTO(usuario));
            }
            return listaUsuarioDTO;
        } else{
            throw new ResourceNotFoundException("Não há usuários cadastrados");
        }
        //        List<Usuario> usuarios = usuarioRepository.findAll();
//        List<UsuarioResponse> responses = new ArrayList<>();
//
//        ObjectMapper mapper = new ObjectMapper();
//        for (Usuario usuario : usuarios){
//            responses.add(mapper.convertValue(usuario, UsuarioResponse.class));
//        }
//        return responses;
    }

    @Override
    public void excluir(Integer id) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(Objects.nonNull(usuario)){
            usuarioRepository.deleteById(id);
        } else{
            throw new ResourceNotFoundException("Usuário não encontrado!");
        }
        //       usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioDTO buscarPorId(Integer id) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(Objects.nonNull(usuario)){
            return toUsuarioDTO(usuario);
        } else{
            throw new ResourceNotFoundException("Usuário não encontrado!");
        }
//        Optional<Usuario> usuario = usuarioRepository.findById(id);
//        UsuarioResponse usuarioResponse = toUsuarioResponse(usuario);
//
//        return Optional.ofNullable(usuarioResponse);
    }

    @Override
    public String atualizar(Integer id, UsuarioDTO usuario) {
        return null;
    }

//    @Override
//    public String atualizar(Integer id, UsuarioRequest request) {
//        ObjectMapper mapper = new ObjectMapper();
//        Usuario usuario = mapper.convertValue(request, Usuario.class);
//        usuario.setId(id);
//        if(usuario.getId() !=null && usuarioRepository.findById(usuario.getId()).isPresent()) {
//            Usuario usuarioSalvo = usuarioRepository.saveAndFlush(usuario);
//            mapper.convertValue(usuarioSalvo, UsuarioResponse.class);
//            return "Usuario atualizado";
//        }
//
//        return "Nao foi possivel atualizar o usuario";
//
//    }
private Usuario toUsuario(UsuarioDTO usuarioDTO) {
    return Usuario.builder()
            .id(usuarioDTO.getId())
            .login(usuarioDTO.getLogin())
            .nome(usuarioDTO.getNome())
            .email(usuarioDTO.getEmail())
            .senha(usuarioDTO.getSenha())
            .nivelAcesso(usuarioDTO.getNivelAcesso())
            .build();
}


    private UsuarioDTO toUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .login(usuario.getLogin())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .nivelAcesso(usuario.getNivelAcesso())
                .build();
    }

    private boolean validaUsuario(UsuarioDTO usuarioDTO) {
        return usuarioDTO.getLogin() != null &&
                !usuarioDTO.getLogin().isEmpty() &&
                !usuarioDTO.getLogin().isBlank() &&
                usuarioDTO.getSenha() != null &&
                !usuarioDTO.getSenha().isEmpty() &&
                !usuarioDTO.getSenha().isBlank();
    }
    
}
