/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.aventuriers;

import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.modele.Tuile;

/**
 *
 * @author tang
 */
public class Pilote extends Aventurier{
    /*construteur*/
    public Pilote(IleInterdite ileInterdite) {
        super(ileInterdite, null);
    }
    
    /*méthodes*/
    @Override
    public boolean peutSeDeplacer(Tuile tuile) {
        return false;
    }
    
}
