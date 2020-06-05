/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.modele;

/**
 *
 * @author tang
 */
public class Messager extends Aventurier{
    /*construteur*/
    public Messager(String nom, IleInterdite ileInterdite) {
        super(nom, ileInterdite);
    }
    
    /*méthodes*/
    @Override
    public void donnerCarteTresor(Aventurier a, Carte carte) {
        super.donnerCarteTresor(a, carte); //To change body of generated methods, choose Tools | Templates.
    }
    
}
