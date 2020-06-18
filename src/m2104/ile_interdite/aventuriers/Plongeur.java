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
                    if (tuiles.indexOf(tete) < 35 && tuiles.get(tuiles.indexOf(tete) + 1) != null) {
                        if (!tuilesDejaVerifies.contains(tuiles.get(tuiles.indexOf(tete) + 1)) && (tuiles.indexOf(tete) + 1) != indexTuileActuelle && (tuiles.indexOf(tete) % 6) % 6 != 5) {
                            System.out.println("--");
                            nextTetesChercheuses.add(tuiles.get(tuiles.indexOf(tete) + 1));
                            tuilesDejaVerifies.add(tuiles.get(tuiles.indexOf(tete) + 1));
                            System.out.println("--");
                        }
                        if (tuiles.indexOf(tete) < 30 && tuiles.get(tuiles.indexOf(tete) + 6) != null) {
                            if (!tuilesDejaVerifies.contains(tuiles.get(tuiles.indexOf(tete) + 6)) && (tuiles.indexOf(tete) + 6) != indexTuileActuelle) {
                                System.out.println("-");
                                nextTetesChercheuses.add(tuiles.get(tuiles.indexOf(tete) + 6));
                                tuilesDejaVerifies.add(tuiles.get(tuiles.indexOf(tete) + 6));
                                System.out.println("-");
                            }
                        }
                    }
                    if (tuiles.indexOf(tete) > 0 && tuiles.get(tuiles.indexOf(tete) - 1) != null) {
                        if (!tuilesDejaVerifies.contains(tuiles.get(tuiles.indexOf(tete) - 1)) && (tuiles.indexOf(tete) - 1) != indexTuileActuelle && (tuiles.indexOf(tete) % 6) != 0) {
                            System.out.println("----");
                            nextTetesChercheuses.add(tuiles.get(tuiles.indexOf(tete) - 1));
                            tuilesDejaVerifies.add(tuiles.get(tuiles.indexOf(tete) - 1));
                            System.out.println("----");
                        }
                        if (tuiles.indexOf(tete) > 5 && tuiles.get(tuiles.indexOf(tete) - 6) != null) {
                            if (!tuilesDejaVerifies.contains(tuiles.get(tuiles.indexOf(tete) - 6)) && (tuiles.indexOf(tete) - 6) != indexTuileActuelle) {
                                System.out.println("---");
                                nextTetesChercheuses.add(tuiles.get(tuiles.indexOf(tete) - 6));
                                tuilesDejaVerifies.add(tuiles.get(tuiles.indexOf(tete) - 6));
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
