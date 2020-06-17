/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import m2104.ile_interdite.util.Utils.Tresor;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
*/
public class Grille {
    private final ArrayList<Tuile> tuiles;
    
    public Grille() {
        this.tuiles = new ArrayList<>();

        ArrayList<String> nomTuiles = new ArrayList<>(Arrays.asList(
                "LaPortedOr",
                "LaPortedArgent",
                "LeJardinDesHurlements",
                "Observatoire",
                "LeLagonPerdu",
                "LeRocherFantome",
                "LePalaisDeCorail",
                "LeValDuCrepuscule",
                "Heliport",
                "LaPorteDeBronze",
                "LePontDesAbimes",
                "LaForetPourpre",
                "LaPorteDeCuivre",
                "LaCarverneDuBrasier",
                "LeTempleDeLaLune",
                "LaCarverneDesOmbres",
                "LePalaisDesMarees",
                "LesFalaisesDeLOubli",
                "LeTempleDuSoleil",
                "LaPorteDeFer",
                "LaTourDuGuet",
                "LeMaraisBrumeux",
                "LesDunesDeLIllusion",
                "LeJardinDesMurmures"
        ));
        
        for (int i = 0 ; i < 36 ; i++) {
            Tuile t;
            String nomTuile;
            
            //Laisse la tuile à null si elle ne fait pas partie du terrain
            if(Arrays.asList(0, 1, 4, 5, 6, 11, 24, 29, 30, 31, 34, 35).contains(i)) {
                t = null;
            } else {
                Random choix = new Random();
                nomTuile = nomTuiles.get(choix.nextInt(nomTuiles.size()));

                switch (nomTuile) {
                    case "LeJardinDesHurlements" :
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.ZEPHYR);
                        break;
                    case "LeJardinDesMurmures":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.ZEPHYR);
                        break;
                    case "LeTempleDeLaLune":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.PIERRE);
                        break;
                    case "LeTempleDuSoleil" :
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.PIERRE);
                        break;
                    case "LaCarverneDuBrasier":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.CRISTAL);
                        break;
                    case "LaCarverneDesOmbres":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.CRISTAL);
                        break;
                    case "LePalaisDesMarees":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.CALICE);
                        break;
                    case "LePalaisDeCorail":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.CALICE);
                        break;
                    default:
                        t = new Tuile(nomTuile,EtatTuile.NORMAL, null);
                        break;
                }
                nomTuiles.remove(nomTuile);
            }
            tuiles.add(t);
        }
    }
    
    
    /**
     * 
     * @param getNull
     * 	true : en prenant les tuiles == null
     * 	false : sans les tuiles == null
     * @return Collection de tuiles contenant les null ou non
     */
    public ArrayList<Tuile> getTuiles(boolean getNull) {
        if(getNull)
            return this.tuiles;
        else {
            ArrayList<Tuile> tuilesClean = new ArrayList<>();

            for(Tuile t : tuiles) {
                if(t != null) {
                    tuilesClean.add(t);
                }
            }

            return tuilesClean;
        }   
    }
    
    public Tuile getTuile(String nom) {
        for(Tuile tuile : getTuiles(false)) {
            if(tuile.getNom() == nom) {
                return tuile;
            }
        }
        return null;
    }
}
