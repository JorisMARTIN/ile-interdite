package m2104.ile_interdite.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Utils.Commandes;

/**
 *
 * @author Yann Laurillau <yann.laurillau@iut2.univ-grenoble-alpes.fr>* 
 * @author Thomas JHISTARRY
 * @author Joris MARTIN
 * @author Tanguy SIGNORET
 * @author Mattéo PAPPALARDO
 */
public class VueInscriptionJoueurs {
    private final IHM ihm;
    private final JFrame fenetre;

    private JComboBox<Integer> choixNbJoueurs, curseur;
    private JLabel [] labelNomJoueurs = new JLabel[4];
    private JTextField [] saisieNomJoueurs = new JTextField[4];
    private final JButton inscrire = new JButton("Jouer");

    private String[] nomJoueurs;

    public VueInscriptionJoueurs(IHM ihm) {
        this.ihm = ihm;

        fenetre = new JFrame("L'île interdite - Inscription");
        fenetre.setLayout(new BorderLayout());
        fenetre.setResizable(false);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(400, 200);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panelChoix = new JPanel(new GridLayout(6,2));
        JPanel panelDifficulte = new JPanel(new GridLayout(1,2));

        
        // Choix difficult�
        curseur = new JComboBox<Integer>(new Integer[] {1, 2, 3, 4});
        panelDifficulte.add(new JLabel("Difficulte :"));
        panelDifficulte.add(curseur);
        
        mainPanel.add(panelDifficulte, BorderLayout.NORTH);
        fenetre.add(mainPanel);
        
        
        // nombre de joueurs
        choixNbJoueurs = new JComboBox<>(new Integer[] { 2, 3, 4 });
        panelChoix.add(new JLabel("Nombre de joueurs :"));
        panelChoix.add(choixNbJoueurs);

        // Saisie des noms de joueurs
        for(int i = 0; i < saisieNomJoueurs.length; i++) {
            saisieNomJoueurs[i] = new JTextField();
            labelNomJoueurs[i] = new JLabel("Nom du joueur No " + (i + 1) + " :");
            panelChoix.add(labelNomJoueurs[i]);
            panelChoix.add(saisieNomJoueurs[i]);
            labelNomJoueurs[i].setEnabled(i < 2);
            saisieNomJoueurs[i].setEnabled(i < 2);
        }

        panelChoix.add(new JPanel()); // Une case vide
        panelChoix.add(inscrire);

        mainPanel.add(panelChoix, BorderLayout.CENTER);

        // Choix du nombre de joueurs
        choixNbJoueurs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int nb = (Integer) choixNbJoueurs.getSelectedItem();

                for(int i = 0; i < saisieNomJoueurs.length; i++) {
                    labelNomJoueurs[i].setEnabled(i < nb);
                    saisieNomJoueurs[i].setEnabled(i < nb);
                }
            }
        });
        

        // Inscription des joueurs
        inscrire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remplissage du tableau contenant le nom des joueurs
                int nbJoueurs = (int) choixNbJoueurs.getSelectedItem();

                nomJoueurs = new String[nbJoueurs];
                for (int i = 0; i < nbJoueurs; ++i) {
                    nomJoueurs[i] = saisieNomJoueurs[i].getText();
                }

                Message m = new Message(Commandes.INITIALISER);
                m.nbJoueurs = nbJoueurs;
                m.difficulte = (int) curseur.getSelectedItem();
                ihm.notifierObservateurs(m);
                fenetre.dispose();
            }
        });

        fenetre.setVisible(true);
    }

    public String[] getNomJoueurs() {
        return Arrays.copyOf(this.nomJoueurs, this.nomJoueurs.length);
    }

}
