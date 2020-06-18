package m2104.ile_interdite.util;

import java.util.ArrayList;

import m2104.ile_interdite.cartes.Carte;
import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.util.Utils;
import m2104.ile_interdite.util.Utils.Pion;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
*/
public class Message {

    public Utils.Commandes commande;
    public int difficulte;
    public int idAventurier;
    public int idCarte;
    public Utils.Tresor tresor;
    public String tuile;
    public Integer nbJoueurs;
    public Grille grille;
    /*Ajout de nouveaux attribus*/
    public ArrayList<Boolean> possibilites;
    public String nomTuile;
    public int valeur;
    public int actionRestantes;
    public int tropCarte;
    public ArrayList<Carte> main;
    public Pion pion;
    public Tuile tuileDepart;
    public Tuile tuileAction;
    public int action;
	public boolean isReussi;
    
    public Message(Utils.Commandes cmd) {
        this.commande = cmd;
        this.possibilites = new ArrayList<>();
    }
}
