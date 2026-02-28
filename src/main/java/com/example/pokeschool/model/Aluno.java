package com.example.pokeschool.model;

public class Aluno {

    private int ra;
    private String nomeCompleto;
    private String email;
    private String senha;
    private int idSala;

    // método construtor

    public Aluno(int ra, String nomeCompleto, String email, String senha, int idSala) {
        this.ra = ra;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.idSala = idSala;
    }

    // métodos getters e setters
    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
        this.ra = ra;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int sala) {
        this.idSala = idSala;
    }
}