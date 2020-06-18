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
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
*/
public class IleInterdite extends Observable<Message> {
    private int curseur;
    private Grille grille;
    private ArrayList<Aventurier> aventuriers;
    private Deck deckTresor;
    private Deck deckInnondation;
    private int joueurCourant;
    private boolean deplacementDUrgence;
    private ArrayList<Utils.Tresor> tresorsEnJeu;
    
    
    public IleInterdite(Observateur<Message> observateur) {
        this.grille = new Grille();
        
        // Création des decks
        this.deckTresor = new Deck(this);
        this.deckInnondation = new Deck(this);
        
        
        //Initialisation des trésor en jeu
        Tresor[] tresors = {Tresor.CALICE, Tresor.CRISTAL, Tresor.PIERRE, Tresor.ZEPHYR};
        tresorsEnJeu = new ArrayList<Utils.Tresor>(Arrays.asList(tresors));
        
        // Remplissage des decks
        
        ArrayList<Carte> cartesAjoutees = new ArrayList<>();
        
        
        // 5 cartes pour chaque trésor
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
        this.deplacementDUrgence = false;
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
    
    public boolean lanceInnondation() {
        int nb = 0;
        if (this.curseur < 3) {
            nb = 2;
        } else if (this.curseur < 6) {
            nb = 3;
        } else if (this.curseur < 8) {
            nb = 4;
        } else {
            nb = 5;
        }
        
        
        for (int i = 0; i < nb; i++) {
            CarteInnondation carte = (CarteInnondation) this.deckInnondation.getPremiereCarte();
            carte.action();
            
            if (carte.getTuile().getEtat() == EtatTuile.RETIREE) {
                for (Aventurier aventurier : carte.getTuile().getAventuriers()) {
                    ArrayList<Boolean> possibilite = aventurier.isDeplacementPossibles();
                    
                    Message msg = new Message(Utils.Commandes.DEPLACEMENT_DURGENCE);
                    
                    msg.possibilites = possibilite;
                    msg.pion = aventurier.getPion();
                    msg.idAventurier = aventuriers.indexOf(aventurier);
                    
                    notifierObservateurs(msg);
                    deplacementDUrgence = true;
                }
            }
        }
        
        if (!deplacementDUrgence) {
            System.out.println("Fin de tour normale");
            Message msg = new Message(Utils.Commandes.MAJ_GRILLE);
            msg.grille = grille;
            notifierObservateurs(msg);
        } else {
            System.out.println("Déplacement d'urgence !");
        }
        
        return deplacementDUrgence;
    }

    public void lanceSurbriller(int idAventurier, ArrayList<Boolean> possibilites, int action) {
        Message msg = new Message(Utils.Commandes.TUILES_POSSIBLES);
        msg.idAventurier = idAventurier;
        msg.possibilites = possibilites;
        msg.pion = this.aventuriers.get(msg.idAventurier).getPion();
        msg.action = action;
        notifierObservateurs(msg);
    }
    
    public void lanceDeplacement(int idAventurier) {
        lanceSurbriller(idAventurier, aventuriers.get(idAventurier).isDeplacementPossibles(), 0);
    }
    
    public void lanceAssechement() {
        lanceSurbriller(this.joueurCourant, aventuriers.get(this.joueurCourant).isAssechementPossibles(), 1);
    }
    
    public void lanceRecuperationTresor() {
    	
        if (aventuriers.get(joueurCourant).peutRecupererTresor()) {
            aventuriers.get(joueurCourant).recupererTresor();
        }
        
        Message msg = new Message(Utils.Commandes.MAJ_GRILLE);
        notifierObservateurs(msg);
    }

    public void deplacerAventurier(String nomTuile, int idAventurier) {
        Tuile tuile = this.grille.getTuile(nomTuile);
        this.aventuriers.get(idAventurier).deplacer(tuile);
        if (deplacementDUrgence) {
            joueurSuivant();
        } else {
            Message msg = new Message(Utils.Commandes.MAJ_GRILLE);
            notifierObservateurs(msg);
        }
    }

    
    public void notifyActionRestantes(int actionsRestantes, Aventurier aventurier) {
        
        int idAventurier = this.aventuriers.indexOf(aventurier);
        
        Message msg = new Message(Utils.Commandes.ACTION_RESTANTES);
        msg.idAventurier = idAventurier;
        msg.actionRestantes = actionsRestantes;
        
        notifierObservateurs(msg);
        
    }

    public void lanceFinTour() {
        
        Aventurier aventurier = this.aventuriers.get(joueurCourant);
        
        if(!aventurier.isaPioche()) {
            // Le joueur pioche 2 cartes
            aventurier.piocherCartes(2);
            aventurier.setaPioche(true);
        }
        
        if(aventurier.getMain().size() > 5) {
            Message m = new Message(Commandes.DEMANDE_DEFFAUSE);
            m.idAventurier = joueurCourant;
            notifierObservateurs(m);
            
        } else {
            
        
            // Lance la phase d'innondation
            boolean deplacementDUrgence = lanceInnondation();
            if (!deplacementDUrgence) {
                // Passe au joueur suivant
                joueurSuivant();
            }
            
        }

    }
    
    public void gagnee(boolean b) {

        Message msg = new Message((b ? Commandes.FIN : Commandes.GAGNEE));
        notifierObservateurs(msg);

    }

	public ArrayList<Utils.Tresor> getTresorsEnJeu() {
		return tresorsEnJeu;
	}

	public void lanceDonCarte(int idAventurier, int idCarte) {
		
		
		boolean b;
		
		if(this.getAventuriers().get(joueurCourant).peutDonnerCarteTresor(this.getAventuriers().get(idAventurier), idCarte)) {
			this.getAventuriers().get(joueurCourant).donnerCarteTresor(this.getAventuriers().get(idAventurier), idCarte);
			b = true;
		}else {
			b = false;
		}
		
		Message msg = new Message(Commandes.FIN_DON);
		msg.isReussi = b;
		msg.idAventurier = joueurCourant;
		
		notifierObservateurs(msg);
		
	}

}
