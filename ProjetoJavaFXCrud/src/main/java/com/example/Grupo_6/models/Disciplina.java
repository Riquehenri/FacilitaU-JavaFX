package com.example.Grupo_6.models;

// Importa a interface Serializable para permitir que objetos da classe sejam salvos em arquivos
import java.io.Serializable;

// Classe que define o modelo de dados para uma disciplina, implementando Serializable para persistência
public class Disciplina implements Serializable {
    // Atributos privados da classe Disciplina
    private int id; // Identificador único da disciplina
    private String nome; // Nome da disciplina
    private int cargaHoraria; // Carga horária da disciplina em horas

    // Construtor que inicializa um objeto Disciplina com ID, nome e carga horária
    public Disciplina(int id, String nome, int cargaHoraria) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    // Método para obter o ID da disciplina
    public int getId() {
        return id;
    }

    // Método para obter o nome da disciplina
    public String getNome() {
        return nome;
    }

    // Método para obter a carga horária da disciplina
    public int getCargaHoraria() {
        return cargaHoraria;
    }

    // Método para definir o nome da disciplina
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método para definir a carga horária da disciplina
    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
}