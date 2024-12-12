package com.project;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.Set;
import java.util.stream.Collectors;

public class Ciutat {
    private long ciutatId;
    private String nom;
    private String pais;
    private int poblacio;
    private Set<Ciutada> ciutadans;

    public Ciutat() {
    }

    public long getCiutatId() {
        return ciutatId;
    }

    public void setCiutatId(long ciutatId) {
        this.ciutatId = ciutatId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(int poblacio) {
        this.poblacio = poblacio;
    }

    public Set<Ciutada> getCiutadans() {
        return ciutadans;
    }

    public void setCiutadans(Set<Ciutada> ciutadans) {
        this.ciutadans = ciutadans;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Ciutada ciutada : ciutadans) {
            if (str.length() > 0) {
                str.append(" | ");
            }
            str.append(ciutada.getNom());
        }
        return this.getCiutatId() + ": " + this.getNom() + ", " + this.getPais() + ", " + this.getPoblacio() + ", Ciutadans: [" + str + "]";
    }


}
