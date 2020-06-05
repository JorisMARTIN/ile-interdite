/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.modele;

import java.util.ArrayList;

import m2104.ile_interdite.util.Utils.Tresor;

/**
 *
 * @author tang
 */
public class Grille {
    /*attribus*/
    private final ArrayList<Tuile> tuiles;
    private final String[] nomTuile = {"La Porte d'Or","La Port d'Argent", "Le Jardin des Hurlements", "L'Observatoire",
            "Le Lagon Perdu", "Le Rocher Fantôme", "Le Palais de Corail", "Le Val du Crépuscule", "L'Héliport",
            "La Porte de Bronze", "Le Pont des Abîmes", "La Forêt Pourpre", "La Porte de Cuivre", "La Caverne du Brasier",
            "Le Temple de la Lune", "La Caverne des Ombres", "Le Palais des Marais", "Les Falaises de l'Oubli",
            "Le Temple du Soleil", "La Porte de Fer", "La Tour de Guet", "Le Marais Brumeux", "Les Dunes de l'Illusion",
            "Le Jardin des Murmures"} ;
    
    /*construteur*/
    public Grille() {
        this.tuiles = new ArrayList<>();
        
        Tuile t;
        for (int i = 0 ; i < 24 ; i++) {
            switch (nomTuile[i]){
                case "Le Jardin des Hurlements" :
                    t = new Tuile(nomTuile[i],EtatTuile.NORMAL, Tresor.ZEPHYR);
                    break;
                case "Le Jardin des Murmures":
                    t = new Tuile(nomTuile[i],EtatTuile.NORMAL, Tresor.ZEPHYR);
                    break;
                case "Le Temple de la Lune":
                    t = new Tuile(nomTuile[i],EtatTuile.NORMAL, Tresor.PIERRE);
                    break;
                case "Le Temple du Soleil" :
                    t = new Tuile(nomTuile[i],EtatTuile.NORMAL, Tresor.PIERRE);
                    break;
                case "La Caverne du Brasier":
                    t = new Tuile(nomTuile[i],EtatTuile.NORMAL, Tresor.CRISTAL);
                    break;
                case "La Caverne des Ombres":
                    t = new Tuile(nomTuile[i],EtatTuile.NORMAL, Tresor.CRISTAL);
                    break;
                case "Le Palais des Marais":
                    t = new Tuile(nomTuile[i],EtatTuile.NORMAL, Tresor.CALICE);
                    break;
                case "Le Palais de Corail":
                    t = new Tuile(nomTuile[i],EtatTuile.NORMAL, Tresor.CALICE);
                    break;
                default:
                    t = new Tuile(nomTuile[i],EtatTuile.NORMAL, null);
                    break;
            }
            tuiles.add(t);
        }
    }
    
    /*méthodes*/
    public ArrayList<Tuile> getTuiles() {
        return this.tuiles;
    }
    
    public Tuile getTuile(final String nom) {
        return null;
    }
}
