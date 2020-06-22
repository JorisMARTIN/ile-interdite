package m2104.ile_interdite.vue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import m2104.ile_interdite.cartes.Carte;
import m2104.ile_interdite.controleur.Controleur;
import m2104.ile_interdite.modele.Grille;
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
public class IHM extends Observable<Message> {

    private final VueInscriptionJoueurs vueInscription;
    private final HashMap<Integer, VueAventurier> vueAventuriers;
    private VueJeu vueJeu;
    private VueNiveau vueNiveau;
    private VueFin vueFin;
    
    private int idCarteADonner; 

    public IHM(Observateur<Message> observateur) {
        this.vueInscription = new VueInscriptionJoueurs(this);
        this.addObservateur(observateur);
        this.vueAventuriers = new HashMap<>();
        this.vueFin = new VueFin(this);
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
                            id,
                            nomAventuriers.length,
                            active,
                            Color.gray
                    )
            );
        }
    }
    
    public void creeVueJeu(Grille grille, ArrayList<Tresor> tresors) {
        
        vueJeu = new VueJeu(this, grille, tresors);
        Message msg = new Message(Utils.Commandes.LANCER_JEU);
        
        notifierObservateurs(msg);
        
    }

    public void creeVueNiveau(Integer difficulte) {
        this.vueNiveau = new VueNiveau(difficulte);
    }
    
    /**
     * 
     * @param possibilites : Liste de boolean selon les possibilitées.
     * @param pion         : Le pions voulant effectuer l'action
     * @param action       : 0 = Assechement, 1 = Deplacement, 2 = Deplacement par
     *                     helicoptère
     * @param idAventurier : Le numero de l'aventurier demandant l'action
     */
    public void surbrillerTuiles(ArrayList<Boolean> possibilites, Utils.Pion pion, int action, int idAventurier) {
        this.vueJeu.surbrillerTuiles(possibilites, pion, action, idAventurier);
    }

    public void lanceChoisirBougerJoueur(int idAventurier) {
        this.vueAventuriers.forEach((i, va) -> {
            if(i != idAventurier)
                va.activerBoutons(false, false, false, false, false, true, false);
        });
    }
    
    public void activerActionsTous(boolean activerMove, boolean activerDry, boolean activerDonner, boolean activerRecuperer, boolean activerRecevoir, boolean activerDeplacer, boolean activerTerminer) {
        this.vueAventuriers.forEach((i, va) -> {
            va.activerBoutons(activerMove, activerDry, activerDonner, activerRecuperer, activerRecevoir, activerDeplacer, activerTerminer);
        });
    }

    public void placerCurseur(int valeur) {
        this.vueNiveau.setNiveau(valeur);
    }

    public void majVueJeu() {
        this.vueJeu.affGrille();
        this.vueJeu.resetSelections();
        this.vueJeu.refresh();
    }

    public void changerJoueurCourant(int idAventurier) {
        for (VueAventurier vue : this.vueAventuriers.values()) {
            
            vue.resetActionRestantes();
            vue.desactiver();
            vue.activerBoutons(false, false, false, false, false, false, false);
            
            if(vue.getMainJoueur().size() > 5) {
                vue.setEtatBoutonsCartes(false);
            }
        }
        
        VueAventurier va = this.vueAventuriers.get(idAventurier);

        va.activer();
        
        if(va.getMainJoueur().size() > 5) {
            va.setEtatBoutonsCartes(true);
        } else {
            activerActions(idAventurier, true, true, true, true, true, va.getNomAventurier() == "Navigateur", true);
        }
    }
    
    public void activerActions(int idAventurier, boolean bouger, boolean assecher, boolean donner, boolean recuperer, boolean recevoir, boolean deplacer, boolean terminer) {
        VueAventurier va = this.vueAventuriers.get(idAventurier);
        va.activerBoutons(bouger, assecher, donner, recuperer, recevoir, deplacer, terminer);
    }

    public void setActionRestantes(Integer idAventurier, Integer actionRestantes) {
        
        VueAventurier vue = this.vueAventuriers.get(idAventurier);
        
        vue.setActionRestantes(actionRestantes);
    }

    public void actualiserMainJoueur(ArrayList<Carte> main, Integer idAventurier) {
        this.vueAventuriers.get(idAventurier).actualiserMain(main);
    }

    public void demandeDefausse(int idAventurier) {
        this.vueAventuriers.get(idAventurier).deffausseCarte();
    }
    
    public void finGagne(boolean isGagnee, String message) {
        this.vueFin.activer(isGagnee, message);
    }
    
    public void demandeDonCarte(int idAventurier) {
        this.vueAventuriers.get(idAventurier).donnerCarte();
    }

    public HashMap<Integer, VueAventurier> getVueAventuriers() {
        return vueAventuriers;
    }
    
    public void setIdCarteADonner(int idCarteADonner) {
        this.idCarteADonner = idCarteADonner;
    }

    public void donneCarte(int idReceveur) {
        
        Message m = new Message(Commandes.DONNER);
        m.idCarte = idCarteADonner;
        m.idAventurier = idReceveur;
        
        this.idCarteADonner = -1;
        
        notifierObservateurs(m);
    }

    public void setVueInscription() {
        new Controleur();
    }

    public VueJeu getVueJeu() {
        return this.vueJeu;
    }

    public VueNiveau getVueNiveau() {
        return this.vueNiveau;
    }
}
