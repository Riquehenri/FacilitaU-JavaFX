package com.example.Grupo_6.controllers;

import com.example.Grupo_6.models.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private static final String FILE_NAME = "usuarios.dat";

    // Lista todos os usuários salvos no arquivo
    public static List<Usuario> listarUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    // Adiciona um novo usuário à lista e salva
    public static void adicionarUsuario(Usuario usuario) {
        List<Usuario> usuarios = listarUsuarios();
        usuarios.add(usuario);
        salvarUsuarios(usuarios);
    }

    // Remove usuário pelo e-mail
    public static void removerUsuario(String email) {
        List<Usuario> usuarios = listarUsuarios();
        usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
        salvarUsuarios(usuarios);
    }

    // Atualiza substituindo um usuário antigo por um novo
    public static void atualizarUsuario(Usuario usuarioAntigo, Usuario usuarioNovo) {
        List<Usuario> usuarios = listarUsuarios();
        usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(usuarioAntigo.getEmail()));
        usuarios.add(usuarioNovo);
        salvarUsuarios(usuarios);
    }

    // Busca usuário por e-mail e senha (para login)
    public static Usuario buscarUsuario(String email, String senha) {
        return listarUsuarios().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha))
                .findFirst()
                .orElse(null);
    }

    // Edita um usuário com base no e-mail original
    public static boolean editarUsuario(String emailOriginal, String novoNome, String novaSenha, String novoTipo) {
        List<Usuario> usuarios = listarUsuarios();
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(emailOriginal)) {
                u.setNome(novoNome);
                u.setSenha(novaSenha);
                u.setTipo(novoTipo);
                salvarUsuarios(usuarios);
                return true;
            }
        }
        return false;
    }

    // Salva a lista de usuários no arquivo
    private static void salvarUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
