package br.ufrn.imd.sistemaproeidi.controller;
import br.ufrn.imd.sistemaproeidi.utils.*;

import br.ufrn.imd.sistemaproeidi.model.enums.Curso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import br.ufrn.imd.sistemaproeidi.model.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class PrincipalAlunoController {
    @FXML private Label CursoAtual, HorarioTurma, NomeProfessor1, NomeProfessor2, NumeroDeTelefone, SO, nomeTurma, nomeUsuario, totalFaltas, DataTermino, DataInicio;
    @FXML private TabPane tabPane;
    @FXML private ListView<String> ListViewCursos, listViewFaltas, listViewAlunosTurma;
    @FXML private Tab perfilTab, turmaTab;
    @FXML private Button btn_perfil, btn_turma, btn_perfil1, btn_turma1;

    private Aluno aluno;
    private Turma turmaAluno;

    private Stage principalSceneAluno;

    public void setPrincipalSceneAluno(Stage stage) {
        this.principalSceneAluno = stage;
    }

    @FXML
    public void initialize() {
        System.out.println("Tela Principal Aluno carregada!");
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
        aluno.detalharAluno(); //OPCIONAL
        carregarDadosAluno();
        carregarCursosFeitos();
        carregarFaltas();
        carregarAlunosTurma();
    }

    public void setTurmaAluno(){
        this.turmaAluno = Gerenciador.buscarTurma(this.aluno.getCodigoTurma());
    }

    private void carregarDadosAluno() {
        if (aluno != null) {
            setTurmaAluno();
            nomeUsuario.setText(aluno.getNome());
            CursoAtual.setText(InputUtils.formatEnum(aluno.getCursoAtual().toString()));
            HorarioTurma.setText(InputUtils.formatEnum(turmaAluno.getHorario().toString()));
            NumeroDeTelefone.setText(aluno.getNumeroCelular());
            SO.setText(aluno.getSistemaOperacional().toString());
            nomeTurma.setText(turmaAluno.getNome());
            DataTermino.setText(InputUtils.formatLocalDate(turmaAluno.getDataTermino()));
            DataInicio.setText(InputUtils.formatLocalDate(turmaAluno.getDataInicio()));

            System.out.println("Dados do aluno carregados: " + aluno.getNome());
        }
    }

    private void carregarCursosFeitos(){
        if (aluno.getCursosFeitos() != null) {
            ObservableList<String> cursos = FXCollections.observableArrayList();

            for (Curso curso : aluno.getCursosFeitos()) {
                cursos.add(InputUtils.formatEnum(curso.toString()));
            }

            ListViewCursos.setItems(cursos);
        }
    }

    private void carregarFaltas() {
        if (aluno.getFaltas() != null) {
            ObservableList<String> faltas = FXCollections.observableArrayList();
            if (totalFaltas != null) {
                totalFaltas.setText(Integer.toString(aluno.getFaltas().size()));
            }
            for (LocalDate falta : aluno.getFaltas()) {
                faltas.add(InputUtils.formatLocalDate(falta));
            }
            listViewFaltas.setItems(faltas);
        } else {
            if (totalFaltas != null) {
                totalFaltas.setText("0");
            }
        }
    }

    private void carregarAlunosTurma(){
        if (turmaAluno.getAlunos() != null) {
            ObservableList<String> alunos = FXCollections.observableArrayList();

            for (Aluno colegaTurma : turmaAluno.getAlunos()) {
                alunos.add(colegaTurma.getNome());
            }

            listViewAlunosTurma.setItems(alunos);
        }
    }

    @FXML
    void clicarBtnPerfil(ActionEvent event) {
        tabPane.getSelectionModel().select(perfilTab);
        System.out.println("Botão PERFIL clicado.");
    }

    @FXML
    void clicarBtnTurma(ActionEvent event) {
        tabPane.getSelectionModel().select(turmaTab);
        System.out.println("Botão TURMA clicado.");
    }
}
