package m2104.ile_interdite.modele;

import java.util.ArrayList;

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
    public Aventurier(String nom, IleInterdite ileInterdite){
        this.nom = nom;
        this.main = new ArrayList<>();
        this.ileInterdite = ileInterdite;
        this.position = null;
    }
    
    /*méthodes*/
    public void seDeplacer(Tuile case){
    }
    
    protected boolean peutSeDeplacer(Tuile case){
        return false;
    }
    
    public void assecher(Tuile case){
    }
    
    protected boolean peutAssecher(Tuile case){
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
}
