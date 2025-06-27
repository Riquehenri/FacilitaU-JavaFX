package com.example.Grupo_6.models;

// Importa a interface Serializable para permitir que objetos da classe sejam salvos em arquivos
import java.io.Serializable;

// Classe que define o modelo de dados para uma tarefa, implementando Serializable para persistência
public class Tarefa implements Serializable {
    // Constante que define a versão da classe para serialização, garantindo compatibilidade
    private static final long serialVersionUID = 1L;

    // Atributos privados da classe Tarefa
    private int id; // Identificador único da tarefa
    private String nome; // Nome da tarefa
    private String descricao; // Descrição detalhada da tarefa
    private String usuario; // Usuário associado à tarefa

    // Construtor principal que inicializa um objeto Tarefa com ID, nome, descrição e usuário
    public Tarefa(int id, String nome, String descricao, String usuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.usuario = usuario;
    }

    // Construtor antigo, marcado como obsoleto, para compatibilidade com versões anteriores
    @Deprecated
    public Tarefa(int id, String nome, String descricao) {
        // Chama o construtor principal, definindo o usuário como uma string vazia por padrão
        this(id, nome, descricao, "");
    }

    // Método para obter o ID da tarefa
    public int getId() {
        return id;
    }

    // Método para definir o ID da tarefa
    public void setId(int id) {
        this.id = id;
    }

    // Método para obter o nome da tarefa
    public String getNome() {
        return nome;
    }

    // Método para definir o nome da tarefa
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método para obter a descrição da tarefa
    public String getDescricao() {
        return descricao;
    }

    // Método para definir a descrição da tarefa
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Método para obter o usuário associado à tarefa
    public String getUsuario() {
        return usuario;
    }

    // Método para definir o usuário associado à tarefa
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}