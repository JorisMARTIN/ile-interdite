package m2104.ile_interdite.cartes;

import java.util.ArrayList;

import m2104.ile_interdite.modele.Deck;
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
     *  <li>Afficher les tuiles pouvant etre asseche</li>
     *  <li>Lancer l'assechement</li>
     * </ol>
     * 
     *  msg.action : 4 = Assechement
     * 
     */
    @Override
    public void action() {      
        Message msg = new Message(Commandes.TUILES_POSSIBLES);
        msg.pion = this.getAventurier().getPion();
        msg.possibilites = new ArrayList<Boolean>();
        
        for (Tuile tuile : this.getDeck().getIleInterdite().getGrille().getTuiles(true)) {
            msg.possibilites.add(tuile != null && tuile.isInnondee());
        }
        
        msg.action = 4;
        msg.idAventurier = this.getDeck().getIleInterdite().getJoueurCourant();
        
        this.getDeck().getIleInterdite().notifierObservateurs(msg);
    }
    
    @Override
    public String toString() {
        return "SacsDeSable";
    }
}
