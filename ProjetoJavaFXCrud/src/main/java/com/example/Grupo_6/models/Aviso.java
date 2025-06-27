package com.example.Grupo_6.models;

// Importa a interface Serializable para permitir que objetos da classe sejam salvos em arquivos
import java.io.Serializable;

// Classe que define o modelo de dados para um aviso, implementando Serializable para persistência
public class Aviso implements Serializable {
    // Constante que define a versão da classe para serialização, garantindo compatibilidade
    private static final long serialVersionUID = 1L;

    // Atributos privados da classe Aviso
    private int id; // Identificador único do aviso
    private String titulo; // Título do aviso
    private String conteudo; // Conteúdo ou descrição do aviso

    // Construtor que inicializa um objeto Aviso com ID, título e conteúdo
    public Aviso(int id, String titulo, String conteudo) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    // Método para obter o ID do aviso
    public int getId() { return id; }

    // Método para obter o título do aviso
    public String getTitulo() { return titulo; }

    // Método para obter o conteúdo do aviso
    public String getConteudo() { return conteudo; }

    // Método para definir o ID do aviso
    public void setId(int id) { this.id = id; }

    // Método para definir o título do aviso
    public void setTitulo(String titulo) { this.titulo = titulo; }

    // Método para definir o conteúdo do aviso
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    // Método que retorna uma representação em String do objeto Aviso
    @Override
    public String toString() {
        // Retorna uma string formatada com o ID e o título do aviso
        return "Aviso{id=" + id + ", titulo='" + titulo + "'}";
    }
}