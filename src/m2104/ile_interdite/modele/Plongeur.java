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
public class Plongeur extends Aventurier{
    /*construteur*/
    public Plongeur(String nom) {
        super(nom);
    }
    
    /*méthodes*/
    @Override
    public boolean peutSeDeplacer(Tuile ) {
        return false;
    }
}
