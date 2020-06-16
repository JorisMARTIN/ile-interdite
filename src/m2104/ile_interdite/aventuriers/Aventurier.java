package m2104.ile_interdite.aventuriers;

import java.util.ArrayList;

import m2104.ile_interdite.cartes.Carte;
import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.util.Utils;
import m2104.ile_interdite.modele.EtatTuile;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class Aventurier {
    private ArrayList<Carte> main;
    private IleInterdite ileInterdite;
    private Tuile position;
    private int actionsRestantes;
    private Utils.Pion pion;
    
    public Aventurier(IleInterdite ileInterdite, Utils.Pion pion) {
        this.main = new ArrayList<>();
        this.ileInterdite = ileInterdite;
        this.pion = pion;
        
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
                this.position = ileInterdite.getGrille().getTuile("LaPorteDOr");
                break;
            
            default:
                this.position = null;
                break;
        }
        this.position.addAventurier(this);
    }

    public boolean peutSeDeplacer(Tuile tuile) {
    	
        boolean peutSeDeplacer = true;
        
        int indexTuileCible = this.ileInterdite.getGrille().getTuiles(true).indexOf(tuile);
        int indexTuileActuelle = this.ileInterdite.getGrille().getTuiles(true).indexOf(this.position);
        
        if (indexTuileCible == indexTuileActuelle || tuile == null) {
            peutSeDeplacer = false;
        } else if ((indexTuileActuelle < 29 && indexTuileActuelle + 6 == indexTuileCible)
             || (indexTuileActuelle < 35 && indexTuileActuelle + 1 == indexTuileCible)
             || (indexTuileActuelle > 5  && indexTuileActuelle - 6 == indexTuileCible)
             || (indexTuileActuelle > 0  && indexTuileActuelle - 1 == indexTuileCible)) {
                if (tuile.getEtat() == EtatTuile.RETIREE) {
                    peutSeDeplacer = false;
                }
        } else {
            peutSeDeplacer = false;
        }
        return peutSeDeplacer;
    }
    
    public ArrayList<Boolean> isDeplacementPossibles() {
        ArrayList<Boolean> deplacementsPossibles = new ArrayList<Boolean>();
        for (Tuile tuile : this.ileInterdite.getGrille().getTuiles(true)) {
            deplacementsPossibles.add(this.peutSeDeplacer(tuile));
        }
        return deplacementsPossibles;
    }
    
    public Tuile getPosition() {
        return this.position;
    }
    
    public void seDeplacer(Tuile tuile) {
        this.position.removeAventurier(this);
        this.position = tuile;
        this.position.addAventurier(this);
    }
    
    protected boolean peutAssecher(Tuile tuile) {
        //TODO
        return false;
    }
    
    public void assecher(Tuile tuile) {
        if (peutAssecher(tuile)) {
            tuile.setEtat(EtatTuile.NORMAL);
        }
    }
    
    public void donnerCarteTresor(Aventurier a, Carte carte) {
        //TODO
    }
    
    public void recupererTresor() {
        //TODO
    }
    
    public ArrayList<Carte> getMain() {
        return this.main;
    }
    
    public void piocherCarte() {
        //TODO
    }
    
    public void initActionsRestantes() {
        this.actionsRestantes = 3;
    }

    public void moinsActions() {
        this.actionsRestantes--;
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

    public void joueCarte(Carte carte) {
        if(carte.getClass().toString() == "CarteMonteeEaux") {
            for (Carte c : this.getMain()) {
                if (c.getClass().toString() == "CarteMonteeEaux") {
                    c.action();
                }
            }
        } else {
            //TODO the case of jouerCarte(Carte carte)
        }
    }
}
