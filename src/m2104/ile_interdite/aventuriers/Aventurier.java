package m2104.ile_interdite.aventuriers;
    
import java.util.ArrayList;
import m2104.ile_interdite.cartes.Carte;
import m2104.ile_interdite.cartes.CarteHelicoptere;
import m2104.ile_interdite.cartes.CarteMonteeEaux;
import m2104.ile_interdite.cartes.CarteSacDeSable;
import m2104.ile_interdite.cartes.CarteTresor;
import m2104.ile_interdite.modele.IleInterdite;  
import m2104.ile_interdite.modele.Tuile; 
import m2104.ile_interdite.util.Message; 
import m2104.ile_interdite.util.Utils;
import m2104.ile_interdite.util.Utils.Commandes;
import m2104.ile_interdite.util.Utils.Tresor;
import m2104.ile_interdite.modele.EtatTuile;
    
/**
 *
 * @author Thomas JHISTARRY
 * @author Joris MARTIN
 * @author Tanguy SIGNORET
 * @author Mattéo PAPPALARDO
 * 
 */
public abstract class Aventurier {
    
    private ArrayList<Carte> main;
    private IleInterdite ileInterdite;
    private Tuile position;
    private int actionsRestantes;
    private Utils.Pion pion;
    private ArrayList<Tresor> tresors;
    private boolean aPioche;
        
        
    public Aventurier(IleInterdite ileInterdite, Utils.Pion pion) {
        
        this.main = new ArrayList<Carte>();
        this.tresors = new ArrayList<Tresor>();
        this.ileInterdite = ileInterdite;
        this.pion = pion;
        this.aPioche = false;
        
        switch (this.pion) {
            case ROUGE:
                this.position = ileInterdite.getGrille().getTuile("LaPorteDeBronze");
                break;

            case VERT:
                this.position = ileInterdite.getGrille().getTuile("LaPorteDeCuivre");
                break;

            case BLEU:
                this.position = ileInterdite.getGrille().getTuile("Heliport");
                break;

            case ORANGE:
                this.position = ileInterdite.getGrille().getTuile("LaPortedArgent");
                break;

            case VIOLET:
                this.position = ileInterdite.getGrille().getTuile("LaPorteDeFer");
                break;

            case JAUNE:
                this.position = ileInterdite.getGrille().getTuile("LaPortedOr");
                break;
            
            default:
                this.position = null;
                break;
        }
        this.position.addAventurier(this);
    }

    public boolean peutSeDeplacer(Tuile tuile) {
        if (tuile == null || tuile.isRetiree() || getPosition() == tuile) {
            return false;
        }

        int indexTuileCible = this.ileInterdite.getGrille().getTuiles(true).indexOf(tuile);
        int indexTuileActuelle = this.ileInterdite.getGrille().getTuiles(true).indexOf(getPosition());
        return (indexTuileActuelle < 30 && indexTuileActuelle + 6 == indexTuileCible)
            || (indexTuileActuelle < 35 && indexTuileActuelle + 1 == indexTuileCible && (indexTuileActuelle % 6) != 5)
            || (indexTuileActuelle > 5 && indexTuileActuelle - 6 == indexTuileCible)
            || (indexTuileActuelle > 0 && indexTuileActuelle - 1 == indexTuileCible && (indexTuileActuelle % 6) != 0);
    }
    
    public ArrayList<Boolean> isDeplacementPossibles() {
        ArrayList<Boolean> deplacementsPossibles = new ArrayList<Boolean>();
        for (Tuile tuile : this.ileInterdite.getGrille().getTuiles(true)) {
            deplacementsPossibles.add(this.peutSeDeplacer(tuile));
        }
        return deplacementsPossibles;
    }
    
    
    public void deplacer(Tuile tuile) {
        this.position.removeAventurier(this);
        this.position = tuile;
        this.position.addAventurier(this);
        
        moinsActions();
    }
    
    protected boolean peutAssecher(Tuile tuile) {
        if(tuile == null || !tuile.isInnondee()) {
            return false;
        }
        
        int indexTuileCible = this.ileInterdite.getGrille().getTuiles(true).indexOf(tuile);
        int indexTuileActuelle = this.ileInterdite.getGrille().getTuiles(true).indexOf(getPosition());

        return (indexTuileCible == indexTuileActuelle)
            || (indexTuileActuelle < 30 && indexTuileActuelle + 6 == indexTuileCible)
            || (indexTuileActuelle < 35 && indexTuileActuelle + 1 == indexTuileCible && (indexTuileActuelle % 6) != 5)
            || (indexTuileActuelle > 5 && indexTuileActuelle - 6 == indexTuileCible)
            || (indexTuileActuelle > 0 && indexTuileActuelle - 1 == indexTuileCible && (indexTuileActuelle % 6) != 0);
    }
    
