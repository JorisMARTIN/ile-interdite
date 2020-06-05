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
    
    /*construteur*/
    public Aventurier(String nom){
        this.nom = nom;
        this.main = new ArrayList<>();
    }
    
    /*méthodes*/
    public void seDeplacer(Tuile case){
    }
    
    public boolean peutSeDeplacer(Tuile case){
        return false;
    }
    
    public void assecher(Tuile case){
    }
    
    public boolean peutAssecher(Tuile case){
        return false;
    }
    
    public void donnerCarteTresor(Aventurier a, Carte carte){
    }
    
    public void recupererTresort(){
    }
    
    public Tuile getPosition(){
        return null;
    }
    
    public ArrayList<Carte> getMain(){
        return null;
    }
    
    public void piocherCarte(){}
}
