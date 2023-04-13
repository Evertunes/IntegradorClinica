package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.EnderecoRequest;
import com.dh.clinica.controller.dto.EnderecoResponse;
import com.dh.clinica.model.Endereco;
import com.dh.clinica.repository.IEnderecoRepository;
import com.dh.clinica.service.IEnderecoService;
import com.dh.clinica.service.IService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements IEnderecoService {
    private IEnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoServiceImpl(IEnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }


    @Override
    public EnderecoResponse salvar(EnderecoRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Endereco endereco = mapper.convertValue(request, Endereco.class);
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        EnderecoResponse enderecoResponse = mapper.convertValue(enderecoSalvo, EnderecoResponse.class);
        return enderecoResponse;
    }

    @Override
    public List<EnderecoResponse> buscarTodos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        List<EnderecoResponse> responses = new ArrayList<>();
        
        ObjectMapper mapper = new ObjectMapper();
        for (Endereco endereco : enderecos){
            responses.add(mapper.convertValue(endereco, EnderecoResponse.class));
        }
        return responses;
    }

    @Override
    public void excluir(Integer id) {
        enderecoRepository.deleteById(id);
    }

    @Override
    public Optional<EnderecoResponse> buscarPorId(Integer id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        EnderecoResponse enderecoResponse = toEnderecoResponse(endereco);

        return Optional.ofNullable(enderecoResponse);
    }


    @Override
    public String atualizar(Integer id, EnderecoRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Endereco endereco = mapper.convertValue(request, Endereco.class);
        endereco.setId(id);
        if (endereco.getId() != null && enderecoRepository.findById(endereco.getId()).isPresent()){
            Endereco enderecoSalvo =  enderecoRepository.saveAndFlush(endereco);
            mapper.convertValue(enderecoSalvo, EnderecoResponse.class);
            return "Endereço Atualizado";
        }
        return "Não foi possível atualizar o Endereço";
    }
    
    private EnderecoResponse toEnderecoResponse(Optional<Endereco> endereco){
        return EnderecoResponse.builder()
        .rua(endereco.get().getRua())
        .numero(endereco.get().getNumero())
        .cidade(endereco.get().getCidade())
        .estado(endereco.get().getEstado())
        .build();
    }
}
