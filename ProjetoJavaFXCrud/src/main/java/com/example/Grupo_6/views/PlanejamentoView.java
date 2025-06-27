package com.example.Grupo_6.views;

// Importa a classe PlanejamentoController para interagir com a lógica de planejamentos
import com.example.Grupo_6.controllers.PlanejamentoController;
// Importa a classe Planejamento, que define o modelo de dados
import com.example.Grupo_6.models.Planejamento;
// Importa classes do JavaFX para criar a interface gráfica
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Classe que define a interface gráfica para gerenciamento de planejamentos
public class PlanejamentoView {

    // Componentes da interface gráfica
    private TableView<Planejamento> table; // Tabela para exibir os planejamentos
    private ObservableList<Planejamento> planejamentoList; // Lista observável para atualizar a tabela
    private String usuario; // Nome do usuário associado aos planejamentos
    private TextField atividadeField; // Campo de texto para a atividade do planejamento
    private TextField dataField; // Campo de texto para a data do planejamento
    private Button adicionarBtn; // Botão para adicionar um novo planejamento
    private Button editarBtn; // Botão para editar um planejamento selecionado
    private Button salvarEdicaoBtn; // Botão para salvar as alterações de um planejamento
    private Planejamento planejamentoSelecionado; // Armazena o planejamento selecionado para edição

    // Construtor que inicializa a view com o nome do usuário
    public PlanejamentoView(String usuario) {
        this.usuario = usuario;
    }

    // Método principal para iniciar a interface de gerenciamento de planejamentos
    public void start(Stage stage) {
        // Define o título da janela com o nome do usuário
        stage.setTitle("Planejamento de Estudos - " + usuario);

        // Inicializa a lista observável com os planejamentos do controlador para o usuário
        planejamentoList = FXCollections.observableArrayList(PlanejamentoController.listar(usuario));

        // Cria a tabela e associa a lista de planejamentos
        table = new TableView<>();
        table.setItems(planejamentoList);

        // Define a coluna para o ID do planejamento
        TableColumn<Planejamento, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        // Define a coluna para a atividade do planejamento
        TableColumn<Planejamento, String> atividadeCol = new TableColumn<>("Atividade");
        atividadeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAtividade()));

        // Define a coluna para a data do planejamento
        TableColumn<Planejamento, String> dataCol = new TableColumn<>("Data");
        dataCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getData()));

        // Adiciona as colunas à tabela
        table.getColumns().addAll(idCol, atividadeCol, dataCol);

        // Cria o campo de texto para inserir a atividade
        atividadeField = new TextField();
        atividadeField.setPromptText("Atividade");

        // Cria o campo de texto para inserir a data
        dataField = new TextField();
        dataField.setPromptText("Data (dd/mm/aaaa)");

        // Cria o botão para adicionar um novo planejamento
        adicionarBtn = new Button("Adicionar");
        adicionarBtn.setOnAction(e -> {
            // Verifica se os campos estão vazios
            if (atividadeField.getText().isEmpty() || dataField.getText().isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos!");
                return;
            }

            // Gera um novo ID baseado no maior ID existente ou 1 se a lista estiver vazia
            int novoId = planejamentoList.isEmpty() ? 1 :
                    planejamentoList.stream().mapToInt(Planejamento::getId).max().getAsInt() + 1;

            // Cria um novo planejamento com os dados inseridos
            Planejamento p = new Planejamento(novoId, atividadeField.getText(), dataField.getText(), usuario);
            // Adiciona o planejamento ao controlador
            PlanejamentoController.adicionarPlanejamento(p);
            // Atualiza a lista na tabela
            planejamentoList.setAll(PlanejamentoController.listar(usuario));
            // Limpa os campos
            atividadeField.clear();
            dataField.clear();
            // Reseta o formulário
            resetForm();
        });

        // Cria o botão para editar um planejamento selecionado
        editarBtn = new Button("Editar Selecionado");
        editarBtn.setOnAction(e -> {
            // Obtém o planejamento selecionado na tabela
            planejamentoSelecionado = table.getSelectionModel().getSelectedItem();
            if (planejamentoSelecionado == null) {
                mostrarAlerta("Aviso", "Nenhum planejamento selecionado!");
                return;
            }

            // Preenche os campos com os dados do planejamento selecionado
            atividadeField.setText(planejamentoSelecionado.getAtividade());
            dataField.setText(planejamentoSelecionado.getData());
            // Desativa os botões de adicionar e editar
            adicionarBtn.setDisable(true);
            editarBtn.setDisable(true);
            // Ativa o botão de salvar edição
            salvarEdicaoBtn.setDisable(false);
        });

        // Cria o botão para salvar as alterações de um planejamento
        salvarEdicaoBtn = new Button("Salvar Edição");
        salvarEdicaoBtn.setDisable(true); // Inicialmente desativado
        salvarEdicaoBtn.setOnAction(e -> {
            // Verifica se os campos estão vazios
            if (atividadeField.getText().isEmpty() || dataField.getText().isEmpty()) {
                mostrarAlerta("Erro", "Preencha todos os campos!");
                return;
            }

            // Cria um novo planejamento com os dados atualizados, mantendo o mesmo ID
            Planejamento planejamentoAtualizado = new Planejamento(
                    planejamentoSelecionado.getId(),
                    atividadeField.getText(),
                    dataField.getText(),
                    usuario
            );

            // Atualiza o planejamento no controlador
            PlanejamentoController.atualizarPlanejamento(planejamentoSelecionado.getId(), planejamentoAtualizado);
            // Atualiza a lista na tabela
            planejamentoList.setAll(PlanejamentoController.listar(usuario));
            // Limpa os campos
            atividadeField.clear();
            dataField.clear();
            // Reseta o formulário
            resetForm();
        });

        // Cria o botão para remover um planejamento selecionado
        Button removerBtn = new Button("Remover Selecionado");
        removerBtn.setOnAction(e -> {
            // Obtém o planejamento selecionado na tabela
            Planejamento selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                // Remove o planejamento do controlador
                PlanejamentoController.removerPlanejamento(selecionado.getId(), usuario);
                // Atualiza a lista na tabela
                planejamentoList.setAll(PlanejamentoController.listar(usuario));
                // Reseta o formulário
                resetForm();
            } else {
                mostrarAlerta("Aviso", "Nenhum planejamento selecionado!");
            }
        });

        // Organiza os componentes do formulário em um layout vertical
        VBox form = new VBox(10, atividadeField, dataField, adicionarBtn, editarBtn, salvarEdicaoBtn, removerBtn);
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
        // Limpa a seleção do planejamento
        planejamentoSelecionado = null;
        // Limpa os campos de texto
        atividadeField.clear();
        dataField.clear();
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