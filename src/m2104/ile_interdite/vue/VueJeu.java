package m2104.ile_interdite.vue;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.modele.Tuile;

public class VueJeu {
    private final IHM ihm;
    private final JFrame fenetre;
    private final JPanel grillePanel;
    private final Grille grille;

    public VueJeu(IHM ihm, Grille grille) {
        this.ihm = ihm;
        this.grille = grille;

        fenetre = new JFrame();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(800, 600);

        grillePanel = new JPanel(new GridLayout(6, 6));

        //tuiles
        JButton button;
        for(int i = 0; i < grille.getTuiles(true).size(); i++) {
            Tuile t = grille.getTuiles(true).get(i);
            button = new JButton();
            if(t == null)
                button.setEnabled(false);
            else
                button.setText(t.getNom());
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
