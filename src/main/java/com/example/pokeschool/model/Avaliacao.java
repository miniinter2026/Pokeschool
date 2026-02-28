package com.example.pokeschool.model;

public class Avaliacao {

    private int id;
    private int idAluno;
    private int idDisciplina;
    private int idBoletim;

    private String nomeDisciplina;
    private double n1;
    private double n2;
    private double media;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public int getIdBoletim() {
        return idBoletim;
    }

    public void setIdBoletim(int idBoletim) {
        this.idBoletim = idBoletim;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public double getN1() {
        return n1;
    }

    public void setN1(double n1) {
        this.n1 = n1;
    }

    public double getN2() {
        return n2;
    }

    public void setN2(double n2) {
        this.n2 = n2;
    }
    

    public double getMediaCalculada() {
        return (this.n1 + this.n2) / 2;
    }

    public String getStatusCalculado() {
        if (getMediaCalculada() >= 7) {
            return "Aprovado";
        } else {
            return "Reprovado";
        }
    }
}