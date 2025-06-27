package com.example.Grupo_6.views;

// Importa o controlador responsável pela manipulação dos usuários
import com.example.Grupo_6.controllers.UsuarioController;
// Importa a classe de modelo Usuario
import com.example.Grupo_6.models.Usuario;

// Importa classes JavaFX para criação de interface gráfica
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;

// Classe responsável por criar a interface gráfica para gerenciamento de usuários
public class UsuarioView {

    // Declaração dos componentes da interface
    private TableView<Usuario> table;                      // Tabela que exibirá a lista de usuários
    private ObservableList<Usuario> usuarioList;           // Lista observável com os usuários para atualizar a tabela em tempo real

    // Método principal para iniciar a tela de gerenciamento de usuários
    public void start(Stage stage) {
        stage.setTitle("Gerenciar Usuários");              // Define o título da janela

        // Inicializa a lista de usuários a partir do controlador
        usuarioList = FXCollections.observableArrayList(UsuarioController.listarUsuarios());

        // Configura a TableView e vincula a lista de usuários
        table = new TableView<>();
        table.setItems(usuarioList);

        // Criação das colunas da tabela e associação com os atributos da classe Usuario

        TableColumn<Usuario, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNome()));

        TableColumn<Usuario, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));

        TableColumn<Usuario, String> senhaCol = new TableColumn<>("Senha");
        senhaCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSenha()));

        TableColumn<Usuario, String> tipoCol = new TableColumn<>("Tipo");
        tipoCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipo()));

        TableColumn<Usuario, String> dataNascimentoCol = new TableColumn<>("Data de Nascimento");
        dataNascimentoCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDataNascimento().toString()));

        // Adiciona todas as colunas à tabela
        table.getColumns().addAll(nomeCol, emailCol, senhaCol, tipoCol, dataNascimentoCol);

        // Campos de formulário para entrada de dados
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField senhaField = new TextField();
        senhaField.setPromptText("Senha");

        DatePicker dataNascimentoField = new DatePicker();
        dataNascimentoField.setPromptText("Data de Nascimento");

        ComboBox<String> tipoCombo = new ComboBox<>();
        tipoCombo.getItems().addAll("Estudante", "Professor", "Coordenador");
        tipoCombo.setValue("Estudante");  // Valor padrão

        // Listener: Quando o usuário seleciona uma linha da tabela, os campos do formulário são preenchidos automaticamente
        table.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                nomeField.setText(novo.getNome());
                emailField.setText(novo.getEmail());
                senhaField.setText(novo.getSenha());
                dataNascimentoField.setValue(novo.getDataNascimento());
                tipoCombo.setValue(novo.getTipo());
                emailField.setDisable(true);  // Impede a alteração do email (chave identificadora)
            }
        });

        // Botão Adicionar - Cria um novo usuário com os dados preenchidos
        Button adicionarBtn = new Button("Adicionar");
        adicionarBtn.setOnAction(e -> {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            String senha = senhaField.getText().trim();
            LocalDate dataNascimento = dataNascimentoField.getValue();
            String tipo = tipoCombo.getValue();

            // Validação dos campos obrigatórios
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
            if (dataNascimento == null) {
                mostrarAlerta("Erro", "Por favor, selecione a data de nascimento.");
                return;
            }

            // Adiciona o novo usuário via controller
            UsuarioController.adicionarUsuario(new Usuario(nome, email, senha, tipo, dataNascimento));
            usuarioList.setAll(UsuarioController.listarUsuarios());

            // Limpa os campos do formulário
            limparCampos(nomeField, emailField, senhaField, dataNascimentoField, tipoCombo);
        });

        // Botão Editar - Atualiza os dados do usuário selecionado
        Button editarBtn = new Button("Editar");
        editarBtn.setOnAction(e -> {
            Usuario selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                String nome = nomeField.getText().trim();
                String senha = senhaField.getText().trim();
                LocalDate dataNascimento = dataNascimentoField.getValue();
                String tipo = tipoCombo.getValue();

                // Cria um novo objeto com os dados atualizados
                Usuario novoUsuario = new Usuario(
                        nome.isEmpty() ? selecionado.getNome() : nome,
                        selecionado.getEmail(), // Email não pode ser alterado
                        senha.isEmpty() ? selecionado.getSenha() : senha,
                        tipo == null ? selecionado.getTipo() : tipo,
                        dataNascimento == null ? selecionado.getDataNascimento() : dataNascimento
                );

                // Atualiza o usuário no controller
                UsuarioController.atualizarUsuario(selecionado, novoUsuario);
                usuarioList.setAll(UsuarioController.listarUsuarios());
                limparCampos(nomeField, emailField, senhaField, dataNascimentoField, tipoCombo);
            } else {
                mostrarAlerta("Erro", "Selecione um usuário na tabela.");
            }
        });

        // Botão Remover - Exclui o usuário selecionado
        Button removerBtn = new Button("Remover Selecionado");
        removerBtn.setOnAction(e -> {
            Usuario selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                UsuarioController.removerUsuario(selecionado.getEmail());
                usuarioList.setAll(UsuarioController.listarUsuarios());
                limparCampos(nomeField, emailField, senhaField, dataNascimentoField, tipoCombo);
            }
        });

        // Layout do formulário de entrada de dados
        VBox form = new VBox(10, nomeField, emailField, senhaField, dataNascimentoField, tipoCombo, adicionarBtn, editarBtn, removerBtn);
        form.setPadding(new Insets(10));

        // Layout geral da janela: tabela à esquerda, formulário à direita
        HBox layout = new HBox(10, table, form);
        layout.setPadding(new Insets(10));

        // Criação da cena principal
        Scene scene = new Scene(layout, 800, 400);
        stage.setScene(scene);
        stage.show();
    }

    // Método para exibir mensagens de alerta
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Método para limpar os campos do formulário após adicionar, editar ou remover
    private void limparCampos(TextField nomeField, TextField emailField, TextField senhaField, DatePicker dataNascimentoField, ComboBox<String> tipoCombo) {
        nomeField.clear();
        emailField.clear();
        senhaField.clear();
        dataNascimentoField.setValue(null);
        tipoCombo.setValue("Estudante");
        emailField.setDisable(false);  // Reativa o campo email para novos cadastros
    }
}
