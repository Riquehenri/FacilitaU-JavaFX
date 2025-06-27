package com.example.Grupo_6;

// Importa a classe base do JavaFX para criar aplicações gráficas
import javafx.application.Application;
// Importa a classe Stage para gerenciar a janela principal
import javafx.stage.Stage;
// Importa a classe LoginView para iniciar a tela de login
import com.example.Grupo_6.views.LoginView;

// Classe principal que inicia a aplicação JavaFX
public class Main extends Application {

    // Método sobrescrito que define o ponto de entrada da interface gráfica
    @Override
    public void start(Stage primaryStage) {
        // Instancia e inicia a tela de login, passando o palco principal
        new LoginView().start(primaryStage);
    }

    // Método principal que inicia a aplicação
    public static void main(String[] args) {
        // Chama o método launch para iniciar a aplicação JavaFX
        launch(args);
    }
}