    public ArrayList<Boolean> isAssechementPossibles() {
        ArrayList<Boolean> assechementsPossibles = new ArrayList<Boolean>();
        for (Tuile tuile : this.ileInterdite.getGrille().getTuiles(true)) {
            assechementsPossibles.add(this.peutAssecher(tuile));
            
        }
        return assechementsPossibles;
    }
    
    public void assecher(Tuile tuile) {
        tuile.setEtat(EtatTuile.NORMAL);
        moinsActions();
    }
    
    public Tuile getPosition() {
        return this.position;
    }
        
    public boolean peutDonnerCarteTresor(Aventurier receveur, int idCarte) {
        
        Carte carte = this.main.get(idCarte);
        
        return receveur.getPosition() == this.position && receveur.getMain().size() < 5 && !(carte instanceof CarteHelicoptere) && !(carte instanceof CarteSacDeSable);
        
    }
    
    /**
     * 
     * <ol>
     *  <li>Ajoute la carte a la main du receuveur</li>
     *  <li>Retire la carte de la main du joueur</li>
     *  <li>Actualise les deux mains</li>
     * </ol>
     * 
     * @param receveur : Le receveur de la carte
     * @param idCarte : L'id de la carte donnee
     * 
     */
    public void donnerCarteTresor(Aventurier receveur, int idCarte) {

        Carte carte = this.main.get(idCarte);
        
        this.main.remove(carte);
        receveur.getMain().add(carte);
        
        moinsActions();
        
        Message msg = new Message(Commandes.ACTUALISER_MAIN);
        msg.idAventurier = this.ileInterdite.getAventuriers().indexOf(this);
        msg.main = this.main;
        
        this.ileInterdite.notifierObservateurs(msg);
        
        Message msg2 = new Message(Commandes.ACTUALISER_MAIN);
        msg2.idAventurier = this.ileInterdite.getAventuriers().indexOf(receveur);
        msg2.main = receveur.getMain();
        
        this.ileInterdite.notifierObservateurs(msg2);
        
    }
    
    /**
     * <ol>
     *  <li>Retrait de 4 carte trésor correspondante de la main du joueur</li>
     *  <li>Ajout du tresor a la liste "tresors" de l'aventurier</li>
     *  <li>Retrait du tresor de la liste "tresorsEnJeu" de IleInterdite</li>
     *  <li>Enlever une action</li>
     *  <li>Met a jour la vueAventurier</li>
     * </ol>
     */
    public void recupererTresor() {
            
        Utils.Tresor tresor = this.position.getTresor();
        
        int i = 4;
        for(CarteTresor carte : getCartesTresorsEnMain()) {
            if(carte.getTresor() == tresor) {
                this.ileInterdite.getDeckTresor().getDefausse().add(carte);
                this.main.remove(carte);
                i--;
            }
            if(i == 0) break;
        }
        
        this.tresors.add(tresor);
        this.ileInterdite.getTresorsEnJeu().remove(tresor);
        
        moinsActions();
        
        Message msg = new Message(Utils.Commandes.ACTUALISER_MAIN);
        
        msg.main = main;
        msg.idAventurier = this.ileInterdite.getAventuriers().indexOf(this);
        
        this.ileInterdite.notifierObservateurs(msg);
      
        
    }
    
    public ArrayList<Carte> getMain() {
        return this.main;
    }
    
    /**
     * 
     * Donne 2 cartes au début de la partie differente de montée des eaux
     */
    public void initCarte() {
        
        for(int i=0; i<2; i++) {
            
            Carte c = this.ileInterdite.getDeckTresor().getPremiereCarte();
            
            int size = this.ileInterdite.getDeckTresor().getPioche().size() - 2;
            
            while(c instanceof CarteMonteeEaux) {
                c = this.ileInterdite.getDeckTresor().getPioche().get(size);
                size --;
            }
            
            this.main.add(c);
            c.setAventurier(this);
            this.ileInterdite.getDeckTresor().getPioche().remove(c);
        }
        
        Message msg = new Message(Utils.Commandes.ACTUALISER_MAIN);
        
        msg.main = main;
        msg.idAventurier = this.ileInterdite.getAventuriers().indexOf(this);
        
        ileInterdite.notifierObservateurs(msg);
        
    }
    
