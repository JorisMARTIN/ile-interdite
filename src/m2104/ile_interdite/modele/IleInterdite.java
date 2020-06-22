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
* @author MattÃ©o PAPPALARDO
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
        
        // CrÃ©ation des decks
        this.deckTresor = new Deck(this);
        this.deckInnondation = new Deck(this);
        
        
        //Initialisation des trÃ©sor en jeu
        Tresor[] tresors = {Tresor.CALICE, Tresor.CRISTAL, Tresor.PIERRE, Tresor.ZEPHYR};
        tresorsEnJeu = new ArrayList<Utils.Tresor>(Arrays.asList(tresors));
        
        // Remplissage des decks
        
        ArrayList<Carte> cartesAjoutees = new ArrayList<>();
        
        
        // 5 cartes pour chaque trÃ©sor
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
        
        CarteInnondation carte;
        
        for(int i=0; i<6; i++) {
            
            carte = (CarteInnondation) deckInnondation.getPremiereCarte();
            
            int size = this.deckInnondation.getPioche().size() - 2;
            
            while(carte.getTuile() == this.grille.getTuile("Heliport")) {
                carte = (CarteInnondation) this.deckInnondation.getPioche().get(size);
                size --;
            }
            
            carte.action();
            
        }
        
        Message m = new Message(Commandes.INITIALISER_GRILLE);
        m.grille = this.grille;
        m.tresors = this.tresorsEnJeu;
        ;
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

        if (this.curseur < 10) {
            Message msg = new Message(Commandes.AUGMENTER_CURSEUR);
            msg.valeur = this.getCurseur();

            notifierObservateurs(msg);
        } else {
            Message msg = new Message(Commandes.FIN);
            msg.isReussi = false;
            msg.messageFin = "L'île a sombrée dans l'oubli";

            notifierObservateurs(msg);
        }

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
    
    public void lanceInnondation() {
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
            if (this.deckInnondation.getPioche().size() == 0) {
                this.deckInnondation.remplirPioche(this.deckInnondation.getDefausse());
                this.deckInnondation.getDefausse().clear();
            }
            CarteInnondation carte = (CarteInnondation) this.deckInnondation.getPremiereCarte();
            carte.action();
        }
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
        lanceSurbriller(this.joueurCourant, aventuriers.get(this.joueurCourant).isAssechementPossibles(), 3);
    }
    
    public void lanceRecuperationTresor() {
        
        if (aventuriers.get(joueurCourant).peutRecupererTresor()) {
            Tresor tresor = aventuriers.get(joueurCourant).getPosition().getTresor();
            aventuriers.get(joueurCourant).recupererTresor(tresor);
            
            for (Aventurier aventurier : this.aventuriers) {
                for (CarteTresor carte : aventurier.getCartesTresorsEnMain()) {
                    if (carte.getTresor() == tresor) {
                        aventurier.getMain().remove(carte);
                        Message msg = new Message(Utils.Commandes.ACTUALISER_MAIN);
                        msg.main = aventurier.getMain();
                        msg.idAventurier = this.aventuriers.indexOf(aventurier);
                        notifierObservateurs(msg);
                    }
                }
            }
            
            ArrayList<Carte> piocheTemp = new ArrayList<Carte>() ;
            for(Carte c : this.deckTresor.getPioche()) {
                piocheTemp.add(c);
            }
        
            for (Carte carte : piocheTemp) {
                if (carte instanceof CarteTresor) {
                    CarteTresor carteTresor = (CarteTresor) carte;
                    if (carteTresor.getTresor() == tresor) {
                        this.deckTresor.getPioche().remove(carteTresor);
                    }
                }
            }
            
            ArrayList<Carte> defausseTemp = new ArrayList<Carte>() ;
            for(Carte c : this.deckTresor.getDefausse()) {
                defausseTemp.add(c);
            }
        
            for (Carte carte : defausseTemp) {
                if (carte instanceof CarteTresor) {
                    CarteTresor carteTresor = (CarteTresor) carte;
                    if (carteTresor.getTresor() == tresor) {
                        this.deckTresor.getDefausse().remove(carteTresor);
                    }
                }
            }
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
    
    public void deplacerAventuriers(String nomTuile, int idAventurier) {
        
        Tuile destination = this.grille.getTuile(nomTuile);
        
        ArrayList<Aventurier> aventuriersADeplacer = new ArrayList<Aventurier>() ;
        
        for(Aventurier a : this.aventuriers.get(idAventurier).getPosition().getAventuriers()) {
            aventuriersADeplacer.add(a);
        }

        
        for(Aventurier aventurier : aventuriersADeplacer) {
            
            aventurier.getPosition().removeAventurier(aventurier);
            aventurier.setPosition(destination);
            destination.addAventurier(aventurier);
        }
        
        Message msg = new Message(Utils.Commandes.MAJ_GRILLE);
        notifierObservateurs(msg);
        
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
            this.lanceInnondation();
            if (!this.testFinHeliportTresor()) {
                this.testDeplacementDUrgence();
            }
        }

    }
    
    public boolean testFinHeliportTresor() {
        boolean fin = false;
        
        int calice = 0;
        int pierre = 0;
        int cristal = 0;
        int zephyr = 0;
        for (Tuile tuile : this.grille.getTuiles(false)) {
            if (tuile.isRetiree() && tuile.getNom() == "Heliport") {

                Message msg = new Message(Utils.Commandes.FIN);
                msg.isReussi = false;
                msg.messageFin = "L'Heliport a coulé";

                notifierObservateurs(msg);
                fin = true;
            }
            if (tuile.isRetiree() && tuile.getTresor() != null) {
                if (tuile.getTresor() == Tresor.CALICE && this.tresorsEnJeu.contains(Tresor.CALICE)) {
                    calice ++;
                } else if (tuile.getTresor() == Tresor.PIERRE && this.tresorsEnJeu.contains(Tresor.PIERRE)) {
                    pierre++;
                } else if (tuile.getTresor() == Tresor.CRISTAL && this.tresorsEnJeu.contains(Tresor.CRISTAL)) {
                    cristal++;
                } else if (tuile.getTresor() == Tresor.ZEPHYR && this.tresorsEnJeu.contains(Tresor.ZEPHYR)) {
                    zephyr++;
                }
            }
        }
        
        if (calice == 2 || pierre == 2 || cristal == 2 || zephyr == 2) {
            Message msg = new Message(Utils.Commandes.FIN);
            msg.isReussi = false;
            msg.messageFin = "Vous avez perdu un trésor";

            notifierObservateurs(msg);
            fin = true;
        }
        
        return fin;
    }
    
    public void gagnee(boolean b) {

        Message msg = new Message((b ? Commandes.FIN : Commandes.GAGNEE));
        notifierObservateurs(msg);

    }

    public ArrayList<Utils.Tresor> getTresorsEnJeu() {
        return tresorsEnJeu;
    }

    public void lanceDonCarte(int idAventurier, int idCarte) {
        boolean b = getAventuriers().get(joueurCourant).peutDonnerCarteTresor(this.getAventuriers().get(idAventurier), idCarte);
        
        Message msg = new Message(Commandes.FIN_DON);
        msg.isReussi = b;
        msg.idAventurier = joueurCourant;
        
        notifierObservateurs(msg);
        
        if(b) {
            this.getAventuriers().get(joueurCourant).donnerCarteTresor(this.getAventuriers().get(idAventurier), idCarte);
        }
        
        
        Message msg2 = new Message(Commandes.ACTUALISER_MAIN);
        msg2.idAventurier = joueurCourant;
        msg2.main = aventuriers.get(joueurCourant).getMain();
        
        this.notifierObservateurs(msg2);
    }
    
    public void testDeplacementDUrgence() {
        int indexTuile = 0;
        boolean arretDuTest = false;
        while (!arretDuTest && indexTuile < 24) {
            Tuile tuile = this.grille.getTuiles(false).get(indexTuile);
            if (tuile.isRetiree()) {
                if (tuile.getAventuriers().size() > 0) {
                    Aventurier aventurier = tuile.getAventuriers().get(0);
                    ArrayList<Boolean> possibilite = aventurier.isDeplacementPossibles();
                    if (possibilite.contains(true)) {
                        Message msg = new Message(Utils.Commandes.DEPLACEMENT_DURGENCE);
                        deplacementDUrgence = true;
                        arretDuTest = true;
                        
                        msg.possibilites = possibilite;
                        msg.pion = aventurier.getPion();
                        msg.idAventurier = aventuriers.indexOf(aventurier);
                        
                        notifierObservateurs(msg);
                    } else {
                        Message msg = new Message(Utils.Commandes.FIN);
                        arretDuTest = true;
                        msg.isReussi = false;
                        msg.messageFin = "Vous avez coulé";
        
                        notifierObservateurs(msg);
                    }
                }
            }
            indexTuile ++;
        }
        if (!arretDuTest) {
            this.joueurSuivant();
        }
    }

}
