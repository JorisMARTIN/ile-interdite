package m2104.ile_interdite.modele;

import java.util.ArrayList;
import java.util.Collections;

import m2104.ile_interdite.cartes.Carte;
/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
*/
public class Deck {
    private ArrayList<Carte> pioche;
    private ArrayList<Carte> defausse;
    private IleInterdite ileInterdite;

    public Deck(IleInterdite ileInterdite) {
        pioche = new ArrayList<Carte>();
        defausse = new ArrayList<Carte>();
        this.ileInterdite = ileInterdite;
    }

    /**

     * @param cartes les cartes ajoutées
     * 
     * Rempli la pioche avec les cartes en les melangeant
     */
    public void remplirPioche(ArrayList<Carte> cartes) {
        pioche.addAll(melanger(cartes));
    }

    public void defausseCarte(Carte carte) {
        pioche.remove(carte);
        defausse.add(carte);
    }

    public boolean isVide() {
        return pioche.isEmpty();
    }

    public Carte getPremiereCarte() {
        return pioche.get(pioche.size() - 1);
    }
    
    // Melange la collection de carte placer en param�tre
    private ArrayList<Carte> melanger(ArrayList<Carte> cartes){
    	
    	Collections.shuffle(cartes);
    	
    	return cartes;
    }

    public IleInterdite getIleInterdite() {
        return this.ileInterdite;
    }

    public ArrayList<Carte> getPioche() {
        return this.pioche;
    }

    public ArrayList<Carte> getDefausse() {
        return this.defausse;
    }
}