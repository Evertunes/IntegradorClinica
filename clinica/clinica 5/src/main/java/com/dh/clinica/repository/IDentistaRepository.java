package com.dh.clinica.repository;

import com.dh.clinica.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDentistaRepository extends JpaRepository<Dentista, Integer> {

    //Optional<Dentista> findDentistaByNameCointainingIgnoreCase(String nome);
}
