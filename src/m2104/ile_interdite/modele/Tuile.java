/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.modele;

import java.util.ArrayList;

/**
 *
 * @author tang
 */
class Tuile {
    /*attribus*/
    private String nom;
    private EtatTuile etat;
    private Tresor tresor;
    private ArrayList<Aventurier> aventuriers;
    
    /*construteur*/
    public Tuile(String nom, EtatTuile etat, Tresor tresor) {
        this.nom = nom;
        this.etat = etat;
        this.tresor = tresor;
        this.aventuriers = new ArrayList<>();
    }
    
    /*méthodes*/
    public boolean isInnondee() {
        return false;
    }
    
    public boolean isRetiree() {
        return false;
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public void setEtat(EtatTuile etat) {
        this.etat = etat;
    }
    
    public ArrayList<Aventurier> getAventuriers() {
        return this.aventuriers;
    }
}