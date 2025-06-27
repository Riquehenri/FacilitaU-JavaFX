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
// Importa LocalDate para manipular a data de nascimento
import java.time.LocalDate;

// Classe que define a interface gráfica para o cadastro de usuários
public class CadastroView {

    // Método principal para iniciar a interface de cadastro
    public void start(Stage stage) {
        // Define o título da janela
        stage.setTitle("Cadastro - Sistema de Gestão");

        // Cria o rótulo e campo para o nome do usuário
        Label nomeLabel = new Label("Nome:");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Digite seu nome");

        // Cria o rótulo e campo para o email do usuário
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Digite seu email");

        // Cria o rótulo e campo para a senha do usuário
        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Digite sua senha");

        // Cria o rótulo e campo para confirmar a senha
        Label confirmarSenhaLabel = new Label("Confirmar Senha:");
        PasswordField confirmarSenhaField = new PasswordField();
        confirmarSenhaField.setPromptText("Confirme sua senha");

        // Cria o rótulo e campo para a data de nascimento
        Label dataNascimentoLabel = new Label("Data de Nascimento:");
        DatePicker dataNascimentoField = new DatePicker();

        // Cria o rótulo e campo para o tipo de usuário
        Label tipoLabel = new Label("Tipo de Usuário:");
        ComboBox<String> tipoCombo = new ComboBox<>();
        // Adiciona opções ao menu suspenso
        tipoCombo.getItems().addAll("Estudante", "Professor", "Coordenador");
        // Define "Estudante" como valor padrão
        tipoCombo.setValue("Estudante");

        // Cria o botão para realizar o cadastro
        Button cadastrarBtn = new Button("Cadastrar");
        cadastrarBtn.setOnAction(e -> {
            // Obtém os valores dos campos, removendo espaços em branco
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            String senha = senhaField.getText().trim();
            String confirmarSenha = confirmarSenhaField.getText().trim();
            LocalDate dataNascimento = dataNascimentoField.getValue();
            String tipo = tipoCombo.getValue();

            // Validações dos campos
            if (nome.isEmpty()) {
                mostrarAlerta("Erro", "O nome não pode estar vazio.");
                return;
            }
            if (email.isEmpty() || !email.contains("@")) {
                mostrarAlerta("Erro", "Por favor, insira um email válido.");
                return;
            }
            if (senha.isEmpty()) {
                mostrarAlerta("Erro", "A senha não pode estar vazia.");
                return;
            }
            if (!senha.equals(confirmarSenha)) {
                mostrarAlerta("Erro", "As senhas não coincidem.");
                return;
            }
            if (dataNascimento == null) {
                mostrarAlerta("Erro", "Por favor, selecione a data de nascimento.");
                return;
            }

            // Cria um novo usuário com os dados fornecidos
            Usuario novoUsuario = new Usuario(nome, email, senha, tipo, dataNascimento);
            // Adiciona o usuário ao sistema através do controlador
            UsuarioController.adicionarUsuario(novoUsuario);

            // Exibe mensagem de sucesso
            mostrarAlerta("Sucesso", "Cadastro realizado com sucesso!");

            // Abre a tela de login em uma nova janela
            new LoginView().start(new Stage());
            // Fecha a janela de cadastro
            stage.close();
        });

        // Organiza os componentes em um layout vertical com espaçamento de 10 pixels
        VBox layout = new VBox(10, nomeLabel, nomeField, emailLabel, emailField,
                senhaLabel, senhaField, confirmarSenhaLabel, confirmarSenhaField,
                dataNascimentoLabel, dataNascimentoField, tipoLabel, tipoCombo, cadastrarBtn);
        layout.setPadding(new Insets(20));

        // Cria a cena com o layout, definindo dimensões de 500x500
        Scene scene = new Scene(layout, 500, 500);
        // Define a cena no palco (janela) e exibe
        stage.setScene(scene);
        stage.show();
    }

    // Método para exibir mensagens de alerta
    private void mostrarAlerta(String titulo, String mensagem) {
        // Cria um alerta do tipo INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        // Exibe o alerta e aguarda o usuário fechar
        alert.showAndWait();
    }
}