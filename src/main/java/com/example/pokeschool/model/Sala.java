package com.example.pokeschool.model;

public class Sala {

    private int id;
    private int serie;
    private String letra;

    // Método construtor
    public Sala(int id, int serie, String letra) {
        this.id = id;
        this.serie = serie;
        this.letra = letra;
    }

    // Método construtor vazio
    public Sala() {

    }

    // Métodos getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }
}