    /**
     * <h1>L'aventurier pioche une carte trésor</h1>
     * <ul>
     *  <li>Si l'aventurier obtien plus de 5 cartes, il doit en deffausser une</li>
     * </ul>
     * @param i : nombre de cartes a piocher 
     */
    public void piocherCartes(int nbCartes) { 
        
        for(int i=0; i < nbCartes; i++) {
            
            Carte carte = this.ileInterdite.getDeckTresor().getPremiereCarte();
            if (carte instanceof CarteMonteeEaux) {
                carte.action();
            } else {
                this.main.add(carte);
                carte.setAventurier(this);
                this.ileInterdite.getDeckTresor().getPioche().remove(carte);                
            }
            
            if(this.ileInterdite.getDeckTresor().isVide()) {
                this.ileInterdite.getDeckTresor().remplirPioche(this.ileInterdite.getDeckTresor().getDefausse());
                this.ileInterdite.getDeckTresor().getDefausse().clear();
            }
            
        
        }
        
        
            Message msg = new Message(Utils.Commandes.ACTUALISER_MAIN);
            
            msg.main = main;
            msg.idAventurier = this.ileInterdite.getAventuriers().indexOf(this);
            
            ileInterdite.notifierObservateurs(msg);
    }
        
    
    public void initActionsRestantes() {
        this.aPioche = false;
        this.actionsRestantes = 3;
        ileInterdite.notifyActionRestantes(actionsRestantes, this);
    }

    public void moinsActions() {
        this.actionsRestantes--;
        
        ileInterdite.notifyActionRestantes(actionsRestantes, this);
        
        if(this.actionsRestantes == 0) {
            Message msg = new Message(Utils.Commandes.ZERO_ACTIONS);
            
            msg.idAventurier = this.ileInterdite.getAventuriers().indexOf(this);
            
            ileInterdite.notifierObservateurs(msg);
        }
        
    }

    public int getActionsRestantes() {
        return actionsRestantes;
    }
    
    /**
     * <ol>
     *  <li>Verifie si le joueur est sur une tuile permettant de recuperer un tresor</li>
     *  <li>Compte si il possède le bon nombre de carte tresor corespondante</li>
     *      <ul>
     *          <li>Si oui : return true</li>
     *          <li>Si non : return false</li>
     *      </ul>
     * </ol>
     */
    public boolean peutRecupererTresor() {
        
        
        if(this.position.getTresor() != null) {
            
            int nbCarte = 0;
            
            switch (this.position.getTresor()) {
            
            case CALICE:
                
                for(CarteTresor carte : this.getCartesTresorsEnMain()) {
                    
                    if(carte.getTresor() == Utils.Tresor.CALICE) {
                        nbCarte++;
                    }
                }
                
                if(nbCarte >= 4) return true;
                
                break;
                
            case PIERRE:
                
                for(CarteTresor carte : this.getCartesTresorsEnMain()) {
                    
                    if(carte.getTresor() == Utils.Tresor.PIERRE) {
                        nbCarte++;
                    }
                }
                
                if(nbCarte >= 4) return true;
                
                break;
                
            case CRISTAL:
                
                for(CarteTresor carte : this.getCartesTresorsEnMain()) {
                    
                    if(carte.getTresor() == Utils.Tresor.CRISTAL) {
                        nbCarte++;
                    }
                }
                
                if(nbCarte >= 4) return true;
                
                break;
                
            case ZEPHYR:
                
                for(CarteTresor carte : this.getCartesTresorsEnMain()) {
                    
                    if(carte.getTresor() == Utils.Tresor.ZEPHYR) {
                        nbCarte++;
                    }
                }
                
                if(nbCarte >= 4) return true;
                
                break;

            }
            
        }
        
        return false;

    }
    
    public Utils.Pion getPion() {
        return this.pion;
    }

    protected IleInterdite getIleInterdite() {
        return this.ileInterdite;
    }

    /**
     * 
     * @param idCarte : L'id de la carte qui va etre jouée
     */
    public void joueCarte(int idCarte) {
        
        Carte carte = this.main.get(idCarte);
        
        carte.action();
        
        this.main.remove(carte);
        this.ileInterdite.getDeckTresor().getDefausse().add(carte);
        
        Message msg = new Message(Utils.Commandes.CARTE_JOUE);
        msg.main = this.main;
        msg.idAventurier = this.ileInterdite.getAventuriers().indexOf(this);

        ileInterdite.notifierObservateurs(msg);
    }
    
