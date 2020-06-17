package m2104.ile_interdite.cartes;

import java.util.ArrayList;

import m2104.ile_interdite.aventuriers.Aventurier;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.modele.Deck;
import m2104.ile_interdite.modele.EtatTuile;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Utils;
import m2104.ile_interdite.util.Utils.Commandes;
import m2104.ile_interdite.util.Utils.Tresor;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
* 
*/
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
            this.getDeck().getIleInterdite().gagnee(tresors.size() == 4);
        } else {
            Message msg = new Message(Commandes.ETAPE_JOUE_CARTE);

            ArrayList<Boolean> possibilites = new ArrayList<>();

            for (Tuile t : this.getDeck().getIleInterdite().getGrille().getTuiles(true)){
                if (t != null && t.getEtat() != EtatTuile.RETIREE && t != this.getAventurier().getPosition()) {
                    possibilites.add(true);
                } else {
                    possibilites.add(false);
                }
            }

            msg.pion = this.getAventurier().getPion();
            msg.possibilites = possibilites;
            msg.action = 2;

            this.getDeck().getIleInterdite().notifierObservateurs(msg);
        }
    }

    @Override
	public String toString() {
		return "H";
	}

}