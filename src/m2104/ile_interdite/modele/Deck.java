package m2104.ile_interdite.modele;

import java.util.ArrayList;
import java.util.Collections;

import m2104.ile_interdite.cartes.Carte;

public class Deck {
    private ArrayList<Carte> pioche;
    private ArrayList<Carte> defausse;
    private IleInterdite ileInterdite;

    public Deck(IleInterdite ileInterdite) {
        pioche = new ArrayList<Carte>();
        defausse = new ArrayList<Carte>();
        this.ileInterdite = ileInterdite;
    }

    public void remplirPioche(ArrayList<Carte> cartes) {
        pioche.addAll(melanger(cartes));
    }

    public void defausseCarte(Carte carte) {
        pioche.remove(carte);
        defausse.add(carte);
    }

    public boolean isVide() {
        return pioche.isEmpty();
    }

    public Carte getPremiereCarte() {
        return pioche.get(pioche.size() - 1);
    }
    
    // Melange la collection de carte placer en paramètre
    private ArrayList<Carte> melanger(ArrayList<Carte> cartes){
    	
    	Collections.shuffle(cartes);
    	
    	return cartes;
    }
}