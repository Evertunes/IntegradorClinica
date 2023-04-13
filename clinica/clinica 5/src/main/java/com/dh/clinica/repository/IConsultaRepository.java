package com.dh.clinica.repository;

import com.dh.clinica.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConsultaRepository extends JpaRepository<Consulta, Integer> {
//    Optional<Dentista> findDentistaByNomeContainingIgnoreCase(String nome);
}
