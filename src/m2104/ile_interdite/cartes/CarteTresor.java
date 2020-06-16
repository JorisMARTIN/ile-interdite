package m2104.ile_interdite.cartes;

import m2104.ile_interdite.modele.Deck;
import m2104.ile_interdite.util.Utils;

public class CarteTresor extends Carte {
	
    private Utils.Tresor tresor;
    
    public CarteTresor(Deck deck,Utils.Tresor tresor) {
        super(deck);
        this.tresor = tresor;
    }

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "T";
	}

}