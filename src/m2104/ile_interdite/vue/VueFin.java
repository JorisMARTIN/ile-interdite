package m2104.ile_interdite.vue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class VueFin {
    private final IHM ihm;
    private final JFrame window;
    //private final Panneau grillePanel;
    private final JButton btnQuitter;
    private final JButton btnRejouer;
    private final JLabel textGagne;
    private final JPanel mainPanel;

    public VueFin(IHM ihm) {
        this.ihm = ihm;

        textGagne =  new JLabel();
        btnQuitter = new JButton("Quitter");
        btnRejouer = new JButton("Rejouer");
        mainPanel = new JPanel(new GridLayout(4, 2));

        window = new JFrame("L'île interdite - Fin");

        mainPanel.add(new JLabel("Vous avez quitté l'île interdite."));

        /*Insertion de JLabel vide*/
        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel(""));

        mainPanel.add(btnRejouer);

        btnRejouer.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                desactiver();
                //TODO afficher la VueInscription
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        /* Insertion de JLabel vide */
        mainPanel.add(new JLabel(""));

        btnQuitter.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                desactiver();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        mainPanel.add(btnQuitter);

        window.add(mainPanel);

        window.setVisible(false);
    }

    // ====================================================== Activation de la VueJeu à son tour
    public void activer(boolean b) {
        textGagne.setText((b ? "Bravo vous avez gagné !" : "Dommage, rééssayer pour gagner."));
        this.mainPanel.repaint();
        this.window.setVisible(true);
    }

    public void desactiver() {
        this.window.setVisible(false);
        this.mainPanel.repaint();
    }
    
}