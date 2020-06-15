package m2104.ile_interdite.vue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.lang.System;

import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.util.Panneau;

public class VueJeu {
    private final IHM ihm;
    private final JFrame fenetre;
    //private final Panneau ecran;
    private final JPanel grillePanel;
    private final Grille grille;
    private final String dossierImage;

    public VueJeu(IHM ihm, Grille grille) {

        if (System.getProperty("user.dir").endsWith("src")) {
            dossierImage = "../images/";
        } else {
            dossierImage = "images/";
        }
        
        this.ihm = ihm;
        this.grille = grille;
        fenetre = new JFrame();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        fenetre.setLocation((int) (dim.getWidth()/2)-500, (int) (dim.getHeight()/2)-300);
        
        fenetre.setSize(1000, 1000);
        
        grillePanel = new Panneau(new ImageIcon(dossierImage + "ocean.jpeg").getImage(), new GridLayout(6, 6));
        //fenetre.add(ecran);

        //grillePanel = new JPanel(new GridLayout(6, 6));

        //tuiles
        JButton button;
        for(int i = 0; i < grille.getTuiles(true).size(); i++) {
            Tuile t = grille.getTuiles(true).get(i);
            button = new JButton();
            //button.setEnabled(false);
            button.setSize(new Dimension(fenetre.getSize().width / 6, fenetre.getSize().height / 6));
            button.setBorder(BorderFactory.createEmptyBorder());
            if (t != null) {
                ImageIcon icon = new ImageIcon(dossierImage + "tuiles/" + t.getNom() + ".png");
                Image img = icon.getImage();
                Image newImg = img.getScaledInstance(button.getWidth(), button.getHeight(), java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);
                button.setIcon(icon);
            } else {
                button.setVisible(false);
            }
            grillePanel.add(button);
        }

        fenetre.add(grillePanel);
        fenetre.setVisible(true);
    }

    public void deplacerPion(Tuile tuileVoulue) {
        //TODO : faire
    }

    public void surbrillerTuile(ArrayList<Boolean> possibilites) {
        // TODO : faire
    }
}
