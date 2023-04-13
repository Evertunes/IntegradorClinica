package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.PacienteRequest;
import com.dh.clinica.controller.dto.PacienteResponse;
import com.dh.clinica.controller.dto.UsuarioResponse;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.model.Usuario;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IPacienteService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl  implements IPacienteService {
    private IPacienteRepository pacienteRepository;

    @Autowired
    public PacienteServiceImpl(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public PacienteResponse salvar(PacienteRequest request) {
    
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Paciente paciente = mapper.convertValue(request, Paciente.class);
        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        PacienteResponse pacienteResponse = mapper.convertValue(pacienteSalvo, PacienteResponse.class);
        return pacienteResponse;
    }

    @Override
    public List<PacienteResponse> buscarTodos() {
    
        
        List<Paciente> pacientes = pacienteRepository.findAll();
        
        List<PacienteResponse> responses = new ArrayList<>();
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        for(Paciente paciente: pacientes){
            responses.add(mapper.convertValue(paciente, PacienteResponse.class));    
        }
        return responses;
    }

    @Override
    public void excluir(Integer id) {
        pacienteRepository.deleteById(id);

    }

    @Override
    public Optional<PacienteResponse> buscarPorId(Integer id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        PacienteResponse pacienteResponse = toPacienteResponse(paciente);
        return Optional.ofNullable(pacienteResponse);
    }
   
    @Override
    public String atualizar(Integer id, PacienteRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Paciente paciente = mapper.convertValue(request, Paciente.class);
        paciente.setId(id);
        if(paciente.getId() != null && pacienteRepository.findById(paciente.getId()).isPresent()){
            Paciente pacienteSalvo =  pacienteRepository.saveAndFlush(paciente);
            mapper.convertValue(pacienteSalvo, PacienteResponse.class);
            return "Paciente atualizado";
        }
        return "Nao foi possivel atualizar o paciente";
    }
    
    private PacienteResponse toPacienteResponse(Optional<Paciente> paciente){
        return PacienteResponse.builder()
        .nome(paciente.get().getNome())
        .sobrenome(paciente.get().getSobrenome())
        .rg(paciente.get().getRg())
        .dataCadastro(paciente.get().getDataCadastro())
        .endereco(paciente.get().getEndereco())
        .build();
    }
    
}