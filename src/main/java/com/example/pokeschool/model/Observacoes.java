package com.example.pokeschool.model;

import java.sql.Date;

public class Observacoes {

    private int id;
    private Date data;
    private String descricao;
    private int idDisciplina;
    private int idAluno;
    private int idProfessor;

    // Método construtor completo
    public Observacoes(int id, Date data, String descricao, int idDisciplina, int idAluno, int idProfessor) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.idDisciplina = idDisciplina;
        this.idAluno = idAluno;
        this.idProfessor = idProfessor;
    }

    // Método construtor vazio
    public Observacoes() {

    }

    // Métodos getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }
}
