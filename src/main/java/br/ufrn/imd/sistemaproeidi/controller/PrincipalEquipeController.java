package br.ufrn.imd.sistemaproeidi.controller;

import br.ufrn.imd.sistemaproeidi.SistemaApplication;
import br.ufrn.imd.sistemaproeidi.model.*;
import br.ufrn.imd.sistemaproeidi.model.enums.*;
import br.ufrn.imd.sistemaproeidi.utils.InputUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class PrincipalEquipeController {

    @FXML private Tab perfilTab, turmasTab,cadastrarAlunoTab,cadastrarEquipeTab,cadastrarTurmaTab, pessoasTab;
    @FXML private TabPane tabPane;

    @FXML private VBox VBoxListaDeTurmas, VBoxListaDePessoas;

    @FXML private TextField cadastroAlunoCPF, cadastroAlunoNome, cadastroAlunoObsSaude, cadastroAlunoTelefone, cadastroEquipeCPF, cadastroEquipeCursoUFRN, cadastroEquipeEmail, cadastroEquipeMatricula, cadastroEquipeNome, cadastroEquipeTelefone;
    @FXML private TextField cadastroTurmaNome, cadastroTurmaVagas;
    @FXML private DatePicker cadastroAlunoDataNascimento, cadastroTurmaDataInicio, cadastroTurmaDataTermino;
    @FXML private ChoiceBox<Curso> cadastroTurmaCurso;
    @FXML private ChoiceBox<Genero> cadastroAlunoGenero, cadastroEquipeGenero;
    @FXML private ChoiceBox<SistemaOperacional> cadastroAlunoSO;
    @FXML private ChoiceBox<Escolaridade> cadastroAlunoEscolaridade;
    @FXML private ChoiceBox<Cargo> cadastroEquipeCargo;
    @FXML private ChoiceBox<Turma> cadastroAlunoTurmaDisponiveis;
    @FXML private ChoiceBox<Horario> cadastroTurmaHorario;
    @FXML private CheckBox checkAlunoComputador, checkAlunoInternet, checkAlunoSmartphone;
    @FXML private Label nomeUsuario, cursoUFRN, email, faltas, matricula, numeroDeTelefone;


    private MembroEquipe membroEquipe;
    private ArrayList<Pessoa> pessoas = BancoDAO.getInstance().getArrayPessoas();
    private ArrayList<Turma> turmas = BancoDAO.getInstance().getArrayTurmas();

    @FXML
    public void initialize() {
        System.out.println("Tela Principal Equipe carregada!");

        carregarChoiceBoxes();
        carregarTurmas();
        carregarPessoas();
    }

    public void setMembroEquipe(MembroEquipe membroEquipe) {
        this.membroEquipe = membroEquipe;
        membroEquipe.detalharMembroEquipe();
        carregarDadosMembroEquipe();
    }

    public void carregarDadosMembroEquipe(){
        if(membroEquipe != null){
            nomeUsuario.setText(membroEquipe.getNome());
            cursoUFRN.setText(membroEquipe.getCursoUFRN());
            email.setText(membroEquipe.getEmail());
            faltas.setText(Integer.toString(membroEquipe.getFaltas().size()));
            matricula.setText(membroEquipe.getMatricula());
            numeroDeTelefone.setText(membroEquipe.getNumeroCelular());
        }
    }

    public void carregarChoiceBoxes(){
        cadastroAlunoGenero.setItems(FXCollections.observableArrayList(Genero.values()));
        cadastroEquipeGenero.setItems(FXCollections.observableArrayList(Genero.values()));
        cadastroAlunoSO.setItems(FXCollections.observableArrayList(SistemaOperacional.values()));
        cadastroAlunoEscolaridade.setItems(FXCollections.observableArrayList(Escolaridade.values()));
        cadastroAlunoTurmaDisponiveis.setItems(FXCollections.observableArrayList(turmas));
        cadastroEquipeCargo.setItems(FXCollections.observableArrayList(Cargo.values()));
        cadastroTurmaCurso.setItems(FXCollections.observableArrayList(Curso.values()));
        cadastroTurmaHorario.setItems(FXCollections.observableArrayList(Horario.values()));
        cadastroAlunoDataNascimento.setValue(LocalDate.of(1964,06,20));
    }

    @FXML
    private void carregarTurmas() {
        VBoxListaDeTurmas.getChildren().clear();
        try {
            for (Turma turma : turmas) {

                adicionarBlocoTurma(turma);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar turmas " + e.getMessage());
        }
    }

    @FXML
    private void carregarPessoas() {
        VBoxListaDePessoas.getChildren().clear();
        try {
            for (Pessoa pessoa : pessoas) {
                String nome = pessoa.getNome();

                adicionarBlocoPessoa(pessoa);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar pessoas " + e.getMessage());
        }
    }

    @FXML
    void clicarBtnPerfil(ActionEvent event) {
        tabPane.getSelectionModel().select(perfilTab);
    }

    @FXML
    void clicarBtnTurmas(ActionEvent event) {
        tabPane.getSelectionModel().select(turmasTab);
    }

    @FXML
    void clicarBtnCadastrarAluno(ActionEvent event) {
        tabPane.getSelectionModel().select(cadastrarAlunoTab);
    }

    @FXML
    void clicarBtnCadastrarEquipe(ActionEvent event) {
        tabPane.getSelectionModel().select(cadastrarEquipeTab);
    }

    @FXML
    void clicarBtnPessoas(ActionEvent event) {
        tabPane.getSelectionModel().select(pessoasTab);
    }

    @FXML
    void clicarBtnTabCadastrarTurma(ActionEvent event) {
        tabPane.getSelectionModel().select(cadastrarTurmaTab);
    }

    @FXML
    public void clicarBtnCadastrarAlunoFinal(ActionEvent event) {

        try {
            String nome = InputUtils.validarNome(cadastroAlunoNome.getText());
            String dataNascimentoTexto = cadastroAlunoDataNascimento.getEditor().getText();
            System.out.println(dataNascimentoTexto);
            LocalDate dataNascimento = InputUtils.validarData(dataNascimentoTexto);
            String cpf = InputUtils.validarCPF(cadastroAlunoCPF.getText());
            Genero genero = (Genero) cadastroAlunoGenero.getValue();
            String numeroCelular = InputUtils.validarTelefone(cadastroAlunoTelefone.getText());
            Escolaridade escolaridade = (Escolaridade) cadastroAlunoEscolaridade.getValue();
            Turma turma = (Turma) cadastroAlunoTurmaDisponiveis.getValue();
            String obsSaude = cadastroAlunoObsSaude.getText();
            boolean temInternet = checkAlunoInternet.isSelected();
            boolean temComputador = checkAlunoComputador.isSelected();
            boolean temSmartphone = checkAlunoSmartphone.isSelected();
            SistemaOperacional sistemaOperacional = (SistemaOperacional) cadastroAlunoSO.getValue();

            if (nome == null ||
                cpf == null ||
                genero == null ||
                numeroCelular == null ||
                escolaridade == null ||
                turma == null) {
                exibirAlerta("Cadastro impedido", "Por favor, preencha os campos corretamente.");
                return;
            }

            if (dataNascimento.isAfter(LocalDate.now())) {
                exibirAlerta("Data inválida", "A data de nascimento não pode ser futura.");
                return;
            }

            if (membroEquipe.matricularAluno(nome, cpf, genero, dataNascimento, numeroCelular, escolaridade, obsSaude, temInternet, temComputador, temSmartphone, sistemaOperacional, turma)) {
                LimparCamposAluno();
                exibirAlertaCadastroConcluido();
                carregarPessoas();
            } else {
                exibirAlerta("Cadastro impedido", "Verifique a idade do aluno e o número de vagas da turma.");
            }

        } catch (Exception e) {
            exibirAlerta("Erro inesperado", "Ocorreu um erro ao tentar cadastrar o aluno. Por favor, tente novamente.");
        }
    }


    @FXML
    public void clicarBtnCadastrarEquipeFinal(ActionEvent event) {

        try {
            String nome = InputUtils.validarNome(cadastroEquipeNome.getText());
            String cpf = InputUtils.validarCPF(cadastroEquipeCPF.getText());
            String numeroCelular = InputUtils.validarTelefone(cadastroEquipeTelefone.getText());
            Genero genero = (Genero) cadastroEquipeGenero.getValue();
            Cargo cargo = (Cargo) cadastroEquipeCargo.getValue();
            String matricula = cadastroEquipeMatricula.getText();
            String cursoUFRN = cadastroEquipeCursoUFRN.getText();
            String email = InputUtils.validarEmail(cadastroEquipeEmail.getText());

            if (nome == null ||
                    cpf == null ||
                    numeroCelular == null ||
                    genero == null ||
                    cargo == null ||
                    matricula.isBlank() ||
                    cursoUFRN.isBlank() ||
                    email == null) {
                exibirAlerta("Cadastro impedido", "Por favor, preencha os campos corretamente.");
                return;
            }
            if (membroEquipe.cadastrarMembroEquipe(nome, cpf, genero, numeroCelular, matricula, cursoUFRN, email, cargo)) {
                LimparCamposEquipe();
                exibirAlertaCadastroConcluido();
                carregarPessoas();
            } else {
                exibirAlerta("Cadastro impedido", "Não foi possível cadastrar o membro da equipe. Verifique os dados e tente novamente.");
            }
        } catch (Exception e) {
            exibirAlerta("Erro inesperado", "Ocorreu um erro ao tentar cadastrar o membro da equipe. Por favor, tente novamente.");
        }
    }


    @FXML
    public void clicarBtnCadastrarTurmaFinal(ActionEvent event) {
        try{
            String nome = cadastroTurmaNome.getText();
            Horario horario = (Horario) cadastroTurmaHorario.getValue();
            Curso curso = (Curso) cadastroTurmaCurso.getValue();
            Integer vagas = Integer.parseInt(cadastroTurmaVagas.getText());
            LocalDate dataInicio = cadastroTurmaDataInicio.getValue();
            LocalDate dataTermino = cadastroTurmaDataTermino.getValue();

            if (nome == null ||
                horario == null ||
                curso == null ||
                vagas == null || vagas <= 0 ||
                dataInicio == null ||
                dataTermino == null
            ) {
                exibirAlerta("Cadastro impedido", "Por favor, preencha os campos corretamente.");
                return;
            }

            if (dataInicio.isAfter(dataTermino)) {
                exibirAlerta("Data inválida", "A data de início não pode ser posterior à data de término.");
                return;
            }

            if(membroEquipe.cadastrarTurma(nome, curso, horario, vagas, dataInicio, dataTermino)){
                LimparCamposTurma();
                exibirAlertaCadastroConcluido();
                carregarTurmas();
                cadastroAlunoTurmaDisponiveis.setItems(FXCollections.observableArrayList(turmas));
            }else {
                exibirAlerta("Cadastro impedido", "Verifique os dados digitados.");
            }
        } catch (Exception e) {
            exibirAlerta("Erro inesperado", "Ocorreu um erro ao tentar cadastrar a turma. Por favor, tente novamente.");
        }
    }

    @FXML
    public void LimparCamposTurma() {
        cadastroTurmaNome.clear();
        cadastroTurmaHorario.setValue(null);
        cadastroTurmaCurso.setValue(null);
        cadastroTurmaVagas.clear();
        cadastroTurmaDataInicio.setValue(null);
        cadastroTurmaDataTermino.setValue(null);
    }

    @FXML
    public void LimparCamposEquipe() {
        cadastroEquipeNome.clear();
        cadastroEquipeCPF.clear();
        cadastroEquipeTelefone.clear();
        cadastroEquipeCargo.setValue(null);
        cadastroEquipeGenero.setValue(null);
        cadastroEquipeMatricula.clear();
        cadastroEquipeCursoUFRN.clear();
        cadastroEquipeEmail.clear();
    }

    @FXML
    public void LimparCamposAluno() {
        cadastroAlunoNome.clear();
        cadastroAlunoDataNascimento.setValue(null);
        cadastroAlunoCPF.clear();
        cadastroAlunoGenero.setValue(null);
        cadastroAlunoTelefone.clear();
        cadastroAlunoEscolaridade.setValue(null);
        cadastroAlunoTurmaDisponiveis.setValue(null);
        cadastroAlunoObsSaude.clear();
        checkAlunoInternet.setSelected(false);
        checkAlunoComputador.setSelected(false);
        checkAlunoSmartphone.setSelected(false);
        cadastroAlunoSO.setValue(null);

    }

    @FXML
    void adicionarBlocoTurma(Turma turma) {
        HBox blocoTurma = criarHBox(500, 129, "-fx-background-color: #2F4A5F; -fx-border-radius: 10; -fx-padding: 10;");
        Pane paneTurma = criarPane(500, 130);

        Label labelNome = criarLabel("NOME: " + turma.getNome(), 14, 14, 500, 30, "-fx-text-fill: white; -fx-font-size: 14;");
        Label labelHorario = criarLabel("HORARIO: " + InputUtils.validarHorario(turma.getHorario()), 14, 44, 500, 30, "-fx-text-fill: white; -fx-font-size: 14;");

        Button btnChamada = criarBotao("Chamada", 250, 59, 100, 30, "-fx-text-fill: black; -fx-font-size: 14;", event -> abrirTelaChamada(turma));
        Button btnVerTurma = criarBotao("Ver", 370, 10, 100, 30, "-fx-text-fill: black; -fx-font-size: 14;", event -> abrirTelaVerTurma(turma));
        Button btnApagar = criarBotao("Apagar", 370, 59, 100, 30, "-fx-text-fill: black; -fx-font-size: 14;", event -> apagarTurma(turma, blocoTurma));

        paneTurma.getChildren().addAll(labelNome, labelHorario, btnApagar, btnVerTurma, btnChamada);

        if (!turma.getConcluido()) {
            Button btnConcluir = criarBotao("Concluir", 250, 10, 100, 30, "-fx-text-fill: black; -fx-font-size: 14;",
                    event -> concluirTurma(turma, paneTurma));
            paneTurma.getChildren().add(btnConcluir);
        }

        blocoTurma.getChildren().add(paneTurma);
        VBoxListaDeTurmas.getChildren().add(blocoTurma);
    }

    @FXML
    void adicionarBlocoPessoa(Pessoa pessoa) {
        HBox blocoPessoa = criarHBox(500, 129, "-fx-background-color: #2F4A5F; -fx-border-radius: 10; -fx-padding: 10;");
        Pane panePessoa = criarPane(500, 130);

        Label labelNome = criarLabel("NOME: " + pessoa.getNome(), 14, 14, 500, 30, "-fx-text-fill: white; -fx-font-size: 14;");
        String tipoPessoa = (pessoa instanceof Aluno) ? "Aluno" : "Membro da Equipe";
        Label labelTipo = criarLabel(tipoPessoa, 14, 50, 500, 30, "-fx-text-fill: white; -fx-font-size: 14;");

        Button btnVerPessoa = criarBotao("Ver", 370, 10, 100, 30, "-fx-text-fill: black; -fx-font-size: 14;", event -> abrirTelaVerPessoa(pessoa));
        Button btnApagar = criarBotao("Apagar", 370, 59, 100, 30, "-fx-text-fill: black; -fx-font-size: 14;", event -> apagarPessoa(pessoa, blocoPessoa));

        panePessoa.getChildren().addAll(labelNome, labelTipo, btnVerPessoa, btnApagar);
        blocoPessoa.getChildren().add(panePessoa);
        VBoxListaDePessoas.getChildren().add(blocoPessoa);
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

    private Button criarBotao(String text, double x, double y, double width, double height, String style, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefSize(width, height);
        button.setStyle(style);
        button.setOnAction(eventHandler);
        return button;
    }

    private void apagarTurma(Turma turma, HBox blocoTurma) {
        if (exibirAlertaConfirmar(turma.getNome(), " será apagada(o)", " será apagada(o). Deseja continuar?")) {
            VBoxListaDeTurmas.getChildren().remove(blocoTurma);
            membroEquipe.removerTurma(turma);
        }
    }

    private void concluirTurma(Turma turma, Pane paneTurma) {
        if (exibirAlertaConfirmar(turma.getNome(), " será concluido(a)", " será concluido(a). Deseja continuar?")) {
            membroEquipe.concluirTurma(turma);
            paneTurma.getChildren().removeIf(node -> node instanceof Button && ((Button) node).getText().equals("Concluir"));
        }
    }

    private void apagarPessoa(Pessoa pessoa, HBox blocoPessoa) {
        if (exibirAlertaConfirmar(pessoa.getNome(), " será apagada(o)", " será apagada(o). Deseja continuar?")) {
            VBoxListaDePessoas.getChildren().remove(blocoPessoa);
            pessoas.remove(pessoa);
            membroEquipe.removerPessoaDasTurmas(pessoa);
        }
    }

    private void exibirAlertaCadastroConcluido() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Cadastro Concluido");
        alerta.setContentText("Parabens! Cadastro Concluido com sucesso!");
        alerta.showAndWait();
    }

    private boolean exibirAlertaConfirmar(String objeto, String mensagem01, String mensagem02) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(objeto + mensagem01);
        alerta.setHeaderText(null);
        alerta.setContentText(objeto + mensagem02);

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void abrirTelaVerPessoa(Pessoa pessoa) {
        if (pessoa instanceof Aluno) {
            try {
                FXMLLoader loader = new FXMLLoader(SistemaApplication.class.getResource("/br/ufrn/imd/sistemaproeidi/VerAluno.fxml"));
                Parent root = loader.load();

                VerAlunoController controller = loader.getController();

                Aluno aluno = (Aluno) pessoa;
                controller.setAluno(aluno);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Aluno");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao carregar VerAluno.fxml: " + e.getMessage());
            }
        } else if (pessoa instanceof MembroEquipe) {
            try {
                FXMLLoader loader = new FXMLLoader(SistemaApplication.class.getResource("/br/ufrn/imd/sistemaproeidi/VerMembro.fxml"));
                Parent root = loader.load();

                VerMembroController controller = loader.getController();

                MembroEquipe membro = (MembroEquipe) pessoa;
                controller.setMembroEquipe(membro);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Membro da Equipe");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao carregar VerAluno.fxml: " + e.getMessage());
            }

        }
    }

    private void abrirTelaVerTurma(Turma turma) {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaApplication.class.getResource("/br/ufrn/imd/sistemaproeidi/VerTurma.fxml"));
            Parent root = loader.load();

            VerTurmaController controller = loader.getController();

            controller.setTurma(turma);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Turma");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar VerTurma.fxml: " + e.getMessage());
        }

    }

    private void abrirTelaChamada(Turma turma) {
        try {
            FXMLLoader loader = new FXMLLoader(SistemaApplication.class.getResource("/br/ufrn/imd/sistemaproeidi/Chamada.fxml"));
            Parent root = loader.load();

            ChamadaController controller = loader.getController();

            controller.setTurma(turma);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Chamada");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar Chamada.fxml: " + e.getMessage());
        }

    }

}
