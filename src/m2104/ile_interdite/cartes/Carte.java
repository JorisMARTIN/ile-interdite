/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.cartes;
import m2104.ile_interdite.aventuriers.Aventurier;
import m2104.ile_interdite.modele.Deck;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
* 
*/
public abstract class Carte {
    private Deck deck;
    private Aventurier aventurier;
	
    public Carte(Deck deck) {
        this.deck = deck;
        this.aventurier = null;
    }

    public Deck getDeck(){
        return this.deck;
    }

    public void setDeck(Deck newDeck){
        this.deck = newDeck;
    }

    public Aventurier getAventurier(){
        return this.aventurier;
    }

    public void setAventurier(Aventurier aventurier){
        this.aventurier = aventurier;
    }
    
    public abstract void action();
  
}
