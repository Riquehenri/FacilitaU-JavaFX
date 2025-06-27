package com.example.Grupo_6.models;

// Importa a interface Serializable para permitir a gravação de objetos em arquivos
import java.io.Serializable;
// Importa a classe LocalDate para representar datas (nascimento do usuário)
import java.time.LocalDate;

// Classe que representa um usuário do sistema
// Implementa Serializable para permitir que os objetos desta classe sejam salvos e lidos de arquivos binários
public class Usuario implements Serializable {

    // Atributos privados que armazenam os dados do usuário
    private String nome;               // Nome completo do usuário
    private String email;              // Email do usuário (utilizado como identificador único)
    private String senha;              // Senha do usuário para autenticação
    private String tipo;               // Tipo de usuário (exemplo: Estudante, Professor, Coordenador)
    private LocalDate dataNascimento;  // Data de nascimento do usuário

    // Construtor principal que inicializa todos os atributos do usuário
    public Usuario(String nome, String email, String senha, String tipo, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.dataNascimento = dataNascimento;
    }

    // Getter para o email do usuário
    public String getEmail() {
        return email;
    }

    // Setter para alterar o email do usuário
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter para a data de nascimento do usuário
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    // Setter para alterar a data de nascimento do usuário
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    // Getter para o nome do usuário
    public String getNome() {
        return nome;
    }

    // Setter para alterar o nome do usuário
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter para a senha do usuário
    public String getSenha() {
        return senha;
    }

    // Setter para alterar a senha do usuário
    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Getter para o tipo de usuário
    public String getTipo() {
        return tipo;
    }

    // Setter para alterar o tipo de usuário
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Método que retorna uma representação textual do usuário
    // Exemplo de retorno: "Maria (Estudante)"
    @Override
    public String toString() {
        return nome + " (" + tipo + ")";
    }
}
