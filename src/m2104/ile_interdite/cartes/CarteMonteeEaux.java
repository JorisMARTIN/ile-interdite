package m2104.ile_interdite.cartes;

import m2104.ile_interdite.modele.Deck;
import m2104.ile_interdite.modele.IleInterdite;

public class CarteMonteeEaux extends Carte {

    public CarteMonteeEaux(Deck deck) {
        super(deck);
    }

	@Override
	public void action() {
		this.getDeck().getIleInterdite().setCurseur(this.getDeck().getIleInterdite().getCurseur() + 1);
		this.getDeck().defausseCarte(this);
	}

}