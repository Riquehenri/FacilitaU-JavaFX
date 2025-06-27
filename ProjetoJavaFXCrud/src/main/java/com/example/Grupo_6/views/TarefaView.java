package com.example.Grupo_6.views;

// Importa a classe TarefaController para interagir com a lógica de tarefas
import com.example.Grupo_6.controllers.TarefaController;
// Importa a classe Tarefa, que define o modelo de dados
import com.example.Grupo_6.models.Tarefa;
// Importa classes do JavaFX para criar a interface gráfica
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Classe que define a interface gráfica para gerenciamento de tarefas
public class TarefaView {

    // Componentes da interface gráfica
    private TableView<Tarefa> table; // Tabela para exibir as tarefas
    private ObservableList<Tarefa> tarefaList; // Lista observável para atualizar a tabela
    private String usuario; // Nome do usuário associado às tarefas
    private TextField nomeField; // Campo de texto para o nome da tarefa
    private TextField descricaoField; // Campo de texto para a descrição da tarefa
    private Button adicionarBtn; // Botão para adicionar uma nova tarefa
    private Button editarBtn; // Botão para editar uma tarefa selecionada
    private Button salvarEdicaoBtn; // Botão para salvar as alterações de uma tarefa
    private Tarefa tarefaSelecionada; // Armazena a tarefa selecionada para edição

    // Construtor que inicializa a view com o nome do usuário
    public TarefaView(String usuario) {
        this.usuario = usuario;
    }

    // Método principal para iniciar a interface de gerenciamento de tarefas
    public void start(Stage stage) {
        // Define o título da janela com o nome do usuário
        stage.setTitle("Gerenciar Tarefas - " + usuario);

        // Inicializa a lista observável com as tarefas do controlador para o usuário
        tarefaList = FXCollections.observableArrayList(TarefaController.listar(usuario));

        // Cria a tabela e associa a lista de tarefas
        table = new TableView<>();
        table.setItems(tarefaList);

        // Define a coluna para o ID da tarefa
        TableColumn<Tarefa, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        // Define a coluna para o nome da tarefa
        TableColumn<Tarefa, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));

        // Define a coluna para a descrição da tarefa
        TableColumn<Tarefa, String> descricaoCol = new TableColumn<>("Descrição");
        descricaoCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescricao()));

        // Adiciona as colunas à tabela
        table.getColumns().addAll(idCol, nomeCol, descricaoCol);

        // Cria o campo de texto para inserir o nome da tarefa
        nomeField = new TextField();
        nomeField.setPromptText("Nome da Tarefa");

        // Cria o campo de texto para inserir a descrição da tarefa
        descricaoField = new TextField();
        descricaoField.setPromptText("Descrição da Tarefa");

        // Cria o botão para adicionar uma nova tarefa
        adicionarBtn = new Button("Adicionar");
        adicionarBtn.setOnAction(e -> {
            // Verifica se os campos estão vazios
            if (nomeField.getText().isEmpty() || descricaoField.getText().isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos!");
                return;
            }

            // Gera um novo ID baseado no maior ID existente ou 1 se a lista estiver vazia
            int novoId = tarefaList.isEmpty() ? 1 :
                    tarefaList.stream().mapToInt(Tarefa::getId).max().getAsInt() + 1;

            // Cria uma nova tarefa com os dados inseridos
            Tarefa t = new Tarefa(novoId, nomeField.getText(), descricaoField.getText(), usuario);
            // Adiciona a tarefa ao controlador
            TarefaController.adicionar(t);
            // Atualiza a lista na tabela
            tarefaList.setAll(TarefaController.listar(usuario));
            // Limpa os campos
            nomeField.clear();
            descricaoField.clear();
            // Reseta o formulário
            resetForm();
        });

        // Cria o botão para editar uma tarefa selecionada
        editarBtn = new Button("Gerenciar Tarefa");
        editarBtn.setOnAction(e -> {
            // Obtém a tarefa selecionada na tabela
            tarefaSelecionada = table.getSelectionModel().getSelectedItem();
            if (tarefaSelecionada == null) {
                mostrarAlerta("Aviso", "Nenhuma tarefa selecionada!");
                return;
            }

            // Preenche os campos com os dados da tarefa selecionada
            nomeField.setText(tarefaSelecionada.getNome());
            descricaoField.setText(tarefaSelecionada.getDescricao());
            // Desativa os botões de adicionar e editar
            adicionarBtn.setDisable(true);
            editarBtn.setDisable(true);
            // Ativa o botão de salvar edição
            salvarEdicaoBtn.setDisable(false);
        });

        // Cria o botão para salvar as alterações de uma tarefa
        salvarEdicaoBtn = new Button("Salvar Edição");
        salvarEdicaoBtn.setDisable(true); // Inicialmente desativado
        salvarEdicaoBtn.setOnAction(e -> {
            // Verifica se os campos estão vazios
            if (nomeField.getText().isEmpty() || descricaoField.getText().isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos!");
                return;
            }

            // Cria uma nova tarefa com os dados atualizados, mantendo o mesmo ID
            Tarefa tarefaAtualizada = new Tarefa(
                    tarefaSelecionada.getId(),
                    nomeField.getText(),
                    descricaoField.getText(),
                    usuario
            );

            // Atualiza a tarefa no controlador
            TarefaController.atualizarTarefa(tarefaSelecionada.getId(), tarefaAtualizada);
            // Atualiza a lista na tabela
            tarefaList.setAll(TarefaController.listar(usuario));
            // Limpa os campos
            nomeField.clear();
            descricaoField.clear();
            // Reseta o formulário
            resetForm();
        });

        // Cria o botão para remover uma tarefa selecionada
        Button removerBtn = new Button("Remover Selecionado");
        removerBtn.setOnAction(e -> {
            // Obtém a tarefa selecionada na tabela
            Tarefa selecionada = table.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                // Remove a tarefa do controlador
                TarefaController.remover(selecionada.getId(), usuario);
                // Atualiza a lista na tabela
                tarefaList.setAll(TarefaController.listar(usuario));
                // Reseta o formulário
                resetForm();
            } else {
                mostrarAlerta("Aviso", "Nenhuma tarefa selecionada!");
            }
        });

        // Organiza os componentes do formulário em um layout vertical
        VBox form = new VBox(10, nomeField, descricaoField, adicionarBtn, editarBtn, salvarEdicaoBtn, removerBtn);
        form.setPadding(new Insets(10));

        // Organiza a tabela e o formulário em um layout horizontal
        HBox layout = new HBox(10, table, form);
        layout.setPadding(new Insets(10));

        // Cria a cena com o layout, definindo dimensões de 700x400
        Scene scene = new Scene(layout, 700, 400);
        // Define a cena no palco (janela) e exibe
        stage.setScene(scene);
        stage.show();
    }

    // Método para resetar o formulário após operações
    private void resetForm() {
        // Reativa os botões de adicionar e editar
        adicionarBtn.setDisable(false);
        editarBtn.setDisable(false);
        // Desativa o botão de salvar edição
        salvarEdicaoBtn.setDisable(true);
        // Limpa a seleção da tarefa
        tarefaSelecionada = null;
        // Limpa os campos de texto
        nomeField.clear();
        descricaoField.clear();
    }

    // Método para exibir mensagens de alerta
    private void mostrarAlerta(String titulo, String mensagem) {
        // Cria um alerta do tipo WARNING
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        // Exibe o alerta e aguarda o usuário fechar
        alert.showAndWait();
    }
}