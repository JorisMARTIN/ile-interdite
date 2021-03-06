/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.modele;

import java.util.ArrayList;

import m2104.ile_interdite.aventuriers.Aventurier;
import m2104.ile_interdite.util.Utils;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
*/
public class Tuile {
    /*attribus*/
    private String nom;
    private EtatTuile etat;
    private Utils.Tresor tresor;
    private ArrayList<Aventurier> aventuriers;
    
    /*construteur*/
    public Tuile(String nom, EtatTuile etat, Utils.Tresor tresor) {
        this.nom = nom;
        this.etat = etat;
        this.tresor = tresor;
        this.aventuriers = new ArrayList<>();
    }
    
    /*méthodes*/
    public boolean isInnondee() {
        return this.etat == EtatTuile.INONDEE;
    }
    
    public boolean isRetiree() {
        return this.etat == EtatTuile.RETIREE;
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public void setEtat(EtatTuile etat) {
        this.etat = etat;
    }
    
    public EtatTuile getEtat() {
    	return this.etat;
    }
    
    public ArrayList<Aventurier> getAventuriers() {
        return this.aventuriers;
    }
    
    public void addAventurier(Aventurier a) {
    	this.aventuriers.add(a);
    }
    
    public void removeAventurier(Aventurier a) {
    	this.aventuriers.remove(a);
    }
    
    public Utils.Tresor getTresor(){
    	return this.tresor;
    }
}
