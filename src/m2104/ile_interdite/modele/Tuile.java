/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.modele;

/**
 *
 * @author tang
 */
class Tuile {
    /*attribus*/
    private String nom;
    private EtatTuile etat;
    private Tresor tresor;
    
    /*construteur*/
    public Tuile(String nom, EtatTuile etat, Tresor tresor) {
        this.nom = nom;
        this.etat = etat;
        this.tresor = tresor;
    }
    
    /*méthodes*/
}
