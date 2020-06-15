/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.cartes;

/**
 *
 * @author tang
 */
public abstract class Carte {
    private Deck deck;
	
    public Carte() {
        this.deck = null;
    }
	
    public Carte(Deck deck) {
        this.deck = deck;
    }

    public Deck getDeck(){
        return this.deck;
    }

    public void setDeck(Deck newDeck){
        this.deck = newDeck;
    }
    
    public abstract void action();  
}
