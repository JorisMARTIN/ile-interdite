/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.aventuriers;

import m2104.ile_interdite.cartes.Carte;
import m2104.ile_interdite.cartes.CarteHelicoptere;
import m2104.ile_interdite.cartes.CarteSacDeSable;
import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.util.Utils.Pion;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
* 
*/
public class Messager extends Aventurier{
    /*construteur*/
    public Messager(IleInterdite ileInterdite) {
        super(ileInterdite, Pion.ORANGE);
    }
    
    /*méthodes*/
    @Override
    public boolean peutDonnerCarteTresor(Aventurier receveur, Carte carte) {
    	
    	return receveur.getMain().size() < 5 && !(carte instanceof CarteHelicoptere) && !(carte instanceof CarteSacDeSable);
    }
    
}
