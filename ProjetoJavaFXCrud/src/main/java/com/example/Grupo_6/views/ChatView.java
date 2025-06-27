package com.example.Grupo_6.views;

// Importa a classe Usuario, que define o modelo de dados
import com.example.Grupo_6.models.Usuario;
// Importa classes do JavaFX para criar a interface gráfica
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Classe que define a interface gráfica do menu principal com funcionalidade de chat
public class ChatView {

    // Atributos privados para armazenar informações do usuário
    private String nomeUsuario; // Nome do usuário logado
    private String tipoUsuario; // Tipo do usuário (ex.: Estudante, Professor, Coordenador)
    private Usuario usuario; // Objeto Usuario com os dados completos

    // Construtor que inicializa a view com os dados do usuário
    public ChatView(String nomeUsuario, String tipoUsuario, Usuario usuario) {
        this.nomeUsuario = nomeUsuario;
        this.tipoUsuario = tipoUsuario;
        this.usuario = usuario;
    }

    // Método principal para iniciar a interface do menu principal
    public void start(Stage stage) {
        // Define o título da janela com o tipo e nome do usuário
        stage.setTitle("Menu Principal - " + tipoUsuario + ": " + nomeUsuario);

        // Cria um layout vertical com espaçamento de 15 pixels
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        // Cria um rótulo de boas-vindas com o nome e tipo do usuário
        Label welcomeLabel = new Label("Bem-vindo, " + nomeUsuario + " (" + tipoUsuario + ")");

        // Cria uma área de texto para exibir o chat
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false); // Impede edição direta pelo usuário
        chatArea.setPrefHeight(200); // Define altura preferida
        chatArea.setWrapText(true); // Habilita quebra de texto
        // Define mensagem inicial do chat
        chatArea.setText("🤖 IA: Olá " + nomeUsuario + "! Em que posso te ajudar?\n");

        // Cria um campo de texto para entrada do usuário
        TextField userInput = new TextField();
        userInput.setPromptText("Digite sua mensagem...");

        // Cria o botão para enviar mensagens
        Button enviarBtn = new Button("Enviar");
        enviarBtn.setOnAction(e -> {
            // Obtém o texto inserido, removendo espaços em branco
            String input = userInput.getText().trim();
            if (!input.isEmpty()) {
                // Adiciona a mensagem do usuário ao chat
                chatArea.appendText("👤 Você: " + input + "\n");
                // Adiciona uma resposta padrão da IA
                chatArea.appendText("🤖 IA: Ainda estou aprendendo! Em breve responderei melhor ;)\n\n");
                // Limpa o campo de entrada
                userInput.clear();
            }
        });

        // Organiza o campo de entrada e o botão em um layout horizontal
        HBox inputBox = new HBox(10, userInput, enviarBtn);

        // Cria um layout vertical para os botões de navegação
        VBox botoesBox = new VBox(10);

        // Cria o botão para acessar o perfil do usuário
        Button perfilBtn = new Button("Meu Perfil");
        perfilBtn.setOnAction(e -> new PerfilView().start(new Stage(), usuario));
        botoesBox.getChildren().add(perfilBtn);

        // Adiciona botões específicos com base no tipo de usuário
        if (tipoUsuario.equals("Estudante")) {
            // Botão para visualizar avisos (somente leitura)
            Button visualizarAvisosBtn = new Button("Visualizar Avisos");
            visualizarAvisosBtn.setOnAction(e -> new AvisoView().startSomenteLeitura());

            // Botão para gerenciar o planejamento de estudos
            Button planejamentoBtn = new Button("Meu Planejamento de Estudos");
            planejamentoBtn.setOnAction(e -> new PlanejamentoView(nomeUsuario).start(new Stage()));

            // Botão para gerenciar tarefas
            Button tarefasBtn = new Button("Gerenciar Tarefas");
            tarefasBtn.setOnAction(e -> new TarefaView(nomeUsuario).start(new Stage()));

            // Adiciona os botões ao layout de botões
            botoesBox.getChildren().addAll(visualizarAvisosBtn, planejamentoBtn, tarefasBtn);
        } else if (tipoUsuario.equals("Professor") || tipoUsuario.equals("Coordenador")) {
            // Botão para gerenciar avisos
            Button avisosBtn = new Button("Gerenciar Avisos");
            avisosBtn.setOnAction(e -> new AvisoView().start(new Stage()));

            // Botão para gerenciar disciplinas
            Button disciplinasBtn = new Button("Gerenciar Disciplinas");
            disciplinasBtn.setOnAction(e -> new DisciplinaView().start(new Stage()));

            // Adiciona os botões ao layout de botões
            botoesBox.getChildren().addAll(avisosBtn, disciplinasBtn);

            // Adiciona botão extra para Coordenadores
            if (tipoUsuario.equals("Coordenador")) {
                // Botão para gerenciar usuários
                Button usuariosBtn = new Button("Gerenciar Usuários");
                usuariosBtn.setOnAction(e -> new UsuarioView().start(new Stage()));
                botoesBox.getChildren().add(usuariosBtn);
            }
        }

        // Adiciona todos os componentes ao layout principal
        layout.getChildren().addAll(welcomeLabel, chatArea, inputBox, new Separator(), botoesBox);

        // Cria a cena com o layout, definindo dimensões de 600x500
        Scene scene = new Scene(layout, 600, 500);
        // Define a cena no palco (janela) e exibe
        stage.setScene(scene);
        stage.show();
    }
}