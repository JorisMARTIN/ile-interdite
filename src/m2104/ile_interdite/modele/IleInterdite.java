package m2104.ile_interdite.modele;

import java.util.ArrayList;
import java.util.Arrays;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Utils.Commandes;
import patterns.observateur.Observable;
import patterns.observateur.Observateur;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@iut2.univ-grenoble-alpes.fr>
 */
public class IleInterdite extends Observable<Message> {
    private int curseur;
    private Grille grille;
    private ArrayList<Aventurier> aventuriers;
    private Deck deckTresor;
    private Deck deckInnondation;
    
    public IleInterdite(Observateur<Message> observateur) {
        this.addObservateur(observateur);
        this.grille = new Grille();
        this.deckTresor = new Deck(this);
        this.deckInnondation = new Deck(this);
    }

    public String[] inscrireJoueurs(int nbJoueurs) {
        // TODO: à remplacer par une réelle assignation des types d'aventuriers
        String[] nomAventuriers = new String[nbJoueurs];
        Arrays.fill(nomAventuriers, "Aventurier");
        return nomAventuriers;
    }

    public int getCurseur() {
        return curseur;
    }

    public void setCurseur(int curseur) {
        this.curseur = curseur;
    }

    public Grille getGrille() {
        return grille;
    }

    public ArrayList<Aventurier> getAventuriers() {
        return aventuriers;
    }

    public Deck getDeckTresor() {
        return deckTresor;
    }

    public Deck getDeckInnondation() {
        return deckInnondation;
    }

    public void initGrille() {
    	
    	Carte carte;
    	
    	for(int i=0; i<6; i++) {
    		
    		carte = deckInnondation.getPremiereCarte();
    		carte.action();
    		deckInnondation.defausseCarte(carte);
  
    	}
    	
    	Message m = new Message(Commandes.INITIALISER_GRILLE);
    	m.grille = grille;
    	notifierObservateurs(m);
    	
    }
}
