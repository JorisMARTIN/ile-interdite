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
public class Pilote extends Aventurier{

    public Pilote(String nom) {
        super(nom);
    }

    @Override
    public boolean peutSeDeplacer(Tuile ) {
        return false;
    }
    
}
