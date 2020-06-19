package m2104.ile_interdite.vue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
*/
public class VueFin {
    private final IHM ihm;
    private final JFrame window;
    //private final Panneau grillePanel;
    private final JButton btnQuitter;
    private final JButton btnRejouer;
    private final JLabel textGagne;
    private final JLabel textGagnePartie2;
    private final JPanel topPanel;
    private final JPanel centerPanel;
    private final JPanel bottomPanel;

    public VueFin(IHM ihm) {
        this.ihm = ihm;

        textGagne = new JLabel("");
        textGagnePartie2 = new JLabel("");

        btnQuitter = new JButton("Quitter");
        btnRejouer = new JButton("Rejouer");

        topPanel = new JPanel();
        centerPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel = new JPanel(new GridLayout(1, 3));

        window = new JFrame("L'île interdite - Fin");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 200);

        /* Panel du haut */        
        topPanel.add(new JLabel("Vous avez quitté l'île interdite."));

        window.add(topPanel,BorderLayout.NORTH);

        /*Panel du centre*/
        centerPanel.add(textGagne);
        centerPanel.add(textGagnePartie2);

        window.add(centerPanel, BorderLayout.CENTER);

        /* Panel du bas */
        bottomPanel.add(btnRejouer);

        btnRejouer.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ihm.setVueInscription();
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

        /* Insertion de JLabel vide */
        bottomPanel.add(new JLabel(""));

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

        bottomPanel.add(btnQuitter);

        window.add(bottomPanel, BorderLayout.SOUTH);

        window.setVisible(false);
    }

    // ====================================================== Activation de la VueJeu à son tour
    public void activer(boolean b) {
        if (b) {
            textGagne.setText("Bravo vous avez gagné !");
        } else {
            textGagne.setText("Dommage, vous êtes parti sans avoir tout les trésorts.");
            textGagnePartie2.setText("Rééssayer pour gagner.");
        }
        this.centerPanel.repaint();
        this.window.setVisible(true);
    }

    public void desactiver() {
        this.window.setVisible(false);
        this.centerPanel.repaint();
    }
    
}