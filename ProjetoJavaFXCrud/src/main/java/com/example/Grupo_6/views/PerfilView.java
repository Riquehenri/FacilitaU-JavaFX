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

// Classe que define a interface gráfica para a visualização e edição do perfil do usuário
public class PerfilView {

    // Método principal para iniciar a interface de perfil
    public void start(Stage stage, Usuario usuario) {
        // Define o título da janela com o nome do usuário
        stage.setTitle("Meu Perfil - " + usuario.getNome());

        // Cria o rótulo e campo para o nome do usuário, pré-preenchido
        Label nomeLabel = new Label("Nome:");
        TextField nomeField = new TextField(usuario.getNome());

        // Cria o rótulo e campo para a data de nascimento, pré-preenchido
        Label dataNascimentoLabel = new Label("Data de Nascimento:");
        DatePicker dataNascimentoField = new DatePicker(usuario.getDataNascimento());

        // Cria o rótulo e campo para a senha, que é apenas visualizável
        Label senhaLabel = new Label("Senha:");
        PasswordField senhaField = new PasswordField();
        senhaField.setText(usuario.getSenha());
        senhaField.setEditable(false); // Impede a edição da senha

        // Cria o rótulo e campo para o tipo de usuário, que é apenas visualizável
        Label tipoLabel = new Label("Tipo de Usuário:");
        TextField tipoField = new TextField(usuario.getTipo());
        tipoField.setEditable(false); // Impede a edição do tipo

        // Cria o botão para salvar as alterações no perfil
        Button salvarBtn = new Button("Salvar Alterações");
        salvarBtn.setOnAction(e -> {
            // Obtém o novo nome e data de nascimento, removendo espaços em branco
            String novoNome = nomeField.getText().trim();
            LocalDate novaDataNascimento = dataNascimentoField.getValue();

            // Valida se o nome está vazio
            if (novoNome.isEmpty()) {
                mostrarAlerta("Erro", "O nome não pode estar vazio.");
                return;
            }
            // Valida se a data de nascimento foi selecionada
            if (novaDataNascimento == null) {
                mostrarAlerta("Erro", "Por favor, selecione a data de nascimento.");
                return;
            }

            // Cria um novo objeto Usuario com os dados atualizados
            Usuario usuarioAtualizado = new Usuario(
                    novoNome,
                    usuario.getEmail(), // Mantém o email original (não editável)
                    usuario.getSenha(), // Mantém a senha original
                    usuario.getTipo(),  // Mantém o tipo original
                    novaDataNascimento
            );

            // Atualiza o usuário no controlador
            UsuarioController.atualizarUsuario(usuario, usuarioAtualizado);

            // Exibe mensagem de sucesso
            mostrarAlerta("Sucesso", "Perfil atualizado com sucesso!");
            // Fecha a janela
            stage.close();
        });

        // Organiza os componentes em um layout vertical com espaçamento de 10 pixels
        VBox layout = new VBox(10,
                nomeLabel, nomeField,
                dataNascimentoLabel, dataNascimentoField,
                senhaLabel, senhaField,
                tipoLabel, tipoField,
                salvarBtn);
        layout.setPadding(new Insets(20));

        // Cria a cena com o layout, definindo dimensões de 400x400
        Scene scene = new Scene(layout, 400, 400);
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