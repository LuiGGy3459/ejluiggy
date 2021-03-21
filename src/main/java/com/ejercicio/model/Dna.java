package com.ejercicio.model;
import javax.persistence.*;

@Entity
@Table(name="DnaData")
public class Dna {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private int Id;

    @Column(name="Dna")
    private String Dna;

    @Column(name="resultado")
    private Boolean resultado;

    public Dna() {}

    public Dna(int Id, String dna, Boolean resultado){
        this.Id = Id;
        this.Dna = dna;
        this.resultado = resultado;
    }

    //getters and setters, Metodos que permiten actualizar los valores.
    public int getId() { return Id; }
    public void setId(int id) { Id = id; }
    public String getDna() { return Dna; }
    public void setDna(String dna) { this.Dna = dna; }
    public Boolean setresultado() { return resultado; }
    public void getresultado(boolean resultado) { this.resultado = resultado; }

    @Override
    public String toString() {

        return "ID Query [Id=" + Id + ", DNA=" + Dna + ", Result=" + resultado + "]";

    }

}