    public void defausseCarte(int idCarte) {
        
        Carte carte = getMain().get(idCarte);
        getMain().remove(carte);

        this.ileInterdite.getDeckTresor().getDefausse().add(carte);

        Message msg = new Message(Utils.Commandes.ACTUALISER_MAIN);

        msg.main = this.main;
        msg.idAventurier = this.ileInterdite.getAventuriers().indexOf(this);

        ileInterdite.notifierObservateurs(msg);

    }

    public ArrayList<Tresor> getTresors() {
        return this.tresors;
    }

    public boolean isaPioche() {
        return aPioche;
    }

    public void setaPioche(boolean aPioche) {
        this.aPioche = aPioche;
    }

    public void setPosition(Tuile position) {
        this.position = position;
    }
    
    public ArrayList<CarteTresor> getCartesTresorsEnMain(){
        
        ArrayList<CarteTresor> cartesTresor = new ArrayList<CarteTresor>();
        
        for(Carte carte : this.main) {
            
            if(carte instanceof CarteTresor) {
                cartesTresor.add((CarteTresor) carte);
            }
            
        }
        
        return cartesTresor;
    }
    
    public void seFaireDeplacer() {
        ArrayList<Boolean> deplacementsPossibles = new ArrayList<Boolean>();

        ArrayList<Tuile> tuiles = this.ileInterdite.getGrille().getTuiles(true);
        int indexTuileActuelle = tuiles.indexOf(getPosition());
        int indexTuileCible;
        boolean deplacementPossible = false;
        
        for (Tuile tuile : tuiles) {
            if (tuile == null || tuile.isRetiree() || getPosition() == tuile) {
                deplacementPossible = false;
            } else {
                indexTuileCible = tuiles.indexOf(tuile);
                if (indexTuileActuelle < 30 && indexTuileActuelle + 6 == indexTuileCible) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle < 35 && indexTuileActuelle + 1 == indexTuileCible && (indexTuileActuelle % 6) != 5) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle > 5 && indexTuileActuelle - 6 == indexTuileCible) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle > 0 && indexTuileActuelle - 1 == indexTuileCible && (indexTuileActuelle % 6) != 0) {
                    deplacementPossible = true;
                } else if (indexTuileActuelle < 24 && indexTuileActuelle + 12 == indexTuileCible) {
                    if (!tuiles.get(indexTuileActuelle + 6).isRetiree()) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle < 34 && indexTuileActuelle + 2 == indexTuileCible && (indexTuileActuelle % 6) < 4) {
                    if (!tuiles.get(indexTuileActuelle + 1).isRetiree()) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 11 && indexTuileActuelle - 12 == indexTuileCible) {
                    if (!tuiles.get(indexTuileActuelle - 6).isRetiree()) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 1  && indexTuileActuelle - 2 == indexTuileCible && (indexTuileActuelle % 6) > 1) {
                    if (!tuiles.get(indexTuileActuelle - 1).isRetiree()) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle < 29 && indexTuileActuelle + 7 == indexTuileCible && (indexTuileActuelle % 6) != 5) {
                    if ((!tuiles.get(indexTuileActuelle + 1).isRetiree())
                     || (!tuiles.get(indexTuileActuelle + 6).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle < 31 && indexTuileActuelle + 5 == indexTuileCible && (indexTuileActuelle % 6) != 0) {
                    if ((!tuiles.get(indexTuileActuelle - 1).isRetiree())
                     || (!tuiles.get(indexTuileActuelle + 6).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 4 && indexTuileActuelle - 5 == indexTuileCible && (indexTuileActuelle % 6) != 5) {
                    if ((!tuiles.get(indexTuileActuelle + 1).isRetiree())
                     || (!tuiles.get(indexTuileActuelle - 6).isRetiree())) {
                        deplacementPossible = true;
                    }
                } else if (indexTuileActuelle > 6 && indexTuileActuelle - 7 == indexTuileCible && (indexTuileActuelle % 6) != 0) {
                    if ((tuiles.get(indexTuileActuelle - 1).getEtat() != EtatTuile.RETIREE)
                     || (tuiles.get(indexTuileActuelle - 6).getEtat() != EtatTuile.RETIREE)) {
                        deplacementPossible = true;
                    }
                } else {
                    deplacementPossible = false;
                }
            }
            deplacementsPossibles.add(deplacementPossible);
        }
        Message msg = new Message(Utils.Commandes.TUILES_POSSIBLES);
        msg.idAventurier = this.ileInterdite.getAventuriers().indexOf(this);
        msg.possibilites = deplacementsPossibles;
        msg.pion = this.getPion();
        msg.action = 3;
        this.ileInterdite.notifierObservateurs(msg);
        
    }
}
