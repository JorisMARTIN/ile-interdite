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
import m2104.ile_interdite.cartes.CarteHelicoptere;
import m2104.ile_interdite.cartes.CarteInnondation;
import m2104.ile_interdite.cartes.CarteMonteeEaux;
import m2104.ile_interdite.cartes.CarteSacDeSable;
import m2104.ile_interdite.cartes.CarteTresor;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Utils;
import m2104.ile_interdite.util.Utils.Commandes;
import m2104.ile_interdite.util.Utils.Tresor;
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
    private int joueurCourant;
    
    public IleInterdite(Observateur<Message> observateur) {
        this.grille = new Grille();
        
        // Création des decks
        this.deckTresor = new Deck(this);
        this.deckInnondation = new Deck(this);
        
        // Remplissage des decks
        
        ArrayList<Carte> cartesAjoutees = new ArrayList<>();
        
        
        // 5 cartes pour chaque trésor
        Tresor[] tresors = {Tresor.CALICE, Tresor.CRISTAL, Tresor.PIERRE, Tresor.ZEPHYR};
        
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++) {
                cartesAjoutees.add(new CarteTresor(this.deckTresor, tresors[i]));
            }
        }
        
        // 3 cartes montee des eaux / 3 cartes helicoptere
        for(int i=0; i<3; i++) {
            cartesAjoutees.add(new CarteMonteeEaux(this.deckTresor));
            cartesAjoutees.add(new CarteHelicoptere(this.deckTresor));
        }
        
        // 2 cartes sac de sable
        cartesAjoutees.add(new CarteSacDeSable(this.deckTresor));
        cartesAjoutees.add(new CarteSacDeSable(this.deckTresor));
        
        deckTresor.remplirPioche(cartesAjoutees);
        
        cartesAjoutees.clear();
        
        for(int i=0; i<24; i++) {
            cartesAjoutees.add(new CarteInnondation(this.deckInnondation, grille.getTuiles(false).get(i)));
        }
        
        this.deckInnondation.remplirPioche(cartesAjoutees);
        
        this.aventuriers = new ArrayList<>();
        this.addObservateur(observateur);
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
            selections.remove(selection);
            aventuriers.add(aventurier);
            
        }
        
        return nomAventuriers;
    }

    public void initGrille() {
        
        Carte carte;
        
        for(int i=0; i<6; i++) {
            
            carte = deckInnondation.getPremiereCarte();
            carte.action();
            
        }
        
        Message m = new Message(Commandes.INITIALISER_GRILLE);
        m.grille = grille;
        notifierObservateurs(m);
        
    }
    
    public int getCurseur() {
        return curseur;
    }

    public void setCurseur(int curseur) {
        this.curseur = curseur;

        if(this.deckTresor.isVide()) {
            this.deckTresor.remplirPioche(this.deckTresor.getDefausse());
        }

        Message msg = new Message(Commandes.AUGMENTER_CURSEUR);
        msg.valeur = this.getCurseur();
        notifierObservateurs(msg);
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
    
    
    
    
    /**
     *  <h1>Methode pour lancer le premier tour</h1>
     *  1. Donne 2 cartes tresor a chaque joueur
     *  1. Defini le joueur courant <br>
     *  2. Attribut son nombre d'action <br>
     *  3. Notifie l'ihm du premier joueur a jouer <br>
     * 
     */
    public void lancePartie() {
        
        for(Aventurier a : this.aventuriers) {
            a.initCarte();
        }
        
        this.joueurCourant = 0;     
        this.aventuriers.get(joueurCourant).initActionsRestantes();
        
        Message msg = new Message(Utils.Commandes.JOUEUR_SUIVANT);
        msg.idAventurier = joueurCourant;
        msg.actionRestantes = this.aventuriers.get(joueurCourant).getActionsRestantes();
        
        notifierObservateurs(msg);
    }
    
    public int getJoueurCourant() {
        return this.joueurCourant;
    }

    /**
     * 
     * Change de joueurCourant
     */
    public void joueurSuivant() {
        
        this.joueurCourant = ((this.joueurCourant + 1) % this.aventuriers.size());
        this.aventuriers.get(joueurCourant).initActionsRestantes();
        
        Message msg = new Message(Utils.Commandes.JOUEUR_SUIVANT);
        msg.idAventurier = joueurCourant;
        msg.actionRestantes = this.aventuriers.get(joueurCourant).getActionsRestantes();
        
        notifierObservateurs(msg);
        
    }
    
    public Deck getDeckInnondation() {
        return this.deckInnondation;
    }
    
    public void lanceInnondation() {
        int nb = 0;
        if (this.curseur < 3) {
            nb = 1;
        } else if (this.curseur < 6) {
            nb = 2;
        } else if (this.curseur < 8) {
            nb = 3;
        } else {
            nb = 4;
        }
        
        for (int i = 0; i < nb; i++) {
            Carte carte = this.deckInnondation.getPremiereCarte();
            carte.action();
        }

        Message msg = new Message(Utils.Commandes.MAJ_GRILLE);
        msg.grille = grille;
        notifierObservateurs(msg);
    }
    
    public void lanceDeplacement() {
        ArrayList<Boolean> possibilite = aventuriers.get(this.joueurCourant).isDeplacementPossibles();
        Message msg = new Message(Utils.Commandes.TUILES_POSSIBLES);
        msg.possibilites = possibilite;
        msg.pion = this.aventuriers.get(joueurCourant).getPion();
        notifierObservateurs(msg);
    }
    
    public void lanceAssechement() {
        
        ArrayList<Boolean> possibilite = aventuriers.get(this.joueurCourant).isAssechementPossibles();
        Message msg = new Message(Utils.Commandes.TUILES_POSSIBLES);
        msg.possibilites = possibilite;
        notifierObservateurs(msg);
    }
    
    public void lanceRecuperationTresor() {
        boolean b = aventuriers.get(joueurCourant).peutRecupererTresort();
        if (b) {
            aventuriers.get(joueurCourant).recupererTresor();
        }
        Message msg = new Message(Utils.Commandes.MAJ_GRILLE);
        notifierObservateurs(msg);
    }

    public void deplacerAventurier(String nomTuile) {
        Tuile tuile = this.grille.getTuile(nomTuile);
        this.aventuriers.get(joueurCourant).deplacer(tuile);
    }

    
    public void notifyActionRestantes(int actionsRestantes, Aventurier aventurier) {
        
        int idAventurier = this.aventuriers.indexOf(aventurier);
        
        Message msg = new Message(Utils.Commandes.ACTION_RESTANTES);
        msg.idAventurier = idAventurier;
        msg.actionRestantes = actionsRestantes;
        
        notifierObservateurs(msg);
        
    }

    public void lanceFinTour() {
        
        // Le joueur pioche 2 cartes
        this.aventuriers.get(joueurCourant).piocherCartes(2);
        
        // Lance la phase d'innondation
        lanceInnondation();
        
        // Passe au joueur suivant
        joueurSuivant();
    }
    
    public void gagnee(boolean b) {

        Message msg = new Message((b ? Commandes.FIN : Commandes.GAGNEE));
        notifierObservateurs(msg);

    }
}
