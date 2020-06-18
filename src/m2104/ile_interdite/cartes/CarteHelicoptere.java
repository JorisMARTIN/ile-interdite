package m2104.ile_interdite.cartes;

import java.util.ArrayList;

import m2104.ile_interdite.aventuriers.Aventurier;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.modele.Deck;
import m2104.ile_interdite.modele.EtatTuile;
import m2104.ile_interdite.util.Message;
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

    /**
     * <h1>Déroulement : </h1>
     * <ol>
     * <li>Tout les aventuriers sont sur la case de l'heliport
     * <ul>
     * <li>Ils ont tout les trésorts, ils ont gagnée et la vueFin est affichée</li>
     * <li>Ils ont pas tout les trésorts, ils ont perdu et la vueFin est affichée</li>
     * </ul>
     * </li>
     * <li>Sinon
     * <ul>
     * <li>Un message demandant à l'utilisateur de choisir sur quel case il veut se déplacer est créé</li>
     * <li>L'observatuer est notifier du message</li>
     * </ul>
     * </li>
     * </ol>
     * 
     * msg.action : 2 = Deplacement par helicoptère
     */
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

            for (Tuile t : this.getDeck().getIleInterdite().getGrille().getTuiles(true)){
                if (t != null && t != this.getAventurier().getPosition() && t.getEtat() != EtatTuile.RETIREE) {
                    msg.possibilites.add(true);
                } else {
                    msg.possibilites.add(false);
                }
            }

            msg.pion = this.getAventurier().getPion();
            msg.action = 3;

            this.getDeck().getIleInterdite().notifierObservateurs(msg);
        }
    }

    @Override
	public String toString() {
		return "H";
	}

}