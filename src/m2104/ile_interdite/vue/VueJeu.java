package m2104.ile_interdite.vue;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.modele.Tuile;
import m2104.ile_interdite.modele.EtatTuile;
import m2104.ile_interdite.util.Panneau;
import m2104.ile_interdite.util.Parameters;
import m2104.ile_interdite.util.Utils;
import m2104.ile_interdite.util.Message;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
*/
public class VueJeu {
    private final IHM ihm;
    private final JFrame fenetre;
    private  Panneau grillePanel;
    private  Grille grille;
    private ArrayList<JButton> boutons;
    private Image[] imagesBtnNormales;
    private Image[] imagesBtnInondees;
    private int aventurierADeplacer;

    public VueJeu(IHM ihm, Grille grille) {
        
        this.ihm = ihm;
        this.grille = grille;
        fenetre = new JFrame("L'île interdite");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        fenetre.setLocation((int) (dim.getWidth()/2)-500, (int) (dim.getHeight()/2)-300);
        
        fenetre.setSize(800, 800);
        
        grillePanel = new Panneau(new ImageIcon(Parameters.IMAGES + "ocean.jpeg").getImage(), new GridLayout(6, 6));

        ArrayList<Tuile> tuiles = this.grille.getTuiles(true);
        this.imagesBtnNormales = new Image[tuiles.size()];
        this.imagesBtnInondees = new Image[tuiles.size()];

        for (int i = 0; i < tuiles.size(); i++) {
            Tuile t = tuiles.get(i);
            if (t != null) {
                imagesBtnNormales[i] = generateImageBtn(Parameters.TUILES + t.getNom() + ".png");
            }
        }

        for (int i = 0; i < tuiles.size(); i++) {
            Tuile t = tuiles.get(i);
            if (t != null) {
                imagesBtnInondees[i] = generateImageBtn(Parameters.TUILES + t.getNom() + "_Inonde.png");
            }
        }
        this.affGrille();

        fenetre.add(grillePanel);
        
        fenetre.setVisible(true);
    }

    private Image generateImageBtn(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance((int) (fenetre.getSize().width / 6.3), (int) (fenetre.getSize().height / 6.55), Image.SCALE_SMOOTH);
        return img;
    }
    
    public void resetSelections() {
        for (int tuile = 0; tuile < grille.getTuiles(true).size(); tuile++) {
            if (this.boutons.get(tuile) != null && this.boutons.get(tuile).isVisible()) {
                this.boutons.get(tuile).setEnabled(false);
                this.boutons.get(tuile).setBorder(BorderFactory.createEmptyBorder());
            }
        }
    }
    
    public void refresh() {
        grillePanel.revalidate();
        grillePanel.repaint();
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
        } else if (nbPion == 4) {
            position = BorderLayout.SOUTH;
        }
        return position;
    }

    public void surbrillerTuiles(ArrayList<Boolean> possibilites, Utils.Pion pion, int action, int idAventurier) {
        aventurierADeplacer = idAventurier;
        for (int tuile = 0; tuile < grille.getTuiles(true).size(); tuile++) {
            JButton bouton = this.boutons.get(tuile);
            bouton.setEnabled(false);
            bouton.setBorder(BorderFactory.createEmptyBorder());
            if (possibilites.get(tuile)) {
                bouton.setEnabled(true);
                Border borderUp = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK);
                Border borderDown = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.BLACK);
                Border coloredBorder = BorderFactory.createLineBorder(pion.getCouleur(), 2);
                Border compound1 = BorderFactory.createCompoundBorder(borderUp, coloredBorder);
                Border compound2 = BorderFactory.createCompoundBorder(coloredBorder, borderDown);
                Border bigCompound = BorderFactory.createCompoundBorder(compound1, compound2);
                bouton.setBorder(bigCompound);
                bouton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Message m = new Message(Utils.Commandes.DEPLACER);
                        m.nomTuile = bouton.getText();
                        m.idAventurier = aventurierADeplacer;
                        ihm.notifierObservateurs(m);
                    }
                });
                
            }
        }
    }
    
    public void affGrille() {
        grillePanel.removeAll();
        //tuiles
        JButton button;
        boutons = new ArrayList<JButton>();
        for(int i = 0; i < grille.getTuiles(true).size(); i++) {
            Tuile t = grille.getTuiles(true).get(i);
            button = new JButton();
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setEnabled(false);
            if (t != null && t.getEtat() != EtatTuile.RETIREE) {
                Image img = null;
                if (t.getEtat() == EtatTuile.NORMAL) {
                    img = imagesBtnNormales[i];
                } else {
                    img = imagesBtnInondees[i];
                }
                button.setIcon(new ImageIcon(img));
                button.setDisabledIcon(new ImageIcon(img));
                button.setText(t.getNom());
                
                JLabel labelPion;
                int nbPion = t.getAventuriers().size();
                if (nbPion == 4) {
                    button.setLayout(new GridLayout(2, 2));
                } else {
                    button.setLayout(new BorderLayout());
                }
                for (int pion = 0; pion < nbPion; pion++) {
                    ImageIcon icon = new ImageIcon(Parameters.PIONS + "pion" + t.getAventuriers().get(pion).getPion() + ".png");
                    Image imgPion = icon.getImage().getScaledInstance(fenetre.getSize().width / (8 + 2 * nbPion), fenetre.getSize().height / (8 + 2 * nbPion), Image.SCALE_SMOOTH);
                    labelPion = new JLabel(new ImageIcon(imgPion));
                    if (nbPion == 4) {
                        button.add(labelPion);
                    } else {
                        button.add(labelPion, getPositionPion(t.getAventuriers().size(), pion));
                    }
                }
            } else {
                button.setVisible(false);
            }
            grillePanel.add(button);
            boutons.add(button);
        }
    }
}
