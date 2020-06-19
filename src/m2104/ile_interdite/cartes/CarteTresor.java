package m2104.ile_interdite.cartes;

import m2104.ile_interdite.modele.Deck;
import m2104.ile_interdite.util.Utils;
import m2104.ile_interdite.util.Utils.Tresor;
/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
* 
*/
public class CarteTresor extends Carte {
	
    private Utils.Tresor tresor;
    
    public CarteTresor(Deck deck,Utils.Tresor tresor) {
        super(deck);
        this.tresor = tresor;
    }

	@Override
	public void action() {}
	
	@Override
	public String toString() {

		if(this.tresor == Tresor.CALICE) return "Calice";
		else if(this.tresor == Tresor.CRISTAL) return "Cristal";
		else if(this.tresor == Tresor.PIERRE) return "Pierre";
		else if(this.tresor == Tresor.ZEPHYR) return "Zephyr";
		return "Tresor";
	}
	
	public Utils.Tresor getTresor(){
		return this.tresor;
	}

}