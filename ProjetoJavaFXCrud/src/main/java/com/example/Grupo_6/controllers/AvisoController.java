package com.example.Grupo_6.controllers;

// Importa a classe Aviso, que define o modelo de dados para um aviso
import com.example.Grupo_6.models.Aviso;
// Importa classes para manipulação de entrada/saída (arquivos)
import java.io.*;
// Importa ArrayList e List para gerenciar coleções de avisos
import java.util.ArrayList;
import java.util.List;

// Classe que gerencia operações CRUD (criar, ler, atualizar, deletar) para avisos
public class AvisoController {
    // Define o nome do arquivo onde os avisos serão salvos (constante)
    private static final String FILE_NAME = "avisos.bin";
    // Lista estática para armazenar todos os avisos, compartilhada pela classe
    private static List<Aviso> avisos = new ArrayList<>();

    // Bloco estático que executa ao carregar a classe, chamando carregarDados()
    static {
        carregarDados();
    }

    // Método para carregar a lista de avisos do arquivo binário
    private static void carregarDados() {
        // Usa try-with-resources para garantir que o arquivo seja fechado
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            // Lê o objeto do arquivo e converte para uma lista de Aviso
            avisos = (List<Aviso>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Se houver erro (ex.: arquivo não existe), cria uma lista vazia
            avisos = new ArrayList<>();
        }
    }

    // Método para salvar a lista de avisos no arquivo binário
    private static void salvarDados() {
        // Usa try-with-resources para garantir que o arquivo seja fechado
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            // Escreve a lista de avisos no arquivo
            oos.writeObject(avisos);
        } catch (IOException e) {
            // Imprime detalhes do erro, caso ocorra
            e.printStackTrace();
        }
    }

    // Método para listar todos os avisos
    public static List<Aviso> listarAvisos() {
        // Retorna uma cópia da lista para proteger a original
        return new ArrayList<>(avisos);
    }

    // Método para adicionar um novo aviso à lista
    public static void adicionarAviso(Aviso aviso) {
        // Adiciona o aviso à lista
        avisos.add(aviso);
        // Salva a lista atualizada no arquivo
        salvarDados();
    }

    // Método para remover um aviso com base no ID
    public static void removerAviso(int id) {
        // Remove o aviso cuja ID corresponde ao parâmetro
        avisos.removeIf(a -> a.getId() == id);
        // Salva a lista atualizada no arquivo
        salvarDados();
    }

    // Método para atualizar um aviso existente com base no ID
    public static void atualizarAviso(int id, Aviso novo) {
        // Percorre a lista de avisos
        for (int i = 0; i < avisos.size(); i++) {
            // Se encontrar o aviso com o ID correspondente
            if (avisos.get(i).getId() == id) {
                // Substitui o aviso antigo pelo novo
                avisos.set(i, novo);
                // Salva a lista atualizada no arquivo
                salvarDados();
                // Sai do método após a atualização
                return;
            }
        }
    }
}