package com.example.Grupo_6.controllers;

// Importa a classe Tarefa, que define o modelo de dados para uma tarefa
import com.example.Grupo_6.models.Tarefa;
// Importa classes para manipulação de entrada/saída (arquivos)
import java.io.*;
// Importa ArrayList e List para gerenciar coleções de tarefas
import java.util.ArrayList;
import java.util.List;

// Classe que gerencia operações CRUD (criar, ler, atualizar, deletar) para tarefas
public class TarefaController {

    // Método que gera o nome do arquivo com base no usuário
    private static String getCaminhoArquivo(String usuario) {
        // Retorna o nome do arquivo no formato "tarefas_usuario.dat", com o usuário em minúsculas
        return "tarefas_" + usuario.toLowerCase() + ".dat";
    }

    // Método para adicionar uma nova tarefa à lista
    public static void adicionar(Tarefa tarefa) {
        // Carrega a lista de tarefas do usuário informado
        List<Tarefa> tarefas = listar(tarefa.getUsuario());
        // Adiciona a nova tarefa à lista
        tarefas.add(tarefa);
        // Salva a lista atualizada no arquivo correspondente ao usuário
        salvar(tarefa.getUsuario(), tarefas);
    }

    // Método para remover uma tarefa com base no ID e usuário
    public static void remover(int id, String usuario) {
        // Carrega a lista de tarefas do usuário informado
        List<Tarefa> tarefas = listar(usuario);
        // Remove a tarefa cuja ID corresponde ao parâmetro
        tarefas.removeIf(t -> t.getId() == id);
        // Salva a lista atualizada no arquivo correspondente ao usuário
        salvar(usuario, tarefas);
    }

    // Método para listar todas as tarefas de um usuário
    public static List<Tarefa> listar(String usuario) {
        // Obtém o caminho do arquivo para o usuário
        String caminho = getCaminhoArquivo(usuario);
        // Usa try-with-resources para garantir que o arquivo seja fechado
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            // Lê o objeto do arquivo e converte para uma lista de Tarefa
            return (List<Tarefa>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Se houver erro (ex.: arquivo não existe), retorna uma lista vazia
            return new ArrayList<>();
        }
    }

    // Método para atualizar uma tarefa existente com base no ID
    public static void atualizarTarefa(int id, Tarefa nova) {
        // Carrega a lista de tarefas do usuário informado
        List<Tarefa> tarefas = listar(nova.getUsuario());
        // Percorre a lista de tarefas
        for (int i = 0; i < tarefas.size(); i++) {
            // Se encontrar a tarefa com o ID correspondente
            if (tarefas.get(i).getId() == id) {
                // Substitui a tarefa antiga pela nova
                tarefas.set(i, nova);
                // Salva a lista atualizada no arquivo correspondente ao usuário
                salvar(nova.getUsuario(), tarefas);
                // Sai do método após a atualização
                return;
            }
        }
    }

    // Método para salvar a lista de tarefas no arquivo binário
    private static void salvar(String usuario, List<Tarefa> tarefas) {
        // Obtém o caminho do arquivo para o usuário
        String caminho = getCaminhoArquivo(usuario);
        // Usa try-with-resources para garantir que o arquivo seja fechado
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            // Escreve a lista de tarefas no arquivo
            oos.writeObject(tarefas);
        } catch (IOException e) {
            // Imprime detalhes do erro, caso ocorra
            e.printStackTrace();
        }
    }
}