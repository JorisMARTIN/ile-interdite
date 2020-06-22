/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.aventuriers;

import java.util.ArrayList;
import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.util.Utils.Pion;
import m2104.ile_interdite.util.Utils.Commandes;
import m2104.ile_interdite.util.Message;

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
        if (tuile == null || !tuile.isInnondee() || tuile.isRetiree()) {
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
    
    public void seFaireDeplacer() {
        ArrayList<Boolean> deplacementsPossibles = new ArrayList<Boolean>();

        ArrayList<Tuile> tuiles = this.getIleInterdite().getGrille().getTuiles(true);
        int indexTuileActuelle = tuiles.indexOf(getPosition());
        int indexTuileCible;
        boolean deplacementPossible = false;
        
        for (Tuile tuile : tuiles) {
            if (tuile == null || tuile.isRetiree() || getPosition() == tuile) {
                deplacementPossible = false;
            } else {
                indexTuileCible = tuiles.indexOf(tuile);
                if (indexTuileActuelle < 30 && indexTuileActuelle + 6 == indexTuileCible) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle < 35 && indexTuileActuelle + 1 == indexTuileCible && (indexTuileActuelle % 6) != 5) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle > 5 && indexTuileActuelle - 6 == indexTuileCible) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle > 0 && indexTuileActuelle - 1 == indexTuileCible && (indexTuileActuelle % 6) != 0) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle < 24 && indexTuileActuelle + 12 == indexTuileCible) {
                    if ((tuiles.get(indexTuileActuelle + 6) != null && !tuiles.get(indexTuileActuelle + 6).isRetiree())
                     || (tuiles.get(indexTuileActuelle + 5) != null && !tuiles.get(indexTuileActuelle + 5).isRetiree())
                     || (tuiles.get(indexTuileActuelle + 7) != null && !tuiles.get(indexTuileActuelle + 7).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle < 34 && indexTuileActuelle + 2 == indexTuileCible && (indexTuileActuelle % 6) < 4) {
                    if ((tuiles.get(indexTuileActuelle + 1) != null && !tuiles.get(indexTuileActuelle + 1).isRetiree())
                     || (tuiles.get(indexTuileActuelle - 5) != null && !tuiles.get(indexTuileActuelle - 5).isRetiree())
                     || (tuiles.get(indexTuileActuelle + 7) != null && !tuiles.get(indexTuileActuelle + 7).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 11 && indexTuileActuelle - 12 == indexTuileCible) {
                    if ((tuiles.get(indexTuileActuelle - 6) != null && !tuiles.get(indexTuileActuelle - 6).isRetiree())
                     || (tuiles.get(indexTuileActuelle - 5) != null && !tuiles.get(indexTuileActuelle - 5).isRetiree())
                     || (tuiles.get(indexTuileActuelle - 7) != null && !tuiles.get(indexTuileActuelle - 7).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 1  && indexTuileActuelle - 2 == indexTuileCible && (indexTuileActuelle % 6) > 1) {
                    if ((tuiles.get(indexTuileActuelle - 1) != null && !tuiles.get(indexTuileActuelle - 1).isRetiree())
                     || (tuiles.get(indexTuileActuelle + 5) != null && !tuiles.get(indexTuileActuelle + 5).isRetiree())
                     || (tuiles.get(indexTuileActuelle - 7) != null && !tuiles.get(indexTuileActuelle - 7).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle < 29 && indexTuileActuelle + 7 == indexTuileCible && (indexTuileActuelle % 6) != 5) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle < 31 && indexTuileActuelle + 5 == indexTuileCible && (indexTuileActuelle % 6) != 0) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle > 4 && indexTuileActuelle - 5 == indexTuileCible && (indexTuileActuelle % 6) != 5) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle > 6 && indexTuileActuelle - 7 == indexTuileCible && (indexTuileActuelle % 6) != 0) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle < 23 && indexTuileActuelle + 13 == indexTuileCible && (indexTuileActuelle % 6) != 5) {
                    if ((tuiles.get(indexTuileActuelle + 6) != null && !tuiles.get(indexTuileActuelle + 6).isRetiree())
                     || (tuiles.get(indexTuileActuelle + 7) != null && !tuiles.get(indexTuileActuelle + 7).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle < 25 && indexTuileActuelle + 11 == indexTuileCible && (indexTuileActuelle % 6) != 0) {
                    if ((tuiles.get(indexTuileActuelle + 6) != null && !tuiles.get(indexTuileActuelle + 6).isRetiree())
                     || (tuiles.get(indexTuileActuelle + 5) != null && !tuiles.get(indexTuileActuelle + 5).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle < 28 && indexTuileActuelle + 8 == indexTuileCible && (indexTuileActuelle % 6) < 4) {
                    if ((tuiles.get(indexTuileActuelle + 1) != null && !tuiles.get(indexTuileActuelle + 1).isRetiree())
                     || (tuiles.get(indexTuileActuelle + 7) != null && !tuiles.get(indexTuileActuelle + 7).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle < 32 && indexTuileActuelle + 4 == indexTuileCible && (indexTuileActuelle % 6) > 1) {
                    if ((tuiles.get(indexTuileActuelle - 1) != null && !tuiles.get(indexTuileActuelle - 1).isRetiree())
                     || (tuiles.get(indexTuileActuelle + 5) != null && !tuiles.get(indexTuileActuelle + 5).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 3 && indexTuileActuelle - 4 == indexTuileCible && (indexTuileActuelle % 6) < 4) {
                    if ((tuiles.get(indexTuileActuelle + 1) != null && !tuiles.get(indexTuileActuelle + 1).isRetiree())
                     || (tuiles.get(indexTuileActuelle - 5) != null && !tuiles.get(indexTuileActuelle - 5).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 7 && indexTuileActuelle - 8 == indexTuileCible && (indexTuileActuelle % 6) > 1) {
                    if ((tuiles.get(indexTuileActuelle - 1) != null && !tuiles.get(indexTuileActuelle - 1).isRetiree())
                     || (tuiles.get(indexTuileActuelle - 7) != null && !tuiles.get(indexTuileActuelle - 7).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 10 && indexTuileActuelle - 11 == indexTuileCible && (indexTuileActuelle % 6) != 5) {
                    if ((tuiles.get(indexTuileActuelle - 6) != null && !tuiles.get(indexTuileActuelle - 6).isRetiree())
                     || (tuiles.get(indexTuileActuelle - 5) != null && !tuiles.get(indexTuileActuelle - 5).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 12 && indexTuileActuelle - 13 == indexTuileCible && (indexTuileActuelle % 6) != 0) {
                    if ((tuiles.get(indexTuileActuelle - 6) != null && !tuiles.get(indexTuileActuelle - 6).isRetiree())
                     || (tuiles.get(indexTuileActuelle - 7) != null && !tuiles.get(indexTuileActuelle - 7).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle < 22 && indexTuileActuelle + 14 == indexTuileCible && (indexTuileActuelle % 6) < 4) {
                    if (tuiles.get(indexTuileActuelle + 7) != null && !tuiles.get(indexTuileActuelle + 7).isRetiree()) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle < 26 && indexTuileActuelle + 10 == indexTuileCible && (indexTuileActuelle % 6) > 1) {
                    if (tuiles.get(indexTuileActuelle + 5) != null && !tuiles.get(indexTuileActuelle + 5).isRetiree()) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 9 && indexTuileActuelle - 10 == indexTuileCible && (indexTuileActuelle % 6) < 4) {
                    if (tuiles.get(indexTuileActuelle - 5) != null && !tuiles.get(indexTuileActuelle - 5).isRetiree()) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 13 && indexTuileActuelle - 14 == indexTuileCible && (indexTuileActuelle % 6) > 1) {
                    if (tuiles.get(indexTuileActuelle - 7) != null && !tuiles.get(indexTuileActuelle - 7).isRetiree()) {
                        deplacementPossible = true;
                    }
                } else {
                    deplacementPossible = false;
                }
            }
            deplacementsPossibles.add(deplacementPossible);
        }
        Message msg = new Message(Commandes.TUILES_POSSIBLES);
        msg.idAventurier = this.getIleInterdite().getAventuriers().indexOf(this);
        msg.possibilites = deplacementsPossibles;
        msg.pion = this.getPion();
        msg.action = 1;
        this.getIleInterdite().notifierObservateurs(msg);
        
    }
    
}
