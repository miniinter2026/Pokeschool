package com.example.pokeschool.model;

public class Aluno {

    private int ra;
    private String nomeCompleto;
    private String email;
    private String senha;
    private int sala;

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

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }
}
