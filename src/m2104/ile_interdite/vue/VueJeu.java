package m2104.ile_interdite.vue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;

import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.util.Panneau;
import m2104.ile_interdite.util.Parameters;

public class VueJeu {
    private final IHM ihm;
    private final JFrame fenetre;
    private final Panneau grillePanel;
    private final Grille grille;

    public VueJeu(IHM ihm, Grille grille) {
        
        this.ihm = ihm;
        this.grille = grille;
        fenetre = new JFrame("L'île interdite");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        fenetre.setLocation((int) (dim.getWidth()/2)-500, (int) (dim.getHeight()/2)-300);
        
        fenetre.setSize(1000, 1000);
        
        grillePanel = new Panneau(new ImageIcon(Parameters.IMAGES + "ocean.jpeg").getImage(), new GridLayout(6, 6));

        //tuiles
        JButton button;
        for(int i = 0; i < grille.getTuiles(true).size(); i++) {
            Tuile t = grille.getTuiles(true).get(i);
            button = new JButton();
            button.setLayout(new BorderLayout());
            button.setBorder(BorderFactory.createEmptyBorder());
            if (t != null) {
                ImageIcon icon;
                icon = new ImageIcon(Parameters.TUILES + t.getNom() + ".png");
                Image img = icon.getImage().getScaledInstance(fenetre.getSize().width / 6, fenetre.getSize().height / 6, Image.SCALE_SMOOTH);
                //JLabel labelTuile = new JLabel(new ImageIcon(img));
                //button.add(labelTuile);
                button.setIcon(new ImageIcon(img));
                if (!t.getAventuriers().isEmpty()) {
                    icon = new ImageIcon(Parameters.PIONS + "pion" + t.getAventuriers().get(0).getPion() + ".png");
                    img = icon.getImage().getScaledInstance(fenetre.getSize().width / 10, fenetre.getSize().height / 10, Image.SCALE_SMOOTH);
                    JLabel labelPion = new JLabel(new ImageIcon(img));
                    button.add(labelPion, BorderLayout.CENTER);
                } else {
                    icon = new ImageIcon(Parameters.TUILES + t.getNom() + ".png");
                }
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
