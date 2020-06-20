/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.aventuriers;

import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.modele.EtatTuile;
import m2104.ile_interdite.util.Utils.Pion;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Utils.Commandes;
import java.util.ArrayList;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
* 
*/
public class Plongeur extends Aventurier{
    /*construteur*/
    public Plongeur(IleInterdite ileInterdite) {
        super(ileInterdite, Pion.VIOLET);
    }
    
    /*méthodes*/
    @Override
    public ArrayList<Boolean> isDeplacementPossibles() {
        
        ArrayList<Boolean> assechementsPossibles = new ArrayList<Boolean>();
        ArrayList<Tuile> tetesChercheuses = new ArrayList<Tuile>();
        ArrayList<Tuile> tuiles = this.getIleInterdite().getGrille().getTuiles(true);
        ArrayList<Tuile> tuilesDejaVerifies = new ArrayList<Tuile>();
        int indexTuileActuelle = tuiles.indexOf(getPosition());
        for (Tuile tuile : tuiles) {
            if (tuile != null) {
                
                int indexTuileCible = tuiles.indexOf(tuile);
                
                if ((indexTuileActuelle < 29 && indexTuileActuelle + 6 == indexTuileCible)
                || (indexTuileActuelle < 35 && indexTuileActuelle + 1 == indexTuileCible && (indexTuileActuelle) % 6 != 5)
                || (indexTuileActuelle > 5 && indexTuileActuelle - 6 == indexTuileCible)
                || (indexTuileActuelle > 0 && indexTuileActuelle - 1 == indexTuileCible && (indexTuileActuelle) % 6 != 0)) {
                    tetesChercheuses.add(tuile);
                    tuilesDejaVerifies.add(tuile);
                }
            }
            assechementsPossibles.add(false);
        }
        
        while (tetesChercheuses.size() > 0) {
            ArrayList<Tuile> nextTetesChercheuses = new ArrayList<Tuile>();
            for (Tuile tete : tetesChercheuses) {
                if (tete.getEtat() != EtatTuile.RETIREE) {
                    assechementsPossibles.set(tuiles.indexOf(tete), true);
                }
                if (tete.getEtat() != EtatTuile.NORMAL) {
                    int indexTete = tuiles.indexOf(tete);
                    int indexNextTete = indexTete + 1;
                    Tuile nextTete;
                    if (indexTete < 35) {
                        nextTete = tuiles.get(indexNextTete);
                        if (nextTete != null && !tuilesDejaVerifies.contains(nextTete) && indexNextTete != indexTuileActuelle && (indexTete % 6) != 5) {
                            nextTetesChercheuses.add(nextTete);
                            tuilesDejaVerifies.add(nextTete);
                        }
                        indexNextTete = indexTete + 6;
                        if (indexTete < 30) {
                            nextTete = tuiles.get(indexNextTete);
                            if (nextTete != null && !tuilesDejaVerifies.contains(nextTete) && indexNextTete != indexTuileActuelle) {
                                nextTetesChercheuses.add(nextTete);
                                tuilesDejaVerifies.add(nextTete);
                            }
                        }
                    }
                    indexNextTete = indexTete - 1;
                    if (indexTete > 0) {
                        nextTete = tuiles.get(indexNextTete);
                        if (nextTete != null && !tuilesDejaVerifies.contains(nextTete) && indexNextTete != indexTuileActuelle && (indexTete % 6) != 0) {
                            nextTetesChercheuses.add(nextTete);
                            tuilesDejaVerifies.add(nextTete);
                        }
                        indexNextTete = indexTete - 6;
                        if (indexTete > 5) {
                            nextTete = tuiles.get(indexNextTete);
                            if (nextTete != null && !tuilesDejaVerifies.contains(nextTete) && indexNextTete != indexTuileActuelle) {
                                nextTetesChercheuses.add(nextTete);
                                tuilesDejaVerifies.add(nextTete);
                            }
                        }
                    }
                }
            }
            tetesChercheuses = nextTetesChercheuses;
        }
        
        return assechementsPossibles;
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
                    deplacementPossible = true;
                } else if (indexTuileActuelle < 34 && indexTuileActuelle + 2 == indexTuileCible && (indexTuileActuelle % 6) < 4) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle > 11 && indexTuileActuelle - 12 == indexTuileCible) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle > 1  && indexTuileActuelle - 2 == indexTuileCible && (indexTuileActuelle % 6) > 1) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle < 29 && indexTuileActuelle + 7 == indexTuileCible) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle < 31 && indexTuileActuelle + 5 == indexTuileCible) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle > 4 && indexTuileActuelle - 5 == indexTuileCible) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle > 6 && indexTuileActuelle - 7 == indexTuileCible) {
                    deplacementPossible = true;
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
