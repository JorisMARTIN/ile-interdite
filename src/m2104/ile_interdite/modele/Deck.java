package m2104.ile_interdite.modele;

import java.util.ArrayList;

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

    }

    public void defausseCarte(Carte carte) {

    }

    public boolean isVide() {
        return true;
    }

    public Carte getPremiereCarte() {
        return pioche.get(pioche.size() - 1);
    }
}