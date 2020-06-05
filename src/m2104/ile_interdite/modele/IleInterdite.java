package m2104.ile_interdite.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import m2104.ile_interdite.aventuriers.Aventurier;
import m2104.ile_interdite.aventuriers.Explorateur;
import m2104.ile_interdite.aventuriers.Ingenieur;
import m2104.ile_interdite.aventuriers.Messager;
import m2104.ile_interdite.aventuriers.Navigateur;
import m2104.ile_interdite.aventuriers.Pilote;
import m2104.ile_interdite.aventuriers.Plongeur;
import m2104.ile_interdite.cartes.Carte;
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
        String[] nomAventuriers = new String[nbJoueurs];
        
        ArrayList<String> selections = new ArrayList<>(Arrays.asList(
            "Explorateur", "Pilote", "Navigateur", "Ingénieur", "Messager", "Plongeur"
        ));

        for(int i = 0; i < nbJoueurs; i++) {
            Random choix = new Random();
            String selection = selections.get(choix.nextInt(selections.size()));

            Aventurier aventurier;

            switch(selection) {
                case "Explorateur":
                    aventurier = new Explorateur(this);
                    break;
                
                case "Pilote":
                    aventurier = new Pilote(this);
                    break;

                case "Navigateur":
                    aventurier = new Navigateur(this);
                    break;

                case "Ingénieur":
                    aventurier = new Ingenieur(this);
                    break;

                case "Messager":
                    aventurier = new Messager(this);
                    break;

                case "Plongeur":
                    aventurier = new Plongeur(this);
                    break;

                default:
                    aventurier = null;
                    break;
            }

            nomAventuriers[i] = selection;

            aventurier.piocherCarte();
            aventurier.piocherCarte();

            selections.remove(selection);
            aventuriers.add(aventurier);
        }

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
