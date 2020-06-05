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
public class Ingenieur extends Aventurier{
    /*construteur*/
    public Ingenieur(IleInterdite ileInterdite) {
        super(ileInterdite);
    }
    
    /*méthodes*/
    @Override
    public boolean peutAssecher(Tuile tuile) {
        return false;
    }
    
}
