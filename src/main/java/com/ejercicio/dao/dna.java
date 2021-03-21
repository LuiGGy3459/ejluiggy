package com.ejercicio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ejercicio.model.Dna;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface dna extends JpaRepository <Dna, Long> {
    @Query(value = "select count(t.resultado) from Dna t where t.resultado=true")
    Long CountMutant();
    @Query(value = "select count(t.resultado) from Dna t where t.resultado=false")
    Long CountHuman();
}
