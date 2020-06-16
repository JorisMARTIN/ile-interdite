package m2104.ile_interdite.vue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import m2104.ile_interdite.cartes.Carte;
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
    private VueNiveau vueNiveau;

    public IHM(Observateur<Message> observateur) {
        this.vueInscription = new VueInscriptionJoueurs(this);
        this.addObservateur(observateur);
        this.vueAventuriers = new HashMap<>();
    }

    public void creerVuesAventuriers(String[] nomAventuriers) {
        // - le pouvoir est disponible dans le modèle
        String[] nomsJoueurs = this.vueInscription.getNomJoueurs();
        assert nomsJoueurs.length == nomAventuriers.length;
        
        Color active;
        
        for (int id = 0; id < nomAventuriers.length; ++id) {
            
        	switch (nomAventuriers[id]) {
        	
			case "Explorateur":
					active = Utils.Pion.VERT.getCouleur();
				break;
				
			case "Pilote" :
					active = Utils.Pion.BLEU.getCouleur();
				break;
				
			case "Navigateur" :
					active = Utils.Pion.JAUNE.getCouleur();
				break;
				
			case "Ingénieur" :
					active = Utils.Pion.ROUGE.getCouleur();
				break;
			
			case "Messager" :
					active = Utils.Pion.ORANGE.getCouleur();
				break;
				
			case "Plongeur" :
					active = Utils.Pion.VIOLET.getCouleur();
				break;
				
			default:
				active = null;
				break;
			}
        	
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
                            active,
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

    public void creeVueNiveau(Integer difficulte) {
        this.vueNiveau = new VueNiveau(difficulte);
    }
    
    public void surbrillerTuiles(ArrayList<Boolean> possibilites, Utils.Pion pion) {
        this.vueJeu.surbrillerTuiles(possibilites, pion);
    }

    public void placerCurseur(int valeur) {
        this.vueNiveau.setNiveau(valeur);
    }

    public void majVueJeu() {
        //TODO
    }

    public void changerJoueurCourant(Integer idAventurier) {

        for(VueAventurier vue : this.vueAventuriers.values()) {
        	vue.resetActionRestantes();
            vue.desactiver();
            vue.activerBoutons(false, false, false, false, false, false, false);
        }
        
        this.vueAventuriers.get(idAventurier).activer();
        this.vueAventuriers.get(idAventurier).activerBoutons(true, true, true, true, true, true, true);
    }

	public void setActionRestantes(Integer idAventurier, Integer actionRestantes) {
		
		VueAventurier vue = this.vueAventuriers.get(idAventurier);
		
		vue.setActionRestantes(actionRestantes);
		
	}

	public void actualiserMainJoueur(ArrayList<Carte> main, boolean tropCarte, Integer idAventurier) {
		
		this.vueAventuriers.get(idAventurier).actualiserMain(main, tropCarte);
		
	}

	public void finPasGagne() {
	}

	public void finGagne() {
	}

    
}
