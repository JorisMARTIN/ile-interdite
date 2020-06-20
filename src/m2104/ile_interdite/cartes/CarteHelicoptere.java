package m2104.ile_interdite.cartes;


import m2104.ile_interdite.aventuriers.Aventurier;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.modele.Deck;
import m2104.ile_interdite.modele.EtatTuile;
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
public class CarteHelicoptere extends Carte {

    public CarteHelicoptere(Deck deck) {
        super(deck);
    }

    /**
     * <h1>Déroulement : </h1>
     * <ol>
     * <li>Tout les aventuriers sont sur la case de l'heliport
     * <ul>
     * <li>Ils ont tout les trésorts, ils ont gagnée et la vueFin est affichée</li>
     * <li>Ils ont pas tout les trésorts, ils ont perdu et la vueFin est affichée</li>
     * </ul>
     * </li>
     * <li>Sinon
     * <ul>
     * <li>Un message demandant à l'utilisateur de choisir sur quel case il veut se déplacer est créé</li>
     * <li>L'observatuer est notifier du message</li>
     * </ul>
     * </li>
     * </ol>
     * 
     * msg.action : 5 = Deplacement par helicoptère
     */
    @Override
    public void action() {
        	
    	Tuile heliport = this.getDeck().getIleInterdite().getGrille().getTuile("Heliport");
    	
    	if(this.getAventurier().getPosition() == heliport && this.getDeck().getIleInterdite().getTresorsEnJeu().size() == 0) {
    		
    		int nbAventurierBienPlacer = 0;
    		
    		for(Aventurier aventurier : this.getDeck().getIleInterdite().getAventuriers()) {
    			if(aventurier.getPosition() == heliport) nbAventurierBienPlacer ++;
    		}
    		
    		if(nbAventurierBienPlacer == 4) {
                Message m = new Message(Commandes.FIN); 
                m.isReussi = true;
                
    			this.getDeck().getIleInterdite().notifierObservateurs(m);
    		}
    		
    	}else{
    		
        	Message msg = new Message(Commandes.CLICK_HELICO);

            for (Tuile t : this.getDeck().getIleInterdite().getGrille().getTuiles(true)){
                if (t != null && t != this.getAventurier().getPosition() && t.getEtat() != EtatTuile.RETIREE) {
                    msg.possibilites.add(true);
                } else {
                    msg.possibilites.add(false);
                }
            }

            msg.pion = this.getAventurier().getPion();
            msg.action = 5;
            
            msg.idAventurier = this.getDeck().getIleInterdite().getAventuriers().indexOf(this.getAventurier());
            
            this.getDeck().getIleInterdite().notifierObservateurs(msg);
    	}
    }

    @Override
	public String toString() {
		return "Helicoptere";
	}

}