package m2104.ile_interdite.vue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class VueJeu {
    private final IHM ihm;
    private final JFrame fenetre;

    public VueJeu(IHM ihm) {
        this.ihm = ihm;

        fenetre = new JFrame();
        fenetre.setLayout(new GridLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(400, 200);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panelChoix = new JPanel(new GridLayout(6,2));
        JPanel panelDifficulte = new JPanel(new GridLayout(1,2));

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
        
        
        
        curseur = new JComboBox<Integer>(new Integer[] {1, 2, 3, 4});
        panelDifficulte.add(new JLabel("Difficulte :"));
        panelDifficulte.add(curseur);
        
        mainPanel.add(panelDifficulte, BorderLayout.SOUTH);
        fenetre.add(mainPanel);
        

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
