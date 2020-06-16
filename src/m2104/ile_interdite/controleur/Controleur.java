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
            	
            	this.ihm.creeVueJeu(msg.grille);
            	
            	break;
            	
            case BOUGER:
                this.ileInterdite.lanceDeplacement();
                break;
                
            case ASSECHER:
                this.ileInterdite.getAventuriers().get(msg.idAventurier).assecher(this.ileInterdite.getGrille().getTuile(msg.tuile));
                break;
            
            case DONNER:
                break;
            
            case RECUPERER_TRESOR:
            	this.ileInterdite.getAventuriers().get(msg.idAventurier).recupererTresor();
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
                ihm.majVueJeu();
                break;

            case TUILES_POSSIBLES:
                ihm.surbrillerTuiles(msg.possibilites, msg.pion);
                break;

            case DEPLACER:
                ileInterdite.deplacerAventurier(msg.nomTuile);
                break;

            case RECUP_TRESOR:
                ileInterdite.lanceRecuperationTresor();
                break;

            case LANCE_CURSEUR:
                ihm.placerCurseur(msg.valeur);
                break;
                
            case JOUEUR_SUIVANT:
                ihm.changerJoueurCourant(msg.idAventurier);
                ihm.setActionRestantes(msg.idAventurier, msg.actionRestantes);
                break;
                
            case LANCER_JEU:
                ileInterdite.lancePartie();
                break;

            case AUGMENTER_CURSEUR:
                ihm.placerCurseur(msg.valeur);
                break;
                
            case ACTION_RESTANTES:
                ihm.setActionRestantes(msg.idAventurier, msg.actionRestantes);
                break;
                
            case PIOCHE_CARTE:
            	ihm.actualiserMainJoueur(msg.main, msg.tropCarte, msg.idAventurier);
            	break;
            	
            case ACTUALISER_MAIN:
            	ihm.actualiserMainJoueur(msg.main, false, msg.idAventurier);
            	break;
            	
            case DEFAUSSE_CARTE:
            	ileInterdite.getAventuriers().get(msg.idAventurier).defausseCarte(msg.idCarte);
            	
            	break;	

            case FIN:
                ihm.finPasGagne();
                break;

            case GAGNEE:
                ihm.finGagne();
                break;

            default:
                if (Parameters.LOGS) {
                    System.err.println("Action interdite !");
                }
        }
    }
}
