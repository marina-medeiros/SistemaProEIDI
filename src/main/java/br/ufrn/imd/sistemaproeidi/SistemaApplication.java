package br.ufrn.imd.sistemaproeidi;

import br.ufrn.imd.sistemaproeidi.model.Gerenciador;
import br.ufrn.imd.sistemaproeidi.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SistemaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Gerenciador.carregarBinario();

        FXMLLoader fxmlLoader = new FXMLLoader(SistemaApplication.class.getResource("Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Boas-Vindas!");
        stage.setScene(scene);
        stage.show();
    }

    public void stop() {
        Gerenciador.salvarBinario();
        System.out.println("Dados salvos e aplicação encerrada.");
    }

    public static void main(String[] args) {
        launch(args); // Inicia a aplicação JavaFX
    }
}