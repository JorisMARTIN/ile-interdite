package m2104.ile_interdite.vue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Utils;
import patterns.observateur.Observable;
import patterns.observateur.Observateur;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@iut2.univ-grenoble-alpes.fr>
 */
public class IHM extends Observable<Message> {

    private final VueInscriptionJoueurs vueInscription;
    private final HashMap<Integer, VueAventurier> vueAventuriers;
    private VueJeu vueJeu;

    public IHM(Observateur<Message> observateur) {
        this.vueInscription = new VueInscriptionJoueurs(this);
        this.addObservateur(observateur);
        this.vueAventuriers = new HashMap<>();
    }

    public void creerVuesAventuriers(String[] nomAventuriers) {
        // - le pouvoir est disponible dans le modèle
        String[] nomsJoueurs = this.vueInscription.getNomJoueurs();
        assert nomsJoueurs.length == nomAventuriers.length;
        for (int id = 0; id < nomAventuriers.length; ++id) {
            this.vueAventuriers.put(
                    id,
                    new VueAventurier(
                            this,
                            id,
                            nomsJoueurs[id],
                            nomAventuriers[id],
                            "YYY",  // TODO: à remplacer par le bon pouvoir
                            id,
                            nomAventuriers.length,
                            Color.orange,
                            Color.gray
                    )
            );
        }
    }
    
    public void creeVueJeu(Grille grille) {
        vueJeu = new VueJeu(this, grille);
        Message msg = new Message(Utils.Commandes.LANCER_JEU);
        
        notifierObservateurs(msg);
    }

    public void surbrillerTuiles(ArrayList<Boolean> possibilites) {
        //TODO
    }

    public void placerCurseur(int valeur) {
        //TODO
    }

    public void majVueJeu(Grille grille) {
        //TODO
    }

	public void changerJoueurCourant(Integer idAventurier) {

		for(VueAventurier vue : this.vueAventuriers.values()) {
			vue.desactiver();
			vue.activerBoutons(false, false, false, false, false, false, false);
		}
		
		this.vueAventuriers.get(idAventurier).activer();
		this.vueAventuriers.get(idAventurier).activerBoutons(true, true, true, true, true, true, true);
	}
    
}
