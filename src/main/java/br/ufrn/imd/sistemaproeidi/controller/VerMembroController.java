package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.model.*;
import br.ufrn.imd.sistemaproeidi.utils.InputUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.util.ArrayList;

public class VerMembroController {
    @FXML private MembroEquipe membroEquipe;

    @FXML private ListView<String> listViewFaltas, listViewTurmas;
    @FXML private ChoiceBox<Turma> opcoesTurmas;
    @FXML private Label cursoUFRN, email, totalFaltas, matricula, nomeUsuario, numeroDeTelefone, CPF, Genero;

    private ArrayList<Turma> turmas = BancoDAO.getInstance().getArrayTurmas();

    public void initialize() {
        System.out.println("Tela Ver Membro carregada!");
        opcoesTurmas.setItems(FXCollections.observableArrayList(turmas));
    }

    public void setMembroEquipe(MembroEquipe membroEquipe) {
        this.membroEquipe = membroEquipe;
        carregarDadosMembro();
        carregarFaltas();
        carregarTurmasParticipantes();
    }

    private void carregarDadosMembro() {
        if (membroEquipe != null) {
            nomeUsuario.setText(membroEquipe.getNome());
            CPF.setText(membroEquipe.getCPF());
            Genero.setText(membroEquipe.getGenero().toString());
            cursoUFRN.setText(membroEquipe.getCursoUFRN());
            email.setText(membroEquipe.getEmail());
            matricula.setText(membroEquipe.getMatricula());
            numeroDeTelefone.setText(membroEquipe.getNumeroCelular());

            System.out.println("Membro carregado: " + membroEquipe.getNome());
        } else {
            System.out.println("Nenhum membro foi carregado.");
        }
    }

    private void carregarFaltas(){
        if (membroEquipe.getFaltas() != null) {
            ObservableList<String> listaFaltas = FXCollections.observableArrayList();
            if (totalFaltas != null) {
                totalFaltas.setText(Integer.toString(membroEquipe.getFaltas().size()));
            }

            for (LocalDate falta : membroEquipe.getFaltas()) {
                listaFaltas.add(InputUtils.formatLocalDate(falta));
            }

            listViewFaltas.setItems(listaFaltas);
        } else {
            if (totalFaltas != null) {
                totalFaltas.setText("0");
            }
        }
    }

    private void carregarTurmasParticipantes() {
        if (membroEquipe.getCodigosTurmas() != null) {
            ObservableList<String> turmas = FXCollections.observableArrayList();

            for (String codigoTurma : membroEquipe.getCodigosTurmas()) {
                Turma turma = Gerenciador.buscarTurma(codigoTurma);
                if (turma != null) {
                    turmas.add(turma.getNome());
                } else {
                    turmas.add("Código inválido: " + codigoTurma);
                }
            }

            listViewTurmas.setItems(turmas);
        } else {
            listViewTurmas.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    public void clicarBtnAdicionarMembroTurma(ActionEvent event) {
        Turma turma = (Turma) opcoesTurmas.getValue();
        membroEquipe.adicionarMembroDaEquipeATurma(turma, membroEquipe);
        opcoesTurmas.setValue(null);
        carregarTurmasParticipantes();
    }

    @FXML
    public void clicarBtnRemoverMembroTurma(ActionEvent event) {
        Turma turma = (Turma) opcoesTurmas.getValue();
        membroEquipe.removerMembroDaEquipeDaTurma(turma, membroEquipe);
        opcoesTurmas.setValue(null);
        carregarTurmasParticipantes();
    }

}
