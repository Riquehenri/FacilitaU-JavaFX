package com.example.Grupo_6.models;

// Importa a interface Serializable para permitir que objetos da classe sejam salvos em arquivos
import java.io.Serializable;

// Classe que define o modelo de dados para um planejamento, implementando Serializable para persistência
public class Planejamento implements Serializable {
    // Constante que define a versão da classe para serialização, garantindo compatibilidade
    private static final long serialVersionUID = 1L;

    // Atributos privados da classe Planejamento
    private int id; // Identificador único do planejamento
    private String atividade; // Descrição da atividade planejada
    private String data; // Data associada ao planejamento
    private String usuario; // Usuário associado ao planejamento

    // Construtor que inicializa um objeto Planejamento com ID, atividade, data e usuário
    public Planejamento(int id, String atividade, String data, String usuario) {
        this.id = id;
        this.atividade = atividade;
        this.data = data;
        this.usuario = usuario;
    }

    // Método para obter o ID do planejamento
    public int getId() { return id; }

    // Método para obter a atividade do planejamento
    public String getAtividade() { return atividade; }

    // Método para obter a data do planejamento
    public String getData() { return data; }

    // Método para obter o usuário associado ao planejamento
    public String getUsuario() { return usuario; }

    // Método para definir o ID do planejamento
    public void setId(int id) { this.id = id; }

    // Método para definir a atividade do planejamento
    public void setAtividade(String atividade) { this.atividade = atividade; }

    // Método para definir a data do planejamento
    public void setData(String data) { this.data = data; }

    // Método para definir o usuário associado ao planejamento
    public void setUsuario(String usuario) { this.usuario = usuario; }

    // Método que retorna uma representação em String do objeto Planejamento
    @Override
    public String toString() {
        // Retorna uma string formatada com o ID, atividade, data e usuário do planejamento
        return "Planejamento{id=" + id + ", atividade='" + atividade + "', data='" + data + "', usuario='" + usuario + "'}";
    }
}