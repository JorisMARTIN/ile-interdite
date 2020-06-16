package m2104.ile_interdite.controleur;

import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Parameters;
import m2104.ile_interdite.vue.IHM;
import patterns.observateur.Observateur;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@iut2.univ-grenoble-alpes.fr>
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
            System.out.println("Controleur.traiterMessage" + msg.commande);
        }

        switch (msg.commande) {
        
            case INITIALISER:
                String[] nomAventuriers = this.ileInterdite.inscrireJoueurs(msg.nbJoueurs);
                this.ileInterdite.setCurseur(msg.difficulte);
                this.ihm.creerVuesAventuriers(nomAventuriers);
                this.ileInterdite.initGrille();
                break;
                
            case INITIALISER_GRILLE:
            	
            	this.ihm.creeVueJeu(msg.grille);
            	
            	break;
            	
            case BOUGER:
                this.ileInterdite.getAventuriers().get(msg.idAventurier).seDeplacer(this.ileInterdite.getGrille().getTuile(msg.tuile));
                break;
                
            case ASSECHER:
                this.ileInterdite.getAventuriers().get(msg.idAventurier).assecher(this.ileInterdite.getGrille().getTuile(msg.tuile));
                break;
            
            case DONNER:
                break;
            
            case RECUPERER_TRESOR:
                break;
            
            case TERMINER:
            	this.ileInterdite.joueurSuivant();
                break;
            
            case RECEVOIR:
                break;
            
            case CHOISIR_CARTE:
                break;
            
            case CHOISIR_TUILE:
                break;
            
            /*case DEPLACER:
                break;*/
            
            case VOIR_DEFAUSSE:
                break;

            /*Ajout des nouveaux cas pour les nouveaux messages*/
            case MAJ_GRILLE:
                ihm.majVueJeu(msg.grille);
                break;

            case TUILES_POSSIBLES:
                ihm.surbrillerTuiles(msg.possibilites);
                break;

            case DEPLACEMENT:
                ileInterdite.lanceDeplacement();
                break;

            case DEPLACER:
                ileInterdite.deplacerAventurier(msg.nomTuile);
                break;

            case RECUP_TRESOR:
                ileInterdite.lanceRecuperationTresor();
                break;

            case AUGMENTER_CURSEUR:
                ihm.placerCurseur(msg.valeur);
                break;
                
            case JOUEUR_SUIVANT:
                ihm.changerJoueurCourant(msg.idAventurier);
                break;
                
            case LANCER_JEU:
                ileInterdite.lancePartie();
                break;

            default:
                if (Parameters.LOGS) {
                    System.err.println("Action interdite !");
                }
        }
    }
}
