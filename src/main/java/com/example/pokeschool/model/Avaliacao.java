package com.example.pokeschool.model;

public class Avaliacao {

    private int id;
    private int idAluno;
    private int idDisciplina;
    private int idBoletim;

    private String nomeDisciplina;
    private Double n1;
    private Double n2;

    // Getters e Setters
    public Double getN1() {
        return n1;
    }

    public void setN1(Double n1) {
        this.n1 = n1;
    }

    public Double getN2() {
        return n2;
    }

    public void setN2(Double n2) {
        this.n2 = n2;
    }

    //
    public Double getMediaCalculada() {
        if (n1 != null && n2 != null) {
            return (n1 + n2) / 2;
        } else if (n1 != null) {
            return n1; //retorna apenas N1
        } else if (n2 != null) {
            return n2; //retorna apenas N2
        } else {
            return null; //ambas nulas
        }
    }

    public String getStatusCalculado() {
        Double media = getMediaCalculada();
        if (media == null) {
            return "SEM NOTAS";
        } else if (media >= 7) {
            return "APROVADO";
        } else {
            return "REPROVADO";
        }
    }

    // Getters e Setters padrão (int)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }
    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }
    public int getIdBoletim() { return idBoletim; }
    public void setIdBoletim(int idBoletim) { this.idBoletim = idBoletim; }
    public String getNomeDisciplina() { return nomeDisciplina; }
    public void setNomeDisciplina(String nomeDisciplina) { this.nomeDisciplina = nomeDisciplina; }
}