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
                System.out.println(indexTuileActuelle + "-" + indexTuileCible);
                
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
            System.out.println(tetesChercheuses);
            ArrayList<Tuile> nextTetesChercheuses = new ArrayList<Tuile>();
            for (Tuile tete : tetesChercheuses) {
                if (tete.getEtat() != EtatTuile.RETIREE) {
                    assechementsPossibles.set(tuiles.indexOf(tete), true);
                }
                if (tete.getEtat() != EtatTuile.NORMAL) {
                    int indexTete = tuiles.indexOf(tete);
                    int indexNextTete = indexTete + 1;
                    Tuile nextTete;
                    if (indexNextTete < 35) {
                        nextTete = tuiles.get(indexNextTete);
                        if (nextTete != null && !tuilesDejaVerifies.contains(nextTete) && indexNextTete != indexTuileActuelle && (indexTete % 6) != 5) {
                            System.out.println("--");
                            nextTetesChercheuses.add(nextTete);
                            tuilesDejaVerifies.add(nextTete);
                            System.out.println("--");
                        }
                        indexNextTete = indexTete + 6;
                        if (indexNextTete < 30) {
                            nextTete = tuiles.get(indexNextTete);
                            if (nextTete != null && !tuilesDejaVerifies.contains(nextTete) && indexNextTete != indexTuileActuelle) {
                                System.out.println("-");
                                nextTetesChercheuses.add(nextTete);
                                tuilesDejaVerifies.add(nextTete);
                                System.out.println("-");
                            }
                        }
                    }
                    indexNextTete = indexTete - 1;
                    if (indexNextTete > 0) {
                        nextTete = tuiles.get(indexNextTete);
                        if (nextTete != null && !tuilesDejaVerifies.contains(nextTete) && indexNextTete != indexTuileActuelle && (indexTete % 6) != 0) {
                            System.out.println("----");
                            nextTetesChercheuses.add(nextTete);
                            tuilesDejaVerifies.add(nextTete);
                            System.out.println("----");
                        }
                        indexNextTete = indexTete - 6;
                        if (indexNextTete > 5) {
                            nextTete = tuiles.get(indexNextTete);
                            if (nextTete != null && !tuilesDejaVerifies.contains(nextTete) && (indexNextTete - 6) != indexTuileActuelle) {
                                System.out.println("---");
                                nextTetesChercheuses.add(nextTete);
                                tuilesDejaVerifies.add(nextTete);
                                System.out.println("---");
                            }
                        }
                    }
                }
            }
            System.out.println(nextTetesChercheuses);
            tetesChercheuses = nextTetesChercheuses;
            System.out.println(tetesChercheuses);
        }
        
        System.out.println("Fin de boucle");
        return assechementsPossibles;
    }
}
