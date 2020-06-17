package m2104.ile_interdite.cartes;

import m2104.ile_interdite.modele.Deck;
import m2104.ile_interdite.modele.EtatTuile;
import m2104.ile_interdite.modele.Tuile;

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

	@Override
	public void action() {
	}
	
	@Override
	public String toString() {
		return "SS";
	}
}
