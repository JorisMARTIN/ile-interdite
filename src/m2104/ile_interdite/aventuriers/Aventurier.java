package m2104.ile_interdite.aventuriers;

import java.util.ArrayList;

import m2104.ile_interdite.cartes.Carte;
import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.modele.Tuile;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class Aventurier {
    /*attribus*/
    private String nom;
    private ArrayList<Carte> main;
    private IleInterdite ileInterdite;
    private Tuile position;
    
    /*construteur*/
    public Aventurier(IleInterdite ileInterdite){
        this.main = new ArrayList<>();
        this.ileInterdite = ileInterdite;
        this.position = null;
    }
    
    /*méthodes*/
    public void seDeplacer(Tuile tuile){
    }
    
    public void assecher(Tuile tuile){
    }
    
    protected boolean peutAssecher(Tuile tuile){
        return false;
    }
    
    public void donnerCarteTresor(Aventurier a, Carte carte) {
    }
    
    public void recupererTresort() {
    }
    
    public Tuile getPosition(){
        return this.position;
    }
    
    public ArrayList<Carte> getMain(){
        return null;
    }
    
    public void piocherCarte() {
    }

	public boolean peutSeDeplacer(Tuile tuile) {
		// TODO Auto-generated method stub
		return false;
	}
}
