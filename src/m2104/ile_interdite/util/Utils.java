package m2104.ile_interdite.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

import m2104.ile_interdite.aventuriers.Aventurier;

/**
 *
 * @author IUT2-Dept Info
 *
 * @author Thomas JHISTARRY
 * @author Joris MARTIN
 * @author Tanguy SIGNORET
 * @author Mattéo PAPPALARDO
 */
public class Utils {

    public static enum Commandes {
        VALIDER_JOUEURS("Valider l'inscription des joueurs"),
        LANCER_DEPLACEMENT("Choisir une case pour d�placer son pion"),
        LANCER_ASSECHEMENT("Choisir une case pour ass�cher une tuile"),
        DONNER("Donner une carte à un autre joueur"),
        RECUPERER_TRESOR("R�cup�rer le tr�sor de la tuile"),
        TERMINER("Terminer son tour"),
        RECEVOIR("Recevoir la carte donn�e par un autre joueur"),
        CHOISIR_CARTE("Utiliser une carte tr�sor"),
        CHOISIR_TUILE("S�lectionner une tuile"),
        DEPLACER("D�placer un joueur"),
        ASSECHER("Ass�cher une tuile"),
        ZERO_ACTIONS("Le joueur ne peut que utiliser une carte action sp�ciale ou finir son tour"),
        INITIALISER_GRILLE("Grille initialiser avec les tuiles innond�es"),
        INITIALISER("Initialisation joueur"),
        /*Ajout de nouveaux messages*/
        MAJ_GRILLE("Mise à jour de la grille"),
        BOUGER("Pouvoir du Navigateur"),
        LANCER_PVNAVIGATEUR("Lance le d�placement d'un pion en tant que pouvoir du Navigateur"),
        TUILES_POSSIBLES("Avec possibilit�, met en valeur les tuiles sur lequel l'action est possible"),
        AUGMENTER_CURSEUR("Augmenter le curseur de mont�e des eaux"),
        LANCE_CURSEUR("Lance le traitement afin d'augmenter le curseur de mont�e des eaux"),
        LANCER_JEU("Lancement du Jeu"),
        JOUEUR_SUIVANT("Passe au joueur suivant."),
        FIN("Fin de la partie les aventurier ont d�col�s mais ont perdu."),
        GAGNEE("Fin de la partie les aventurier ont d�col�s et ils ont gagn�es."),
        ACTION_RESTANTES("Nombre d'action restantes pour un joueur"),
        DEFAUSSE_CARTE("Le joueur d�fausse une carte"),
        JOUE_CARTE("Le Joueur joue une carte"),
        ACTUALISER_MAIN("Actualise la main du joueur"),
        DEMANDE_DEFFAUSE("Demande au joueur de d�ffausser une carte"),
        CARTE_JOUE("Le Joueur vient de jouer une carte"),
        CLICK_HELICO("Demande quel d�placement est voulu pour la carte jouée."),
        DEPLACEMENT_DURGENCE("Demande au joueur de se d�placer si cela est possible, toute autre action est impossible en dehors des cartes action sp�ciale"),
        DON_CARTE("Le joueur fait dont d'une carte"),
        FIN_DON("Le don de carte est terminer"),
        DEPLACER_HELICO("Deplacement par helicopt�re");


        private final String libelle ;

        Commandes(String libelle) {
            this.libelle = libelle ;
        }

        @Override
        public String toString() {
            return this.libelle ;
        }
    }

    public static enum Tresor {
        PIERRE("La Pierre Sacr�e", new Color(141,79,9), new Color(255,242,0), Parameters.TRESORS + "pierre.png"),
        ZEPHYR("La statue du Z�phyr", new Color(255,215,0), new Color(208,26,136), Parameters.TRESORS + "zephyr.png"),
        CRISTAL("Le Cristal Ardent", new Color(219,56,154), new Color(99,187,242), Parameters.TRESORS + "cristal.png"),
        CALICE("Le Calice de l'Onde", new Color(27,188,245), new Color(141,79,9), Parameters.TRESORS + "calice.png") ;

        private final String libelle;
        private final Color bgColor ;
        private final Color textColor ;
        private final String pathPicture ;

        Tresor(String libelle, Color bgColor, Color textColor, String pathPicture) {
            this.libelle = libelle;
            this.bgColor = bgColor ;
            this.textColor = textColor ;
            this.pathPicture = pathPicture ;
        }

        @Override
        public String toString() {
            return this.libelle ;
        }

        public Color getBgColor() {
            return this.bgColor ;
        }

        public Color getTextColor() {
            return this.textColor ;
        }

