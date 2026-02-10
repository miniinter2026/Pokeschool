package com.example.pokeschool.model;

public class Boletim {

    private int id;
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

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
