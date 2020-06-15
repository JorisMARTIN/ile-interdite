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
 * @author tang
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
                    case "Le Jardin des Hurlements" :
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.ZEPHYR);
                        break;
                    case "Le Jardin des Murmures":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.ZEPHYR);
                        break;
                    case "Le Temple de la Lune":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.PIERRE);
                        break;
                    case "Le Temple du Soleil" :
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.PIERRE);
                        break;
                    case "La Caverne du Brasier":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.CRISTAL);
                        break;
                    case "La Caverne des Ombres":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.CRISTAL);
                        break;
                    case "Le Palais des Marais":
                        t = new Tuile(nomTuile, EtatTuile.NORMAL, Tresor.CALICE);
                        break;
                    case "Le Palais de Corail":
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
        for(Tuile tuile : tuiles) {
            if(tuile.getNom() == nom) {
                return tuile;
            }
        }
        return null;
    }
}
