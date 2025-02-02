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
    public void initialize() {
        diaDeHoje.setText("Data: " + InputUtils.formatLocalDate(LocalDate.now()));
    }

    @FXML
    void adicionarBlocoPessoa(Pessoa pessoa) {
        HBox blocoPessoa = criarHBox(670, 90, "-fx-background-color: #2F4A5F; -fx-border-radius: 10; -fx-padding: 10;");
        Pane panePessoa = criarPane(670, 90);

        Label labelNome = criarLabel("NOME: " + pessoa.getNome(), 14, 14, 500, 30, "-fx-text-fill: white; -fx-font-size: 14;");
        String tipoPessoa = (pessoa instanceof Aluno) ? "Aluno" : "Membro da Equipe";
        Label labelTipo = criarLabel(tipoPessoa, 14, 50, 500, 30, "-fx-text-fill: white; -fx-font-size: 14;");

        CheckBox checkBoxPresenca = criarCheckBox("PRESENTE", 500, 15, "-fx-text-fill: white; -fx-font-size: 14;");
        presenca.put(pessoa, false); // Inicializa a presença como `false`

        checkBoxPresenca.setOnAction(event -> presenca.put(pessoa, checkBoxPresenca.isSelected()));

        panePessoa.getChildren().addAll(labelNome, labelTipo, checkBoxPresenca);
        blocoPessoa.getChildren().add(panePessoa);
        VBoxListaDePessoas.getChildren().add(blocoPessoa);
    }

    private CheckBox criarCheckBox(String text, double x, double y, String style) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.setLayoutX(x);
        checkBox.setLayoutY(y);
        checkBox.setStyle(style);
        return checkBox;
    }

    private HBox criarHBox(double width, double height, String style) {
        HBox hbox = new HBox();
        hbox.setPrefSize(width, height);
        hbox.setStyle(style);
        return hbox;
    }

    private Pane criarPane(double width, double height) {
        Pane pane = new Pane();
        pane.setPrefSize(width, height);
        return pane;
    }

    private Label criarLabel(String text, double x, double y, double width, double height, String style) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setPrefSize(width, height);
        label.setStyle(style);
        return label;
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
                }
            }
        }
    }
}
