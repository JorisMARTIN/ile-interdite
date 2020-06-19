
package m2104.ile_interdite.controleur;

import java.util.ArrayList;

import m2104.ile_interdite.aventuriers.Aventurier;
import m2104.ile_interdite.modele.EtatTuile;
import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Parameters;
import m2104.ile_interdite.vue.IHM;
import patterns.observateur.Observateur;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@iut2.univ-grenoble-alpes.fr>
 * @author Thomas JHISTARRY
 * @author Joris MARTIN
 * @author Tanguy SIGNORET
 * @author Mattéo PAPPALARDO
 */
public class Controleur implements Observateur<Message> {

    private final IleInterdite ileInterdite;
    private final IHM ihm;
    

    public Controleur() {
        this.ileInterdite = new IleInterdite(this);
        this.ihm = new IHM(this);
    }

    @Override
    public void traiterMessage(Message msg) {
        if (Parameters.LOGS) {
            System.out.println("Controleur.traiterMessage : " + msg.commande);
        }
        
        switch (msg.commande) {
        
            case INITIALISER:
                String[] nomAventuriers = this.ileInterdite.inscrireJoueurs(msg.nbJoueurs);
                this.ihm.creeVueNiveau(msg.difficulte);
                this.ileInterdite.setCurseur(msg.difficulte);
                this.ihm.creerVuesAventuriers(nomAventuriers);
                this.ileInterdite.initGrille();
                break;
                
            case INITIALISER_GRILLE:
                this.ihm.creeVueJeu(msg.grille, msg.tresors);
                break;
                
            case LANCER_DEPLACEMENT:
                this.ileInterdite.lanceDeplacement(msg.idAventurier);
                break;
                
            case LANCER_ASSECHEMENT:
                this.ileInterdite.lanceAssechement();
                break;
            
            case DONNER:
                this.ileInterdite.lanceDonCarte(msg.idAventurier, msg.idCarte);
                break;
            
            case RECUPERER_TRESOR:
                this.ileInterdite.lanceRecuperationTresor();
                break;
            
            case TERMINER:
                this.ileInterdite.lanceFinTour();
                break;
            
            case RECEVOIR:
                this.ihm.donneCarte(msg.idAventurier);
                break;
            
            case CHOISIR_CARTE:
                break;
            
            case CHOISIR_TUILE:
                break;
            
            case ZERO_ACTIONS:
                ihm.getVueAventuriers().forEach((i, va) -> {
                    if (msg.idAventurier == i)
                        va.activerBoutons(false, false, false, false, false, false, true);
                    else
                        va.activerBoutons(false, false, false, false, false, false, false);
                });
                break;

            /*Ajout des nouveaux cas pour les nouveaux messages*/
            case MAJ_GRILLE:
                this.ihm.majVueJeu();
                break;

            case TUILES_POSSIBLES:
                this.ihm.surbrillerTuiles(msg.possibilites, msg.pion, msg.action, msg.idAventurier);
                break;

            case DEPLACER:
                if(msg.action == 0) {
                    this.ileInterdite.deplacerAventurier(msg.nomTuile, msg.idAventurier);
                    this.ihm.activerActions(msg.idAventurier, true, true, true, true, false, this.ihm.getVueAventuriers().get(msg.idAventurier).getNomAventurier() == "Navigateur", true);
                } else { //pouvoir du navigateur
                    Tuile tuile = this.ileInterdite.getGrille().getTuile(msg.nomTuile);
                    Aventurier av = this.ileInterdite.getAventuriers().get(msg.idAventurier);
                    av.getPosition().removeAventurier(av);
                    av.setPosition(tuile);
                    av.getPosition().addAventurier(av);
                    ileInterdite.getAventuriers().get(ileInterdite.getJoueurCourant()).moinsActions();
                    ihm.majVueJeu();
                }
                break;

            case ASSECHER:
                Tuile tuile = this.ileInterdite.getGrille().getTuile(msg.nomTuile);
                if(msg.action == 1)
                    this.ileInterdite.getAventuriers().get(msg.idAventurier).assecher(tuile);
                else
                    tuile.setEtat(EtatTuile.NORMAL);
                this.ihm.majVueJeu();
                this.ihm.activerActions(msg.idAventurier, true, true, true, true, false, this.ihm.getVueAventuriers().get(msg.idAventurier).getNomAventurier() == "Navigateur", true);
                break;

            case BOUGER:
                ihm.lanceChoisirBougerJoueur(msg.idAventurier);
                break;

            case LANCER_PVNAVIGATEUR:
                ArrayList<Boolean> possibilites = this.ileInterdite.getAventuriers().get(msg.idAventurier).isDeplacementPossibles();
                ileInterdite.lanceSurbriller(msg.idAventurier, possibilites, 3);
                break;

            case LANCE_CURSEUR:
                this.ihm.placerCurseur(msg.valeur);
                break;
                
            case JOUEUR_SUIVANT:
                this.ihm.changerJoueurCourant(msg.idAventurier);
                this.ihm.setActionRestantes(msg.idAventurier, msg.actionRestantes);
                this.ihm.majVueJeu();
                break;
                
            case LANCER_JEU:
                this.ileInterdite.lancePartie();
                break;

            case AUGMENTER_CURSEUR:
                this.ihm.placerCurseur(msg.valeur);
                break;
                
            case ACTION_RESTANTES:
                this.ihm.setActionRestantes(msg.idAventurier, msg.actionRestantes);
                break;
                
            case ACTUALISER_MAIN:
                this.ihm.actualiserMainJoueur(msg.main, msg.idAventurier);
                break;
                
            case DEMANDE_DEFFAUSE:
                this.ihm.demandeDefausse(msg.idAventurier);
                break;
                
            case DEFAUSSE_CARTE:
                this.ileInterdite.getAventuriers().get(msg.idAventurier).defausseCarte(msg.idCarte);
                this.ileInterdite.lanceFinTour();
                break;  

            case JOUE_CARTE:
                this.ileInterdite.getAventuriers().get(msg.idAventurier).joueCarte(msg.idCarte);
                break;
            
            case CARTE_JOUE:
                this.ihm.actualiserMainJoueur(msg.main, msg.idAventurier);
                if(msg.idAventurier != this.ileInterdite.getJoueurCourant()) {
                    this.ihm.activerActions(msg.idAventurier, false, false, false, false, false, false, false);
                }
                break;
                
            case FIN:
                this.ihm.finGagne(false);
                break;

            case GAGNEE:
                this.ihm.finGagne(true);
                break;
                
            case DEPLACEMENT_DURGENCE:
                this.ihm.surbrillerTuiles(msg.possibilites, msg.pion, msg.action, msg.idAventurier);
                break;
                
            case CLICK_HELICO:
                this.ihm.surbrillerTuiles(msg.possibilites, msg.pion, msg.action, msg.idAventurier);
                break;
                
            case FIN_DON:
                this.ihm.getVueAventuriers().get(msg.idAventurier).setDescription(msg.isReussi == true ? "Don effectué !" : "Erreur, le don n'a\npas pu être effectué !");
                break;
                
            case DEPLACER_HELICO:
                this.ileInterdite.deplacerAventuriers(msg.nomTuile, msg.idAventurier);
                break;

            default:
                if (Parameters.LOGS) {
                    System.err.println("Action interdite !");
                }
        }
    }
}
