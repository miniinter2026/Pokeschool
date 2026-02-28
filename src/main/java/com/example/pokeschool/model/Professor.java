package com.example.pokeschool.model;

public class Professor {
    private int id;
    private String nomeCompleto;
    private String nomeUsuario;
    private String senha;
    private String email;
    private int idDisciplina;
    private String nomeDisciplina;

    // Construtor vazio
    public Professor() {}

    // Construtor com parâmetros
    public Professor(int id, String nomeCompleto, String nomeUsuario, String senha, String email) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.email = email;
    }

    // Construtor sem id para cadastro
    public Professor(String nomeCompleto, String nomeUsuario, String senha, String email) {
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.email = email;
    }

    public Professor(String nomeCompleto, String nomeUsuario, String senha) {
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }

    public String getNomeDisciplina() { return nomeDisciplina; }
    public void setNomeDisciplina(String nomeDisciplina) { this.nomeDisciplina = nomeDisciplina; }
}