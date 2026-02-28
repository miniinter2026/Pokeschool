package com.example.pokeschool.model;

import java.time.LocalDateTime;

public class Token {
    int id;
    String token;
    String emailUsuario;

    LocalDateTime dataCriacao;
    LocalDateTime dataExpiracao;
    boolean utilizado;

    // Método construtor
    public Token(int id, String token, String emailUsuario, LocalDateTime dataCriacao, LocalDateTime dataExpiracao, boolean utilizado) {
        this.id = id;
        this.token = token;
        this.emailUsuario = emailUsuario;
        this.dataCriacao = dataCriacao;
        this.dataExpiracao = dataExpiracao;
        this.utilizado = utilizado;
    }

    // Método construtor sem o id

    public Token(String token, String emailUsuario, LocalDateTime dataCriacao, LocalDateTime dataExpiracao, boolean utilizado) {
        this.token = token;
        this.emailUsuario = emailUsuario;
        this.dataCriacao = dataCriacao;
        this.dataExpiracao = dataExpiracao;
        this.utilizado = utilizado;
    }

    // métodos getters
    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public boolean isUtilizado() {
        return utilizado;
    }

    // métodos setters

    public void setToken(String token) {
        this.token = token;
    }

    public void setEmail_usuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public void setUtilizado(boolean utilizado) {
        this.utilizado = utilizado;
    }
}
