package com.example.Grupo_6.views;

// Importa a classe DisciplinaController para interagir com a lógica de disciplinas
import com.example.Grupo_6.controllers.DisciplinaController;
// Importa a classe Disciplina, que define o modelo de dados
import com.example.Grupo_6.models.Disciplina;
// Importa classes do JavaFX para criar a interface gráfica
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Classe que define a interface gráfica para gerenciamento de disciplinas
public class DisciplinaView {

    // Componentes da interface gráfica
    private TableView<Disciplina> table; // Tabela para exibir as disciplinas
    private ObservableList<Disciplina> disciplinaList; // Lista observável para atualizar a tabela
    private TextField nomeField; // Campo de texto para o nome da disciplina
    private TextField cargaField; // Campo de texto para a carga horária
    private Button adicionarBtn; // Botão para adicionar uma nova disciplina
    private Button editarBtn; // Botão para editar uma disciplina selecionada
    private Button salvarEdicaoBtn; // Botão para salvar as alterações de uma disciplina
    private Disciplina disciplinaSelecionada; // Armazena a disciplina selecionada para edição

    // Método principal para iniciar a interface de gerenciamento de disciplinas
    public void start(Stage stage) {
        // Define o título da janela
        stage.setTitle("Gerenciar Disciplinas");

        // Inicializa a lista observável com as disciplinas do controlador
        disciplinaList = FXCollections.observableArrayList(DisciplinaController.listarDisciplinas());

        // Cria a tabela e associa a lista de disciplinas
        table = new TableView<>();
        table.setItems(disciplinaList);

        // Define a coluna para o ID da disciplina
        TableColumn<Disciplina, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getId()).asObject());

        // Define a coluna para o nome da disciplina
        TableColumn<Disciplina, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNome()));

        // Define a coluna para a carga horária da disciplina
        TableColumn<Disciplina, Integer> cargaCol = new TableColumn<>("Carga Horária");
        cargaCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getCargaHoraria()).asObject());

        // Adiciona as colunas à tabela
        table.getColumns().addAll(idCol, nomeCol, cargaCol);

        // Cria o campo de texto para inserir o nome da disciplina
        nomeField = new TextField();
        nomeField.setPromptText("Nome da Disciplina");

        // Cria o campo de texto para inserir a carga horária
        cargaField = new TextField();
        cargaField.setPromptText("Carga Horária");

        // Cria o botão para adicionar uma nova disciplina
        adicionarBtn = new Button("Adicionar");
        adicionarBtn.setOnAction(e -> {
            try {
                // Obtém o nome e a carga horária, removendo espaços em branco
                String nome = nomeField.getText().trim();
                int carga = Integer.parseInt(cargaField.getText().trim());

                // Valida o nome da disciplina
                if (nome.isEmpty()) {
                    mostrarAlerta("Erro", "O nome da disciplina não pode estar vazio.");
                    return;
                }
                // Valida a carga horária
                if (carga <= 0) {
                    mostrarAlerta("Erro", "A carga horária deve ser um número positivo.");
                    return;
                }

                // Gera um novo ID baseado no maior ID existente ou 1 se a lista estiver vazia
                int novoId = disciplinaList.isEmpty() ? 1 :
                        disciplinaList.stream().mapToInt(Disciplina::getId).max().getAsInt() + 1;

                // Cria uma nova disciplina com os dados inseridos
                Disciplina d = new Disciplina(novoId, nome, carga);
                // Adiciona a disciplina ao controlador
                DisciplinaController.adicionarDisciplina(d);
                // Atualiza a lista na tabela
                disciplinaList.setAll(DisciplinaController.listarDisciplinas());
                // Limpa os campos
                nomeField.clear();
                cargaField.clear();
                // Reseta o formulário
                resetForm();
            } catch (NumberFormatException ex) {
                // Exibe alerta se a carga horária não for um número válido
                mostrarAlerta("Erro", "Carga horária inválida. Insira um número válido.");
            }
        });

        // Cria o botão para editar uma disciplina selecionada
        editarBtn = new Button("Editar Selecionado");
        editarBtn.setOnAction(e -> {
            // Obtém a disciplina selecionada na tabela
            disciplinaSelecionada = table.getSelectionModel().getSelectedItem();
            if (disciplinaSelecionada == null) {
                mostrarAlerta("Aviso", "Nenhuma disciplina selecionada!");
                return;
            }

            // Preenche os campos com os dados da disciplina selecionada
            nomeField.setText(disciplinaSelecionada.getNome());
            cargaField.setText(String.valueOf(disciplinaSelecionada.getCargaHoraria()));
            // Desativa os botões de adicionar e editar
            adicionarBtn.setDisable(true);
            editarBtn.setDisable(true);
            // Ativa o botão de salvar edição
            salvarEdicaoBtn.setDisable(false);
        });

        // Cria o botão para salvar as alterações de uma disciplina
        salvarEdicaoBtn = new Button("Salvar Edição");
        salvarEdicaoBtn.setDisable(true); // Inicialmente desativado
        salvarEdicaoBtn.setOnAction(e -> {
            try {
                // Obtém o nome e a carga horária, removendo espaços em branco
                String nome = nomeField.getText().trim();
                int carga = Integer.parseInt(cargaField.getText().trim());

                // Valida o nome da disciplina
                if (nome.isEmpty()) {
                    mostrarAlerta("Erro", "O nome da disciplina não pode estar vazio.");
                    return;
                }
                // Valida a carga horária
                if (carga <= 0) {
                    mostrarAlerta("Erro", "A carga horária deve ser um número positivo.");
                    return;
                }

                // Cria uma nova disciplina com os dados atualizados, mantendo o mesmo ID
                Disciplina disciplinaAtualizada = new Disciplina(
                        disciplinaSelecionada.getId(),
                        nome,
                        carga
                );

                // Atualiza a disciplina no controlador
                DisciplinaController.atualizarDisciplina(disciplinaSelecionada.getId(), disciplinaAtualizada);
                // Atualiza a lista na tabela
                disciplinaList.setAll(DisciplinaController.listarDisciplinas());
                // Limpa os campos
                nomeField.clear();
                cargaField.clear();
                // Reseta o formulário
                resetForm();
            } catch (NumberFormatException ex) {
                // Exibe alerta se a carga horária não for um número válido
                mostrarAlerta("Erro", "Carga horária inválida. Insira um número válido.");
            }
        });

        // Cria o botão para remover uma disciplina selecionada
        Button removerBtn = new Button("Remover Selecionado");
        removerBtn.setOnAction(e -> {
            // Obtém a disciplina selecionada na tabela
            Disciplina selecionada = table.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                // Remove a disciplina do controlador
                DisciplinaController.removerDisciplina(selecionada.getId());
                // Atualiza a lista na tabela
                disciplinaList.setAll(DisciplinaController.listarDisciplinas());
                // Reseta o formulário
                resetForm();
            } else {
                mostrarAlerta("Aviso", "Nenhuma disciplina selecionada!");
            }
        });

        // Organiza os componentes do formulário em um layout vertical
        VBox form = new VBox(10, nomeField, cargaField, adicionarBtn, editarBtn, salvarEdicaoBtn, removerBtn);
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

    // Método para resetar o formulário após operações
    private void resetForm() {
        // Reativa os botões de adicionar e editar
        adicionarBtn.setDisable(false);
        editarBtn.setDisable(false);
        // Desativa o botão de salvar edição
        salvarEdicaoBtn.setDisable(true);
        // Limpa a seleção da disciplina
        disciplinaSelecionada = null;
        // Limpa os campos de texto
        nomeField.clear();
        cargaField.clear();
    }
}