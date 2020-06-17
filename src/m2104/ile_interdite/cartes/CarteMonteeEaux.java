package m2104.ile_interdite.cartes;


import m2104.ile_interdite.modele.Deck;

import java.util.ArrayList;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
* 
*/

public class CarteMonteeEaux extends Carte {

    public CarteMonteeEaux(Deck deck) {
        super(deck);
    }

    @Override
    public void action() {
        
        this.getDeck().getIleInterdite().setCurseur(this.getDeck().getIleInterdite().getCurseur() + 1);
        this.getDeck().defausseCarte(this);
        
        Deck inondation = this.getDeck().getIleInterdite().getDeckInnondation();
        ArrayList<Carte> defausse = inondation.getDefausse();
        
        inondation.remplirPioche(defausse);
        defausse.clear();
    }
    
    @Override
    public String toString() {
        return "ME";
    }

}