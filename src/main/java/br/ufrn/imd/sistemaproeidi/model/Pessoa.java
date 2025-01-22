package br.ufrn.imd.sistemaproeidi.model;

import br.ufrn.imd.sistemaproeidi.model.enums.Genero;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Vector;

public abstract class Pessoa implements Serializable{
    private String nome;
    private String CPF;
    private Genero genero;
    private String numeroCelular;
    private Vector<LocalDate> faltas = new Vector<LocalDate>();

    public Pessoa(String nome, String CPF, Genero genero, String numeroCelular) {
        this.nome = nome;
        this.CPF = CPF;
        this.genero = genero;
        this.numeroCelular = numeroCelular;
    }

    public Vector<LocalDate> getFaltas() {
        return faltas;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getCPF() {
        return CPF;
    }

    public String getNome() {
        return nome;
    }


}