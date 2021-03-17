package com.ejercicio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ejercicio.model.Dna;

public interface dna extends JpaRepository <Dna, Integer> {
}