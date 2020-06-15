package m2104.ile_interdite.cartes;

import m2104.ile_interdite.util.Utils.EtatTuile;

public class CarteSacDeSable extends Carte {
	
	
    public CarteSacDeSable() {
        super();
    }

	@Override
	public void action(Tuile tuile) {
		if(tuile.getEtat() == EtatTuile.INONDEE) {
			tuile.setEtat(EtatTuile.ASSECHEE);
		} else {
			tuile.setEtat(EtatTuile.INONDEE);
		}
		
	}
}