package com.example.Grupo_6.controllers;

// Importa a classe Planejamento, que define o modelo de dados para um planejamento
import com.example.Grupo_6.models.Planejamento;
// Importa classes para manipulação de entrada/saída (arquivos)
import java.io.*;
// Importa ArrayList e List para gerenciar coleções de planejamentos
import java.util.ArrayList;
import java.util.List;
// Importa Collectors para operações com streams (não usado diretamente aqui, mas presente no código original)


// Classe que gerencia operações CRUD (criar, ler, atualizar, deletar) para planejamentos
public class PlanejamentoController {

    // Método que gera o nome do arquivo com base no usuário
    private static String getCaminhoArquivo(String usuario) {
        // Retorna o nome do arquivo no formato "planejamentos_usuario.dat", com o usuário em minúsculas
        return "planejamentos_" + usuario.toLowerCase() + ".dat";
    }

    // Método para adicionar um novo planejamento à lista
    public static void adicionarPlanejamento(Planejamento p) {
        // Carrega a lista de planejamentos do usuário informado
        List<Planejamento> planejamentos = listar(p.getUsuario());
        // Adiciona o novo planejamento à lista
        planejamentos.add(p);
        // Salva a lista atualizada no arquivo correspondente ao usuário
        salvar(p.getUsuario(), planejamentos);
    }

    // Método para remover um planejamento com base no ID e usuário
    public static void removerPlanejamento(int id, String usuario) {
        // Carrega a lista de planejamentos do usuário informado
        List<Planejamento> planejamentos = listar(usuario);
        // Remove o planejamento cuja ID corresponde ao parâmetro
        planejamentos.removeIf(p -> p.getId() == id);
        // Salva a lista atualizada no arquivo correspondente ao usuário
        salvar(usuario, planejamentos);
    }

    // Método para listar todos os planejamentos de um usuário
    public static List<Planejamento> listar(String usuario) {
        // Obtém o caminho do arquivo para o usuário
        String caminho = getCaminhoArquivo(usuario);
        // Usa try-with-resources para garantir que o arquivo seja fechado
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            // Lê o objeto do arquivo e converte para uma lista de Planejamento
            return (List<Planejamento>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Se houver erro (ex.: arquivo não existe), retorna uma lista vazia
            return new ArrayList<>();
        }
    }

    // Método para salvar a lista de planejamentos no arquivo binário
    private static void salvar(String usuario, List<Planejamento> planejamentos) {
        // Obtém o caminho do arquivo para o usuário
        String caminho = getCaminhoArquivo(usuario);
        // Usa try-with-resources para garantir que o arquivo seja fechado
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            // Escreve a lista de planejamentos no arquivo
            oos.writeObject(planejamentos);
        } catch (IOException e) {
            // Imprime detalhes do erro, caso ocorra
            e.printStackTrace();
        }
    }

    // Método para atualizar um planejamento existente com base no ID
    public static void atualizarPlanejamento(int id, Planejamento novo) {
        // Carrega a lista de planejamentos do usuário informado
        List<Planejamento> planejamentos = listar(novo.getUsuario());
        // Percorre a lista de planejamentos
        for (int i = 0; i < planejamentos.size(); i++) {
            // Se encontrar o planejamento com o ID correspondente
            if (planejamentos.get(i).getId() == id) {
                // Substitui o planejamento antigo pelo novo
                planejamentos.set(i, novo);
                // Salva a lista atualizada no arquivo correspondente ao usuário
                salvar(novo.getUsuario(), planejamentos);
                // Sai do método após a atualização
                return;
            }
        }
    }
}