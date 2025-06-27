package com.example.Grupo_6.views;

// Importa a classe AvisoController para interagir com a lógica de avisos
import com.example.Grupo_6.controllers.AvisoController;
// Importa a classe Aviso, que define o modelo de dados
import com.example.Grupo_6.models.Aviso;
// Importa classes do JavaFX para criar a interface gráfica
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Classe que define a interface gráfica para gerenciamento de avisos
public class AvisoView {

    // Componentes da interface gráfica
    private TableView<Aviso> table; // Tabela para exibir os avisos
    private ObservableList<Aviso> avisoList; // Lista observável para atualizar a tabela
    private TextField tituloField; // Campo de texto para o título do aviso
    private TextField conteudoField; // Campo de texto para o conteúdo do aviso
    private Button adicionarBtn; // Botão para adicionar um novo aviso
    private Button editarBtn; // Botão para editar um aviso selecionado
    private Button salvarEdicaoBtn; // Botão para salvar as alterações de um aviso
    private Aviso avisoSelecionado; // Armazena o aviso selecionado para edição

    // Método principal para iniciar a interface completa (com funcionalidades de CRUD)
    public void start(Stage stage) {
        // Define o título da janela
        stage.setTitle("Gerenciamento de Avisos");

        // Inicializa a lista observável com os avisos do controlador
        avisoList = FXCollections.observableArrayList(AvisoController.listarAvisos());

        // Cria a tabela e associa a lista de avisos
        table = new TableView<>();
        table.setItems(avisoList);

        // Define a coluna para o ID do aviso
        TableColumn<Aviso, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        // Define a coluna para o título do aviso
        TableColumn<Aviso, String> tituloCol = new TableColumn<>("Título");
        tituloCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitulo()));

        // Define a coluna para o conteúdo do aviso
        TableColumn<Aviso, String> conteudoCol = new TableColumn<>("Conteúdo");
        conteudoCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getConteudo()));

        // Adiciona as colunas à tabela
        table.getColumns().addAll(idCol, tituloCol, conteudoCol);

        // Cria o campo de texto para inserir o título
        tituloField = new TextField();
        tituloField.setPromptText("Título");

        // Cria o campo de texto para inserir o conteúdo
        conteudoField = new TextField();
        conteudoField.setPromptText("Conteúdo");

        // Cria o botão para adicionar um novo aviso
        adicionarBtn = new Button("Adicionar");
        adicionarBtn.setOnAction(e -> {
            // Verifica se os campos estão vazios
            if (tituloField.getText().isEmpty() || conteudoField.getText().isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos!");
                return;
            }

            // Gera um novo ID baseado no maior ID existente ou 1 se a lista estiver vazia
            int novoId = avisoList.isEmpty() ? 1 :
                    avisoList.stream().mapToInt(Aviso::getId).max().getAsInt() + 1;

            // Cria um novo aviso com os dados inseridos
            Aviso aviso = new Aviso(novoId, tituloField.getText(), conteudoField.getText());
            // Adiciona o aviso ao controlador
            AvisoController.adicionarAviso(aviso);
            // Atualiza a lista na tabela
            avisoList.setAll(AvisoController.listarAvisos());
            // Limpa os campos
            tituloField.clear();
            conteudoField.clear();
            // Reseta o formulário
            resetForm();
        });

        // Cria o botão para editar um aviso selecionado
        editarBtn = new Button("Editar Selecionado");
        editarBtn.setOnAction(e -> {
            // Obtém o aviso selecionado na tabela
            avisoSelecionado = table.getSelectionModel().getSelectedItem();
            if (avisoSelecionado == null) {
                mostrarAlerta("Aviso", "Nenhum aviso selecionado!");
                return;
            }

            // Preenche os campos com os dados do aviso selecionado
            tituloField.setText(avisoSelecionado.getTitulo());
            conteudoField.setText(avisoSelecionado.getConteudo());
            // Desativa os botões de adicionar e editar
            adicionarBtn.setDisable(true);
            editarBtn.setDisable(true);
            // Ativa o botão de salvar edição
            salvarEdicaoBtn.setDisable(false);
        });

        // Cria o botão para salvar as alterações de um aviso
        salvarEdicaoBtn = new Button("Salvar Edição");
        salvarEdicaoBtn.setDisable(true); // Inicialmente desativado
        salvarEdicaoBtn.setOnAction(e -> {
            // Verifica se os campos estão vazios
            if (tituloField.getText().isEmpty() || conteudoField.getText().isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos!");
                return;
            }

            // Cria um novo aviso com os dados atualizados, mantendo o mesmo ID
            Aviso avisoAtualizado = new Aviso(
                    avisoSelecionado.getId(),
                    tituloField.getText(),
                    conteudoField.getText()
            );

            // Atualiza o aviso no controlador
            AvisoController.atualizarAviso(avisoSelecionado.getId(), avisoAtualizado);
            // Atualiza a lista na tabela
            avisoList.setAll(AvisoController.listarAvisos());
            // Limpa os campos
            tituloField.clear();
            conteudoField.clear();
            // Reseta o formulário
            resetForm();
        });

        // Cria o botão para remover um aviso selecionado
        Button removerBtn = new Button("Remover Selecionado");
        removerBtn.setOnAction(e -> {
            // Obtém o aviso selecionado na tabela
            Aviso selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                // Remove o aviso do controlador
                AvisoController.removerAviso(selecionado.getId());
                // Atualiza a lista na tabela
                avisoList.setAll(AvisoController.listarAvisos());
                // Reseta o formulário
                resetForm();
            } else {
                mostrarAlerta("Aviso", "Nenhum aviso selecionado!");
            }
        });

        // Organiza os componentes do formulário em um layout vertical
        VBox form = new VBox(10, tituloField, conteudoField, adicionarBtn, editarBtn, salvarEdicaoBtn, removerBtn);
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

    // Método para iniciar uma interface somente leitura para estudantes
    public void startSomenteLeitura() {
        // Cria uma nova janela
        Stage stage = new Stage();
        stage.setTitle("Avisos para Estudantes");

        // Inicializa a lista observável com os avisos do controlador
        ObservableList<Aviso> avisos = FXCollections.observableArrayList(AvisoController.listarAvisos());

        // Cria a tabela e associa a lista de avisos
        TableView<Aviso> tabela = new TableView<>();
        tabela.setItems(avisos);

        // Define a coluna para o ID do aviso
        TableColumn<Aviso, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        // Define a coluna para o título do aviso
        TableColumn<Aviso, String> tituloCol = new TableColumn<>("Título");
        tituloCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitulo()));

        // Define a coluna para o conteúdo do aviso
        TableColumn<Aviso, String> conteudoCol = new TableColumn<>("Conteúdo");
        conteudoCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getConteudo()));

        // Adiciona as colunas à tabela
        tabela.getColumns().addAll(idCol, tituloCol, conteudoCol);

        // Organiza a tabela em um layout vertical
        VBox layout = new VBox(10, tabela);
        layout.setPadding(new Insets(15));

        // Cria a cena com o layout, definindo dimensões de 600x400
        Scene scene = new Scene(layout, 600, 400);
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
        // Limpa a seleção do aviso
        avisoSelecionado = null;
        // Limpa os campos de texto
        tituloField.clear();
        conteudoField.clear();
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