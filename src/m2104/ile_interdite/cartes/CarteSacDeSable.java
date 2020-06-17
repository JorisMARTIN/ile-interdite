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
public class CarteSacDeSable extends Carte {

	
    public CarteSacDeSable(Deck deck) {
        super(deck);
    }
    
    /**
     * <h1>Déroulement</h1>
     * <ol>
     * 	<li>Afficher les tuiles pouvant etre asseche</li>
     * 	<li>Lancer l'assechement</li>
     * </ol>
     * 
     * 	msg.action : 0 = Assechement
     * 
     */
	@Override
	public void action() {		
		Message msg = new Message(Commandes.TUILES_POSSIBLES);
		msg.pion = this.getAventurier().getPion();
		msg.possibilites = this.getAventurier().isAssechementPossibles();
		msg.action = 0;
		
		this.getDeck().getIleInterdite().notifierObservateurs(msg);
	}
	
	@Override
	public String toString() {
		return "SS";
	}
}
