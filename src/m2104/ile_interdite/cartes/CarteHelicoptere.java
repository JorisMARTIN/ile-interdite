package m2104.ile_interdite.cartes;

import java.util.ArrayList;

import m2104.ile_interdite.aventuriers.Aventurier;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.modele.Deck;
import m2104.ile_interdite.util.Utils.Tresor;

public class CarteHelicoptere extends Carte {

    public CarteHelicoptere(Deck deck) {
        super(deck);
    }

    @Override
    public void action() {

        if (this.getAventurier().getPosition().getAventuriers().size() == this.getDeck().getIleInterdite().getAventuriers().size()) {
            ArrayList<Tresor> tresors = new ArrayList<>();
            for(Aventurier a : this.getDeck().getIleInterdite().getAventuriers() ) {
                for (Tresor t : a.getTresors()) {
                    tresors.add(t);
                }
            }
            this.getDeck().getIleInterdite().gagnee(((tresors.size() == 4) ?  true : false));
        } else {
            this.getDeck().getIleInterdite().gagnee(false);
        }

    }

    public void action(Tuile tuileDepart, Tuile tuileArrivee) {
        for (Aventurier a : tuileDepart.getAventuriers()) {
            a.deplacer(tuileArrivee);
        }
    }

    @Override
	public String toString() {
		return "H";
	}

}