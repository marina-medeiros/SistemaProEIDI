package br.ufrn.imd.sistemaproeidi.model;

import br.ufrn.imd.sistemaproeidi.model.enums.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Vector;

public class Turma implements Serializable {
    private Vector<Aluno> alunos = new Vector<Aluno>();
    private Vector<MembroEquipe> equipe = new Vector<MembroEquipe>();

    private String nome;
    private Curso curso;
    private Horario horario;
    private Integer numeroVagas;
    private Boolean concluido = false;
    private LocalDate DataInicio;
    private LocalDate DataTermino;
    private String codigo;

    public Turma(String nome, Curso curso, Horario horario, Integer numeroVagas, LocalDate dataInicio, LocalDate dataTermino) {
        this.nome = nome;
        this.curso = curso;
        this.horario = horario;
        this.numeroVagas = numeroVagas;
        this.concluido = false;
        this.DataInicio = dataInicio;
        this.DataTermino = dataTermino;

        this.codigo = BancoDAO.getInstance().gerarCodigoTurma(curso.toString(), dataInicio);
    }

    public Turma(){}

    public String getNome() {
        return nome;
    }

    public Curso getCurso() {
        return curso;
    }

    public Horario getHorario() {
        return horario;
    }

    public LocalDate getDataInicio() {
        return DataInicio;
    }

    public LocalDate getDataTermino() {
        return DataTermino;
    }

    public Boolean getConcluido() {
        return concluido;
    }

    public Integer getNumeroVagas() {
        return numeroVagas;
    }

    public Vector<Aluno> getAlunos() {
        if (alunos == null) {
            alunos = new Vector<>();
        }
        return alunos;
    }

    public String getCodigo() {
        return codigo;
    }

    public Vector<MembroEquipe> getEquipe() {
        if (equipe == null) {
            equipe = new Vector<>();
        }
        return equipe;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setAlunos(Vector<Aluno> alunos) {
        this.alunos = alunos;
    }

    public void setEquipe(Vector<MembroEquipe> equipe) {
        this.equipe = equipe;
    }

    public void setNumeroVagas(Integer numeroVagas) {
        this.numeroVagas = numeroVagas;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }

    @Override
    public String toString() {
        return nome;
    }

}
