/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.aventuriers;

import m2104.ile_interdite.modele.EtatTuile;
import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.util.Utils.Pion;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
* 
*/
public class Pilote extends Aventurier{
    /*attribut utilisation du pouvoir*/
    private boolean utiliseePouvoir = true;
    /*construteur*/
    public Pilote(IleInterdite ileInterdite) {
        super(ileInterdite, Pion.BLEU);
    }
    
    /*méthodes*/
    @Override
    public boolean peutSeDeplacer(Tuile tuile) {
        boolean peutNormal = super.peutAssecher(tuile);

        System.out.println("Etat : " + tuile.getEtat() + " tuile==null ? " + tuile==null);

        if(tuile.getEtat() != EtatTuile.RETIREE){

            if (!utiliseePouvoir && tuile != null) {
                utiliseePouvoir = true;
                return true;
            } else if (utiliseePouvoir && tuile != null) {
                utiliseePouvoir = false;
                return peutNormal;
            } else {
                utiliseePouvoir = false;
                return peutNormal;
            }
        } else {
            utiliseePouvoir = false;
            return peutNormal;
        }
    }   
}
