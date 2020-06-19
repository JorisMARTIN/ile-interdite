package m2104.ile_interdite.cartes;

import m2104.ile_interdite.modele.Deck;
import m2104.ile_interdite.modele.EtatTuile;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Utils.Commandes;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
* 
*/

public class CarteInnondation extends Carte {
    
    private Tuile tuile;

    public CarteInnondation(Deck deck, Tuile tuile) {
        super(deck);
        this.tuile = tuile;
    }

    @Override
    public void action() {
        if(this.tuile.getEtat() == EtatTuile.NORMAL) {
            this.tuile.setEtat(EtatTuile.INONDEE);
            this.getDeck().defausseCarte(this);
        } else if (this.tuile.getEtat() == EtatTuile.INONDEE && this.tuile.getNom() != "Helicoptere") {
            this.tuile.setEtat(EtatTuile.RETIREE);
            this.getDeck().getPioche().remove(this);
        } else if (this.tuile.getEtat() == EtatTuile.INONDEE && this.tuile.getNom() != "Helicoptere") {
            Message msg = new Message(Commandes.FIN);
            msg.isReussi = false;

            this.getDeck().getIleInterdite().notifierObservateurs(msg);
        }
    }
    
    @Override
    public String toString() {
        return "I";
    }

    public Tuile getTuile() {
        return this.tuile;
    }
}