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
public class Explorateur extends Aventurier{
    /*construteur*/
    public Explorateur(String nom, IleInterdite ileInterdite) {
        super(nom, ileInterdite);
    }
    
    /*méthodes*/
    @Override
    public boolean peutAssecher(Tuile tuile) {
        return false;
    }

    @Override
    public boolean peutSeDeplacer(Tuile tuile) {
        return false;
    }
    
}
