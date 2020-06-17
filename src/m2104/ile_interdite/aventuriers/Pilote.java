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
    private boolean utiliseePouvoir;
    
    /*construteur*/
    public Pilote(IleInterdite ileInterdite) {
        super(ileInterdite, Pion.BLEU);
    }
    
    /*méthodes*/
    @Override
    public boolean peutSeDeplacer(Tuile tuile) {
        boolean peutNormal = super.peutSeDeplacer(tuile);

        if(!utiliseePouvoir && tuile != null && tuile.getEtat() != EtatTuile.RETIREE && tuile != this.getPosition()) {
            return true;
        } else {
            return peutNormal;
        }
    }   
    
    @Override
    public void deplacer(Tuile tuile) {
        if (!utiliseePouvoir) {
            utiliseePouvoir = true;
            if (peutSeDeplacer(tuile)) {
                utiliseePouvoir = false;
            }
        }
        super.deplacer(tuile);
    }
    
    @Override
    public void initActionsRestantes() {
        super.initActionsRestantes();
        utiliseePouvoir = false;
    }
}
