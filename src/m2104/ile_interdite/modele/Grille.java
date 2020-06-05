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
public class Grille {
    /*attribus*/
    private ArrayList<Tuile> tuiles;
    
    /*construteur*/
    public Grille() {
        this.tuiles = new ArrayList<>();
    }
    
    /*méthodes*/
    public ArrayList<Tuile> getTuiles() {
        return this.tuiles;
    }
    
    public Tuile getTuile(String nom) {
        return null;
    }
}
