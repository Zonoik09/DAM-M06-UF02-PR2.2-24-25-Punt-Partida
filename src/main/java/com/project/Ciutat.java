package com.project;


import java.util.Set;

public class Ciutat {
    long ciutatId;
    String nom;
    String pais;
    int codiPostal;
    private Set<Ciutada> ciutadans;

    public long getCiutatId() {
        return ciutatId;
    }

    public String getNom() {
        return nom;
    }

    public String getPais() {
        return pais;
    }

    public int getPoblacio() {
        return 0;
    }

    public Set<Ciutada> getCiutadans() {
        return ciutadans;
    }

    public void setCiutadans(Set<Ciutada> ciutadans) {
        this.ciutadans = ciutadans;
    }
}