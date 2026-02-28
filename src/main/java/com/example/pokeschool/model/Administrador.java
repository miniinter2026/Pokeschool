package com.example.pokeschool.model;

public class Administrador {
    private int id;
    private String nome;
    private String nomeUsuario;
    private String senha;
    private String email;

    // Construtor vazio
    public Administrador() {}

    // Construtor com parâmetros
    public Administrador(int id, String nome, String nomeUsuario, String senha, String email) {
        this.id = id;
        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.email = email;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}