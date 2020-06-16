package m2104.ile_interdite.cartes;

import m2104.ile_interdite.modele.Deck;
import m2104.ile_interdite.modele.EtatTuile;
import m2104.ile_interdite.modele.Tuile;

public class CarteInnondation extends Carte {
	
    private Tuile tuile;

    public CarteInnondation(Deck deck, Tuile tuile) {
        super(deck);
        this.tuile = tuile;
    }

	@Override
	public void action() {
		if(this.tuile.getEtat() == EtatTuile.INONDEE) {
			this.tuile.setEtat(EtatTuile.RETIREE);
		}else {
			this.tuile.setEtat(EtatTuile.INONDEE);
		}
	}
	
	@Override
	public String toString() {
		return "I";
	}

    
}