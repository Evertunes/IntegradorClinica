package com.dh.clinica.service.impl;

import com.dh.clinica.model.Consulta;
import com.dh.clinica.repository.IConsultaRepository;
import com.dh.clinica.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaServiceImpl implements IService<Consulta> {
    private IConsultaRepository consultaRepository;

    @Autowired
    public void IConsultaRepository(IConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }


    @Override
    public Consulta salvar(Consulta consulta) {
        Consulta c = consultaRepository.save(consulta);
        return c;
    }

    @Override
    public List<Consulta> buscarTodos() {
        return consultaRepository.findAll();
    }

    @Override
    public void excluir(Integer id) {
        consultaRepository.deleteById(id);
    }

    @Override
    public Optional<Consulta> buscarPorId(Integer id) {
        return consultaRepository.findById(id);
    }


    @Override
    public String atualizar(Consulta consulta) {
        if (consulta.getId() != null && consultaRepository.findById(consulta.getId()).isPresent()){
            consultaRepository.saveAndFlush(consulta);
            return "Consulta Atualizada";
        }
        return "Não foi possível atualizar a Consulta";
    }
}
