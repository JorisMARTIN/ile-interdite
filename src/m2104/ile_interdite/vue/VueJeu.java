package m2104.ile_interdite.vue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;

import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.aventuriers.Aventurier;
import m2104.ile_interdite.util.Panneau;
import m2104.ile_interdite.util.Parameters;
import m2104.ile_interdite.util.Utils;

public class VueJeu {
    private final IHM ihm;
    private final JFrame fenetre;
    private final Panneau grillePanel;
    private final Grille grille;
    private ArrayList<JButton> boutons;

    public VueJeu(IHM ihm, Grille grille) {
        
        this.ihm = ihm;
        this.grille = grille;
        fenetre = new JFrame("L'île interdite");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        fenetre.setLocation((int) (dim.getWidth()/2)-500, (int) (dim.getHeight()/2)-300);
        
        fenetre.setSize(800, 800);
        
        grillePanel = new Panneau(new ImageIcon(Parameters.IMAGES + "ocean.jpeg").getImage(), new GridLayout(6, 6));

        //tuiles
        JButton button;
        boutons = new ArrayList<JButton>();
        for(int i = 0; i < grille.getTuiles(true).size(); i++) {
            Tuile t = grille.getTuiles(true).get(i);
            button = new JButton();
            button.setLayout(new BorderLayout());
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            
            if (t != null) {
                ImageIcon icon;
                icon = new ImageIcon(Parameters.TUILES + t.getNom() + ".png");
                Image img = icon.getImage().getScaledInstance((int) (fenetre.getSize().width / 6.3), (int) (fenetre.getSize().height / 6.55), Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(img));
                button.setDisabledIcon(new ImageIcon(img));
                button.setEnabled(false);
                
                JLabel labelPion;
                int nbPion = t.getAventuriers().size();
                for (int pion = 0; pion < nbPion; pion++) {
                    icon = new ImageIcon(Parameters.PIONS + "pion" + t.getAventuriers().get(pion).getPion() + ".png");
                    img = icon.getImage().getScaledInstance(fenetre.getSize().width / (8 + 2 * nbPion), fenetre.getSize().height / (8 + 2 * nbPion), Image.SCALE_SMOOTH);
                    labelPion = new JLabel(new ImageIcon(img));
                    button.add(labelPion, getPositionPion(t.getAventuriers().size(), pion));
                }
            } else {
                button.setVisible(false);
            }
            grillePanel.add(button);
            boutons.add(button);
        }

        fenetre.add(grillePanel);
        fenetre.setVisible(true);
    }
    
    private String getPositionPion(int nbPion, int numeroPion) {
        String position = "";
        if (nbPion == 1) {
            position = BorderLayout.CENTER;
        } else if (numeroPion == 0) {
            position = BorderLayout.EAST;
        } else if (numeroPion == 1) {
            position = BorderLayout.WEST;
        } else if (numeroPion == 2) {
            position = BorderLayout.NORTH;
        } else if (numeroPion == 3) {
            position = BorderLayout.SOUTH;
        }
        return position;
    }

    public void deplacerPion(Tuile tuileVoulue) {
        //TODO : faire
    }

    public void surbrillerTuiles(ArrayList<Boolean> possibilites, Utils.Pion pion) {
        for (int tuile = 0; tuile < grille.getTuiles(true).size(); tuile++) {
            System.out.println(possibilites.get(tuile));
            if (possibilites.get(tuile)) {
                this.boutons.get(tuile).setEnabled(true);
                this.boutons.get(tuile).setBorder(BorderFactory.createLineBorder(pion.getCouleurSelectionAssechee(), 6));
            }
        }
    }
}
