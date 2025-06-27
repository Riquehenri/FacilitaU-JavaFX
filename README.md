# 💻 Projeto JavaFX CRUD – FacilitaU

> Sistema de gerenciamento acadêmico com JavaFX e persistência em arquivos binários.

## 📚 Sobre o Projeto

Este projeto foi desenvolvido como parte da disciplina de Programação Orientada a Objetos do curso de Engenharia de Software na PUCPR. O objetivo principal é criar um sistema CRUD completo utilizando JavaFX, com foco em aprendizado de manipulação de interface gráfica, persistência de dados e boas práticas em Java.

A aplicação simula um sistema acadêmico onde usuários podem gerenciar avisos, tarefas e planejamentos de estudos, com funcionalidades de login para diferentes perfis (Estudante, Professor, Coordenador).

---

## 🧰 Tecnologias Utilizadas

- **Java 8+**
- **JavaFX**
- **Maven**
- **POO (Programação Orientada a Objetos)**
- **Serialização em arquivos binários (`.dat`)**

---

## 👥 Perfis de Usuário

- **Estudante**: Pode visualizar avisos e gerenciar tarefas e planejamentos.
- **Professor**: Pode cadastrar novos avisos.
- **Coordenador**: Tem acesso a todos os recursos administrativos e de visualização.

---

## 📂 Estrutura de Pastas

```
ProjetoJavaFXCrud/
├── controllers/
│   ├── AvisoController.java
│   ├── PlanejamentoController.java
│   ├── TarefaController.java
│   └── UsuarioController.java
├── models/
│   ├── Aviso.java
│   ├── Planejamento.java
│   ├── Tarefa.java
│   └── Usuario.java
├── views/
│   ├── AvisoView.java
│   ├── PlanejamentoView.java
│   ├── TarefaView.java
│   ├── UsuarioView.java
│   └── PerfilView.java
├── utils/
│   └── Serializacao.java
└── Main.java
```

---

## ✅ Funcionalidades

- 🔐 **Login por tipo de usuário**
- 📢 **CRUD de Avisos**
- 📅 **CRUD de Planejamentos**
- ✅ **CRUD de Tarefas**
- 👤 **Edição de Perfil (dados parciais)**
- 💾 **Persistência com arquivos `.dat` usando `Serializable`**
- 🎯 **Interface construída com componentes JavaFX (sem SceneBuilder)**

---

## 🚀 Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/seuusuario/ProjetoJavaFXCrud.git
```

2. Importe o projeto em sua IDE (como IntelliJ ou Eclipse) como um projeto Maven.

3. Compile e execute a classe `Main.java`.

> Certifique-se de ter o Java 8+ e JavaFX configurados corretamente no ambiente.

---

## 📑 Relatórios Individuais

Cada integrante da equipe contribuiu com diferentes módulos do sistema, aprimorando habilidades em:

- Interface gráfica sem SceneBuilder
- Controle de TableViews e eventos
- Manipulação de arquivos binários
- Estruturação de CRUDs modulares

---

## 👨‍💻 Equipe (FacilitaU - Grupo 6)

- Éden Samuel
- Felipe Carneiro
- Fernando Lopes
- **Henrique Ricardo**
- Hugo Takeda

---

## 📌 Observações

Este projeto é acadêmico e tem como foco o aprendizado. Algumas melhorias que poderiam ser aplicadas futuramente:

- Validação mais robusta de formulários
- Criptografia de senhas
- Interface responsiva
- Uso de banco de dados relacional (como SQLite ou PostgreSQL)

---

## 📖 Licença

Este projeto é de uso acadêmico, distribuído sob a licença MIT. Consulte o arquivo `LICENSE` se necessário.
