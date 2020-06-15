package m2104.ile_interdite.util;

import java.util.ArrayList;

import m2104.ile_interdite.modele.Grille;

/**
 *
 * @author IUT2-Dept Info
 */
public class Message {

	public Utils.Commandes commande;
	public Integer difficulte;
    public Integer idAventurier;
    public Integer idCarte;
    public Utils.Tresor tresor;
    public String tuile;
    public Integer nbJoueurs;
    public Grille grille;
    /*Ajout de nouveaux attribus*/
    public Arraylist<Boolean> possibilites;
    public String nomTuile;
    public int valeur;
    
    
    public Message(Utils.Commandes cmd) {
        this.commande = cmd;
        this.possibilites = new ArrayList<>();
    }
}
