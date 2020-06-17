package m2104.ile_interdite.aventuriers;
    
import java.util.ArrayList;
import m2104.ile_interdite.cartes.Carte;
import m2104.ile_interdite.cartes.CarteHelicoptere;
import m2104.ile_interdite.cartes.CarteMonteeEaux; 
import m2104.ile_interdite.modele.IleInterdite;  
import m2104.ile_interdite.modele.Tuile; 
import m2104.ile_interdite.util.Message; 
import m2104.ile_interdite.util.Utils;
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
        return (indexTuileActuelle < 29 && indexTuileActuelle + 6 == indexTuileCible)
            || (indexTuileActuelle < 35 && indexTuileActuelle + 1 == indexTuileCible && (indexTuileActuelle) % 6 != 5)
            || (indexTuileActuelle > 5 && indexTuileActuelle - 6 == indexTuileCible)
            || (indexTuileActuelle > 0 && indexTuileActuelle - 1 == indexTuileCible && (indexTuileActuelle) % 6 != 0);
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
        if(tuile == null || !tuile.isInnondee() || tuile.isRetiree()) {
            return false;
        }
        
        int indexTuileCible = this.ileInterdite.getGrille().getTuiles(true).indexOf(tuile);
        int indexTuileActuelle = this.ileInterdite.getGrille().getTuiles(true).indexOf(getPosition());

        return (indexTuileCible == indexTuileActuelle)
        	||	(indexTuileActuelle < 29 && indexTuileActuelle + 6 == indexTuileCible)
            || (indexTuileActuelle < 35 && indexTuileActuelle + 1 == indexTuileCible && (indexTuileActuelle) % 6 != 5)
            || (indexTuileActuelle > 5 && indexTuileActuelle - 6 == indexTuileCible && (indexTuileActuelle) % 6 != 0)
            || (indexTuileActuelle > 0 && indexTuileActuelle - 1 == indexTuileCible);
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
        
    public void donnerCarteTresor(Aventurier a, Carte carte) {
        if (a.getPosition() == this.getPosition()
            && carte.getClass().toString() != "CarteSacDeSable"
            && carte.getClass().toString() != "CarteHelicoptere" ) {
            a.getMain().add(carte);
        }
        
        moinsActions();
    }
    
    public void recupererTresor() {
        //TODO
        
        moinsActions();
    }
    
    public ArrayList<Carte> getMain() {
        return this.main;
    }
    
    /**
     * 
     * Donne 2 cartes au début de la partie
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
        
        Message msg = new Message(Utils.Commandes.PIOCHE_CARTE);
        
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
        
        
            Message msg = new Message(Utils.Commandes.PIOCHE_CARTE);
            
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

    public boolean peutRecupererTresort() {
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

        Message msg = new Message(Utils.Commandes.PIOCHE_CARTE);

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

    public void setaPioche(boolean aPioche) { this.aPioche = aPioche;
    }
}
