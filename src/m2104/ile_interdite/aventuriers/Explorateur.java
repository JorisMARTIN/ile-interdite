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
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
* 
*/
public class Explorateur extends Aventurier{
    /*construteur*/
    public Explorateur(IleInterdite ileInterdite) {
        super(ileInterdite, Pion.VERT);
    }
    
    /*méthodes*/
    @Override
    public boolean peutAssecher(Tuile tuile) {
        if (tuile == null || !tuile.isInnondee() || tuile.isRetiree() || getPosition() == tuile) {
            return false;
        }

        boolean peutNormal = super.peutAssecher(tuile);

        int indexTuileCible = this.getIleInterdite().getGrille().getTuiles(true).indexOf(tuile);
        int indexTuileActuelle = this.getIleInterdite().getGrille().getTuiles(true).indexOf(getPosition());
        
        return peutNormal || ((indexTuileActuelle < 28 && indexTuileActuelle + 7 == indexTuileCible && (indexTuileActuelle % 6) != 5)
                || (indexTuileActuelle < 31 && indexTuileActuelle + 5 == indexTuileCible && (indexTuileActuelle % 6) != 0)
                || (indexTuileActuelle > 6 && indexTuileActuelle - 7 == indexTuileCible && (indexTuileActuelle % 6) != 0)
                || (indexTuileActuelle > 4 && indexTuileActuelle - 5 == indexTuileCible && (indexTuileActuelle % 6) != 5));
    }

    @Override
    public boolean peutSeDeplacer(Tuile tuile) {
        if (tuile == null || tuile.isRetiree() || getPosition() == tuile) {
            return false;
        }

        boolean peutNormal = super.peutSeDeplacer(tuile);

        int indexTuileCible = this.getIleInterdite().getGrille().getTuiles(true).indexOf(tuile);
        int indexTuileActuelle = this.getIleInterdite().getGrille().getTuiles(true).indexOf(getPosition());

        return peutNormal || ((indexTuileActuelle < 28 && indexTuileActuelle + 7 == indexTuileCible && (indexTuileActuelle % 6) != 5)
                || (indexTuileActuelle < 31 && indexTuileActuelle + 5 == indexTuileCible && (indexTuileActuelle % 6) != 0)
                || (indexTuileActuelle > 6 && indexTuileActuelle - 7 == indexTuileCible && (indexTuileActuelle % 6) != 0)
                || (indexTuileActuelle > 4 && indexTuileActuelle - 5 == indexTuileCible && (indexTuileActuelle % 6) != 5));
    }
    
}
