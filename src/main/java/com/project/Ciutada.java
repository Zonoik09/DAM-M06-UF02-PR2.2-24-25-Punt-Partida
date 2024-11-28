package com.project;


public class Ciutada {
    long id;
    long ciutatId;
    String nom;
    String cognom;
    int edat;

    public long getCiutadaId() {
        return id;
    }

    public String getCognom() {
        return cognom;
    }

    public int getEdat() {
        return edat;
    }

    public String getNom() {
        return nom;
    }

    public void setCiutadaId(long id) {
        this.id = id;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }
}
