package com.example.Grupo_6.views;

// Importa a classe UsuarioController para interagir com a lógica de usuários
import com.example.Grupo_6.controllers.UsuarioController;
// Importa a classe Usuario, que define o modelo de dados
import com.example.Grupo_6.models.Usuario;
// Importa classes do JavaFX para criar a interface gráfica
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Classe que define a interface gráfica para a tela de login
public class LoginView {

    // Método principal para iniciar a interface de login
    public void start(Stage stage) {
        // Define o título da janela
        stage.setTitle("Login - Sistema de Gestão");

        // Cria o rótulo e campo para o email do usuário
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Digite seu email");

        // Cria o rótulo e campo para a senha do usuário
        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Digite sua senha");

        // Cria o botão para realizar o login
        Button entrarBtn = new Button("Entrar");
        entrarBtn.setOnAction(e -> {
            // Obtém o email e a senha, removendo espaços em branco
            String email = emailField.getText().trim();
            String senha = senhaField.getText().trim();

            // Valida se o email está vazio
            if (email.isEmpty()) {
                mostrarAlerta("Erro", "Informe seu email.");
                return;
            }

            // Valida se a senha está vazia
            if (senha.isEmpty()) {
                mostrarAlerta("Erro", "Informe sua senha.");
                return;
            }

            // Busca o usuário no controlador com base no email e senha
            Usuario usuario = UsuarioController.buscarUsuario(email, senha);
            if (usuario == null) {
                // Exibe alerta se o usuário não for encontrado
                mostrarAlerta("Erro", "Email ou senha inválidos.");
                return;
            }

            // Abre a tela principal (ChatView) passando o nome, tipo e objeto do usuário
            new ChatView(usuario.getNome(), usuario.getTipo(), usuario).start(new Stage());
            // Fecha a janela de login
            stage.close();
        });

        // Cria o botão para ir para a tela de cadastro
        Button cadastrarBtn = new Button("Cadastrar");
        cadastrarBtn.setOnAction(e -> {
            // Abre a tela de cadastro em uma nova janela
            new CadastroView().start(new Stage());
            // Fecha a janela de login
            stage.close();
        });

        // Organiza os componentes em um layout vertical com espaçamento de 10 pixels
        VBox layout = new VBox(10, emailLabel, emailField, senhaLabel, senhaField, entrarBtn, cadastrarBtn);
        layout.setPadding(new Insets(20));

        // Cria a cena com o layout, definindo dimensões de 450x300
        Scene scene = new Scene(layout, 450, 300);
        // Define a cena no palco (janela) e exibe
        stage.setScene(scene);
        stage.show();
    }

    // Método para exibir mensagens de alerta
    private void mostrarAlerta(String titulo, String mensagem) {
        // Cria um alerta do tipo ERROR
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        // Exibe o alerta e aguarda o usuário fechar
        alert.showAndWait();
    }
}