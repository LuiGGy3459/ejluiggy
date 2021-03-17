package com.ejercicio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

public class Dna {
    @Id
    private Integer Id;

    @Column
    private String dna;

    //getters and setters, Metodos que permiten actualizar los valores.
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public String getDna() {
        return dna;
    }
    public void setDna(String dna) {
        this.dna = dna;
    }

}
