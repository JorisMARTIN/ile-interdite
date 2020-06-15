/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.aventuriers;

import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.util.Utils.Pion;

/**
 *
 * @author tang
 */
public class Ingenieur extends Aventurier{
    /*construteur*/
    public Ingenieur(IleInterdite ileInterdite, Pion pion) {
        super(ileInterdite, pion);
    }
    
    /*méthodes*/
    @Override
    public boolean peutAssecher(Tuile tuile) {
        return false;
    }
    
}
