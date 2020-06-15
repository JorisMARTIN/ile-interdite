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
    private Utils.Pion couleur;
    
    public Aventurier(IleInterdite ileInterdite, Utils.Pion couleur) {
        this.main = new ArrayList<>();
        this.ileInterdite = ileInterdite;
        this.couleur = couleur;
        
        switch (couleur) {
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
    }

    public boolean peutSeDeplacer(Tuile tuile) {
        return tuile != null && tuile.getEtat() != EtatTuile.RETIREE;
    }
    
    public ArrayList<Boolean> isDeplacementPossible() {
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
        this.position = tuile;
    }
    
    protected boolean peutAssecher(Tuile tuile) {
        //TODO
        return false;
    }
    
    public void assecher(Tuile tuile) {
        //TODO
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
}
