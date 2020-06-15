/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.aventuriers;

import m2104.ile_interdite.cartes.Carte;
import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.util.Utils.Pion;

/**
 *
 * @author tang
 */
public class Messager extends Aventurier{
    /*construteur*/
    public Messager(IleInterdite ileInterdite) {
        super(ileInterdite, Pion.ORANGE);
    }
    
    /*méthodes*/
    @Override
    public void donnerCarteTresor(Aventurier a, Carte carte) {
        super.donnerCarteTresor(a, carte); //To change body of generated methods, choose Tools | Templates.
    }
    
}
