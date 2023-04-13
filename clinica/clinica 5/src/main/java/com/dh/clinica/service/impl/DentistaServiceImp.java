package com.dh.clinica.service.impl;

import com.dh.clinica.controller.dto.DentistaRequest;
import com.dh.clinica.controller.dto.DentistaResponse;
import com.dh.clinica.model.Dentista;
import com.dh.clinica.repository.IDentistaRepository;
import com.dh.clinica.service.IDentistaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DentistaServiceImp implements IDentistaService {

    private IDentistaRepository dentistaRepository;

    @Autowired
    public DentistaServiceImp(IDentistaRepository dentistaRepository) {
        this.dentistaRepository = dentistaRepository;
    }


@Override
    public DentistaResponse salvar(DentistaRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Dentista dentista = mapper.convertValue(request, Dentista.class);
        Dentista dentistaSalvo = dentistaRepository.save(dentista);
        DentistaResponse DentistaResponse = mapper.convertValue(dentistaSalvo, DentistaResponse.class);
        return DentistaResponse;
    }

    @Override
    public List<DentistaResponse> buscarTodos() {
        List<Dentista> dentistas = dentistaRepository.findAll();
        List<DentistaResponse> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        
        for (Dentista dentista : dentistas){
            responses.add(mapper.convertValue(dentista, DentistaResponse.class));
        }
        return responses;
    }

    @Override
    public void excluir(Integer id) {
       dentistaRepository.deleteById(id);
    }

    @Override
    public Optional<DentistaResponse> buscarPorId(Integer id) {
        Optional<Dentista> dentista = dentistaRepository.findById(id);
        DentistaResponse dentistaResponse = toDentistaResponse(dentista);

        return Optional.ofNullable(dentistaResponse);
    }

    @Override
    public String atualizar(Integer id, DentistaRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        Dentista dentista = mapper.convertValue(request, Dentista.class);
        dentista.setId(id);
        if(dentista.getId() !=null && dentistaRepository.findById(dentista.getId()).isPresent()) {
            Dentista dentistaSalvo = dentistaRepository.saveAndFlush(dentista);
            mapper.convertValue(dentistaSalvo, DentistaResponse.class);
            return "Dentista atualizado";
        }

        return "Nao foi possivel atualizar o dentista";
        
    }

    private DentistaResponse toDentistaResponse(Optional<Dentista> dentista){

        return DentistaResponse.builder()
                .nome(dentista.get().getNome())
                .sobrenome(dentista.get().getSobrenome())
                .matricula(dentista.get().getMatricula())
                .build();
    }


}
