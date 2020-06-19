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
    private final JPanel mainPanel;
    private final JPanel topPanel;

    public VueFin(IHM ihm) {
        this.ihm = ihm;

        textGagne =  new JLabel();
        btnQuitter = new JButton("Quitter");
        btnRejouer = new JButton("Rejouer");
        topPanel = new JPanel();
        mainPanel = new JPanel(new GridLayout(3, 2));

        window = new JFrame("L'île interdite - Fin");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 200);
        
        topPanel.add(new JLabel("Vous avez quitté l'île interdite."));

        window.add(topPanel,BorderLayout.NORTH);
        
        /*Insertion de JLabel vide*/
        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel(""));
        // Pourquoi pas insérer les statistiques de la partie
        //TODO insérer les statistiques de la partie

        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel(""));

        mainPanel.add(btnRejouer);

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

        window.add(mainPanel, BorderLayout.SOUTH);

        window.setVisible(false);
    }

    // ====================================================== Activation de la VueJeu à son tour
    public void activer(boolean b) {
        textGagne.setText((b ? "Bravo vous avez gagné !" : "Dommage, vous êtes parti sans avoir tou les trésorts.\nRééssayer pour gagner."));
        this.mainPanel.repaint();
        this.window.setVisible(true);
    }

    public void desactiver() {
        this.window.setVisible(false);
        this.mainPanel.repaint();
    }
    
}