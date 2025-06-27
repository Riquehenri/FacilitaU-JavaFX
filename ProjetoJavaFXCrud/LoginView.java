package com.example.Grupo_6.views;

import com.example.Grupo_6.controllers.UsuarioController;
import com.example.Grupo_6.models.Usuario;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

    public void start(Stage stage) {
        stage.setTitle("Login - Sistema de Gestão");

        Label nomeLabel = new Label("Nome:");
        TextField nomeField = new TextField();

        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();

        Label tipoLabel = new Label("Tipo de Usuário:");
        ComboBox<String> tipoCombo = new ComboBox<>();
        tipoCombo.getItems().addAll("Estudante", "Professor", "Coordenador");
        tipoCombo.setValue("Estudante");

        Button entrarBtn = new Button("Entrar");
        entrarBtn.setOnAction(e -> {
            String nome = nomeField.getText().trim();
            String senha = senhaField.getText().trim();
            String tipo = tipoCombo.getValue();

            if (nome.isEmpty()) {
                mostrarAlerta("Erro", "Informe seu nome.");
                return;
            }

            if (senha.isEmpty()) {
                mostrarAlerta("Erro", "Informe sua senha.");
                return;
            }

            abrirMenuPorPerfil(tipo, nome, senha);
            stage.close();
        });

        VBox layout = new VBox(10, nomeLabel, nomeField, senhaLabel, senhaField, tipoLabel, tipoCombo, entrarBtn);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 450, 300);

        stage.setScene(scene);
        stage.show();
    }

    private void abrirMenuPorPerfil(String tipo, String nome, String senha) {
        Stage menuStage = new Stage();
        menuStage.setTitle("Menu - " + tipo + ": " + nome);
        
        // Criar uma cópia final das variáveis para usar no lambda
        final String tipoFinal = tipo;
        final String nomeFinal = nome;
        final String senhaFinal = senha;
        
        // Buscar usuário
        final Usuario usuarioAtual = UsuarioController.buscarUsuario(nomeFinal, tipoFinal);
        final Usuario usuarioParaUsar = usuarioAtual != null ? usuarioAtual : new Usuario(nomeFinal, senhaFinal, tipoFinal);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        // Botão de perfil
        Button perfilBtn = new Button("Meu Perfil");
        perfilBtn.setOnAction(e -> new PerfilView().start(new Stage(), usuarioParaUsar));

        if (tipoFinal.equals("Professor") || tipoFinal.equals("Coordenador")) {
            Button avisosBtn = new Button("Gerenciar Avisos");
            avisosBtn.setOnAction(e -> new AvisoView().start(new Stage()));

            Button disciplinasBtn = new Button("Gerenciar Disciplinas");
            disciplinasBtn.setOnAction(e -> new DisciplinaView().start(new Stage()));

            layout.getChildren().addAll(perfilBtn, avisosBtn, disciplinasBtn);
        } else if (tipoFinal.equals("Estudante")) {
            Button visualizarAvisosBtn = new Button("Visualizar Avisos");
            visualizarAvisosBtn.setOnAction(e -> new AvisoView().startSomenteLeitura());

            Button planejamentoBtn = new Button("Meu Planejamento de Estudos");
            planejamentoBtn.setOnAction(e -> new PlanejamentoView().start(new Stage()));

            Button tarefasBtn = new Button("Gerenciar Tarefas");
            tarefasBtn.setOnAction(e -> new TarefaView().start(new Stage()));

            layout.getChildren().addAll(perfilBtn, visualizarAvisosBtn, planejamentoBtn, tarefasBtn);
        }

        Scene scene = new Scene(layout, 350, 250);
        menuStage.setScene(scene);
        menuStage.show();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}