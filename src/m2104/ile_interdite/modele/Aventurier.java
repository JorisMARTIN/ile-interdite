package m2104.ile_interdite.modele;

import java.util.Collection;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class Aventurier {
    /*attribus*/
    private String nom;
    
    /*construteur*/
    public Aventurier(String nom){
        this.nom = nom;
    }
    
    /*méthodes*/
    public void seDeplacer(Tuile case){
    }
    
    public boolean peutSeDeplacer(Tuile case){
        return null;
    }
    
    public void assecher(Tuile case){
    }
    
    public boolean peutAssecher(Tuile case){
    }
    
    public void donnerCarteTresor(Aventurier a, Carte carte){
    }
    
    public void recupererTresort(){
    }
    
    public Tuile getPosition(){
        return null;
    }
    
    public Collection<Carte> getMain(){
        return null;
    }
    
    public void piocherCarte(){}
}
