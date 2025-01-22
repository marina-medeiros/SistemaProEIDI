package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.model.Aluno;
import br.ufrn.imd.sistemaproeidi.model.Pessoa;
import br.ufrn.imd.sistemaproeidi.model.Turma;
import br.ufrn.imd.sistemaproeidi.utils.InputUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChamadaController {

    @FXML private Button btn_confirmarChamada;
    @FXML private Label diaDeHoje;
    @FXML private VBox VBoxListaDePessoas;

    private Turma turma;
    private Map<Pessoa, Boolean> presenca = new HashMap<>();

    @FXML
    public void setTurma(Turma turma){
        this.turma = turma;
        carregarPessoas();
    }

    @FXML
    private void carregarPessoas() {
        VBoxListaDePessoas.getChildren().clear();
        try {
            for (Pessoa pessoa : turma.getAlunos()) {
                adicionarBlocoPessoa(pessoa);
            }
            for (Pessoa pessoa : turma.getEquipe()) {
                adicionarBlocoPessoa(pessoa);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar pessoas " + e.getMessage());
        }
    }

    @FXML
    void adicionarBlocoPessoa(Pessoa pessoa) {
        HBox blocoPessoa = new HBox();
        blocoPessoa.setPrefSize(670, 90);
        blocoPessoa.setStyle("-fx-background-color: #2F4A5F; -fx-border-radius: 10; -fx-padding: 10;");

        Pane panePessoa = new Pane();
        panePessoa.setPrefSize(670, 90);

        Label labelNome = new Label("NOME: " + pessoa.getNome());
        labelNome.setLayoutX(14);
        labelNome.setLayoutY(14);
        labelNome.setPrefSize(500, 30);
        labelNome.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        String tipoPessoa = (pessoa instanceof Aluno) ? "Aluno" : "Membro da Equipe";

        Label labelTipo = new Label(tipoPessoa);
        labelTipo.setLayoutX(14);
        labelTipo.setLayoutY(50);
        labelTipo.setPrefSize(500, 30);
        labelTipo.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        CheckBox checkBoxPresenca = new CheckBox("PRESENTE");
        checkBoxPresenca.setLayoutX(500);
        checkBoxPresenca.setLayoutY(15);
        checkBoxPresenca.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        presenca.put(pessoa, false);

        checkBoxPresenca.setOnAction(event -> {
            presenca.put(pessoa, checkBoxPresenca.isSelected());
        });

        panePessoa.getChildren().addAll(labelNome, labelTipo, checkBoxPresenca);
        blocoPessoa.getChildren().add(panePessoa);
        VBoxListaDePessoas.getChildren().add(blocoPessoa);
    }


    @FXML
    public void initialize() {
        diaDeHoje.setText("Data: " + InputUtils.formatLocalDate(LocalDate.now()));
    }

    @FXML
    public void clicarBtnConfirmarChamada(ActionEvent event) {
        if (exibirAlertaConfirmarChamada()) {
            registrarFaltas();
        }
    }

    private boolean exibirAlertaConfirmarChamada() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Confirmação");
        alerta.setHeaderText(null);
        alerta.setContentText("Chamada será registrada. Confirma?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private void registrarFaltas() {
        LocalDate hoje = LocalDate.now();

        for (Map.Entry<Pessoa, Boolean> entry : presenca.entrySet()) {
            Pessoa pessoa = entry.getKey();
            Boolean estaPresente = entry.getValue();

            if (!estaPresente) {
                if (!pessoa.getFaltas().contains(hoje)) {
                    pessoa.getFaltas().add(hoje);
                    System.out.println("Falta registrada para: " + pessoa.getNome());
                } else {
                    System.out.println("Falta já registrada para hoje: " + pessoa.getNome());
                }
            }

            System.out.println("Pessoa: " + pessoa.getNome() + " - Presença: " + estaPresente);
        }
    }
}