        public String getPathPicture() {
            return this.pathPicture ;
        }

        public static Tresor getFromName(String name) {
            for (Tresor tresor: Tresor.values()) {
                if (tresor.name().equals(name)) {
                    return tresor;
                }
            }
            return null;
        }
    }

    public static enum Pion {
        ROUGE("Rouge", new Color(255, 0, 0), new Color(176, 79, 79), new Color(255, 145, 145), new Color(226,166,166), "images/pions/pionRouge.png"),
        VERT("Vert", new Color(0, 195, 0), new Color(79, 153, 79), new Color(145, 255, 145), new Color(166,226,166), "images/pions/pionVert.png"),
        BLEU("Bleu", new Color(55,194,198), new Color(100,153,154), new Color(175,221,221), new Color(202,219,219), "images/pions/pionBleu.png"),
        ORANGE("Orange", new Color(255, 148, 0), new Color(176, 135, 79), new Color(255, 199, 127), new Color(246,198,135), "images/pions/pionBronze.png"),
        VIOLET("Violet", new Color(204, 94, 255), new Color(146, 115, 176), new Color(211, 164, 234), new Color(202,176,214), "images/pions/pionViolet.png"),
        JAUNE("Jaune", new Color(255, 255, 0), new Color(176, 176, 79), new Color(255, 255, 140), new Color(245,245,148), "images/pions/pionJaune.png") ;

        private final String libelle ;
        private final Color couleur ;
        private final Color couleurGrisee ;
        private final Color couleurSelectionTuileAssechee ;
        private final Color couleurSelectionTuileInondee ;
        private final String picturePath ;

        Pion (String libelle, Color couleur, Color couleurGrisee, Color couleurSelectionTuileAssechee, Color couleurSelectionTuileInondee, String path) {
            this.libelle = libelle ;
            this.couleur = couleur ;
            this.couleurGrisee = couleurGrisee ;
            this.couleurSelectionTuileAssechee = couleurSelectionTuileAssechee ;
            this.couleurSelectionTuileInondee = couleurSelectionTuileInondee ;
            this.picturePath = path ;
        }

        @Override
        public String toString() {
            return this.libelle ;
        }

        public Color getCouleur() {
            return this.couleur ;
        }

        public Color getCouleurGrisee() {
            return this.couleurGrisee ;
        }

        public Color getCouleurSelectionAssechee() {
            return this.couleurSelectionTuileAssechee ;
        }

        public Color getCouleurSelectionInondee() {
            return this.couleurSelectionTuileInondee ;
        }

        public String getPath() {
            return this.picturePath ;
        }

        public static Pion getFromName(String name) {
            for (Pion pion: Pion.values()) {
                if (pion.name().equals(name)) {
                    return pion;
                }
            }
            return null;
        }


    }

    public static String toRGB(Color couleur) {
        return "#"+Integer.toHexString(couleur.getRGB()).substring(2);
    }

    public static ArrayList<Aventurier> melangerAventuriers(ArrayList<Aventurier> arrayList) {
        if (Parameters.ALEAS) {
            Collections.shuffle(arrayList);
        }
        return arrayList ;
    }

    public static Integer[] melangerPositions(Integer[] tableau) {
        if (Parameters.ALEAS) {
            Collections.shuffle(Arrays.asList(tableau));
        }
        return tableau ;
    }

    /**
     * Permet de poser une question à laquelle l'utilisateur répond par oui ou non
     * @param question texte à afficher
     * @return true si l'utilisateur répond oui, false sinon
     */
    public static Boolean poserQuestion(String question) {
        System.out.println("Divers.poserQuestion(" + question + ")");
        int reponse = JOptionPane.showConfirmDialog (null, question, "", JOptionPane.YES_NO_OPTION) ;
        System.out.println("\tr�ponse : " + (reponse == JOptionPane.YES_OPTION ? "Oui" : "Non"));
        return reponse == JOptionPane.YES_OPTION;
    }

    /**
     * Permet d'afficher un message d'information avec un bouton OK
     * @param message Message à afficher
     */
    public static void afficherInformation(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.OK_OPTION);
    }

    public static void showOnScreen(int screen, JFrame frame) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        if (screen > -1 && screen < gd.length) {
            frame.setLocation(gd[screen].getDefaultConfiguration().getBounds().x, frame.getY());
        } else if (gd.length > 0) {
            frame.setLocation(gd[0].getDefaultConfiguration().getBounds().x, frame.getY());
        } else {
            throw new RuntimeException("No Screens Found");
        }
    }
}
