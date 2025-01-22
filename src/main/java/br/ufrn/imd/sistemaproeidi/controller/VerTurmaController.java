package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.model.Aluno;
import br.ufrn.imd.sistemaproeidi.model.MembroEquipe;
import br.ufrn.imd.sistemaproeidi.model.Turma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class VerTurmaController {
    @FXML
    private Label HorarioTurma, boolConcluida, NomeTurma, Curso, Vagas, DataInicio, DataTermino;

    @FXML
    private ListView<String> listViewAlunosTurma, listViewMembrosTurma;

    @FXML private Turma turma;

    @FXML
    public void setTurma(Turma turma){
        this.turma = turma;
        carregarDadosTurma();
        carregarAlunosTurma();
        carregarMembrosTurma();
    }

    private void carregarDadosTurma() {
        if (turma != null) {
            NomeTurma.setText(turma.getNome());
            Curso.setText(turma.getCurso().toString());
            HorarioTurma.setText(turma.getHorario().toString());
            Vagas.setText(turma.getNumeroVagas().toString());
            DataInicio.setText(turma.getDataInicio().toString());
            DataTermino.setText(turma.getDataTermino().toString());

            boolConcluida.setText(turma.getConcluido() ? "Sim" : "Não");

            System.out.println("Dados da turma carregados: " + turma.getNome());
        } else {
            System.out.println("Nenhuma turma foi carregada.");
        }
    }


    @FXML
    private void carregarAlunosTurma(){
        if (turma.getAlunos() != null) {
            ObservableList<String> alunos = FXCollections.observableArrayList();

            for (Aluno colegaTurma : turma.getAlunos()) {
                alunos.add(colegaTurma.getNome());
            }

            listViewAlunosTurma.setItems(alunos);
        }
    }

    @FXML
    private void carregarMembrosTurma(){
        if (turma.getAlunos() != null) {
            ObservableList<String> membros = FXCollections.observableArrayList();

            for (MembroEquipe membroEquipe : turma.getEquipe()) {
                membros.add(membroEquipe.getNome());
            }

            listViewMembrosTurma.setItems(membros);
        }
    }
}
