package com.example.Grupo_6.controllers;

// Importa a classe Disciplina, que define o modelo de dados para uma disciplina
import com.example.Grupo_6.models.Disciplina;
// Importa classes para manipulação de entrada/saída (arquivos)
import java.io.*;
// Importa ArrayList e List para gerenciar coleções de disciplinas
import java.util.ArrayList;
import java.util.List;

// Classe que gerencia operações CRUD (criar, ler, atualizar, deletar) para disciplinas
public class DisciplinaController {
    // Define o nome do arquivo onde as disciplinas serão salvas (constante)
    private static final String FILE_NAME = "disciplinas.dat";

    // Método para listar todas as disciplinas
    public static List<Disciplina> listarDisciplinas() {
        // Usa try-with-resources para garantir que o arquivo seja fechado
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            // Lê o objeto do arquivo e converte para uma lista de Disciplina
            return (List<Disciplina>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Se houver erro (ex.: arquivo não existe), retorna uma lista vazia
            return new ArrayList<>();
        }
    }

    // Método para adicionar uma nova disciplina à lista
    public static void adicionarDisciplina(Disciplina disciplina) {
        // Carrega a lista atual de disciplinas
        List<Disciplina> disciplinas = listarDisciplinas();
        // Adiciona a nova disciplina à lista
        disciplinas.add(disciplina);
        // Salva a lista atualizada no arquivo
        salvarDisciplinas(disciplinas);
    }

    // Método para remover uma disciplina com base no ID
    public static void removerDisciplina(int id) {
        // Carrega a lista atual de disciplinas
        List<Disciplina> disciplinas = listarDisciplinas();
        // Remove a disciplina cuja ID corresponde ao parâmetro
        disciplinas.removeIf(d -> d.getId() == id);
        // Salva a lista atualizada no arquivo
        salvarDisciplinas(disciplinas);
    }

    // Método para atualizar uma disciplina existente com base no ID
    public static void atualizarDisciplina(int id, Disciplina nova) {
        // Carrega a lista atual de disciplinas
        List<Disciplina> disciplinas = listarDisciplinas();
        // Percorre a lista de disciplinas
        for (int i = 0; i < disciplinas.size(); i++) {
            // Se encontrar a disciplina com o ID correspondente
            if (disciplinas.get(i).getId() == id) {
                // Substitui a disciplina antiga pela nova
                disciplinas.set(i, nova);
                // Salva a lista atualizada no arquivo
                salvarDisciplinas(disciplinas);
                // Sai do método após a atualização
                return;
            }
        }
    }

    // Método para salvar a lista de disciplinas no arquivo binário
    private static void salvarDisciplinas(List<Disciplina> disciplinas) {
        // Usa try-with-resources para garantir que o arquivo seja fechado
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            // Escreve a lista de disciplinas no arquivo
            oos.writeObject(disciplinas);
        } catch (IOException e) {
            // Imprime detalhes do erro, caso ocorra
            e.printStackTrace();
        }
    }
}