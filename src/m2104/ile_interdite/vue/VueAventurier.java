package m2104.ile_interdite.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;
import m2104.ile_interdite.util.Parameters;
import m2104.ile_interdite.util.Utils;
import m2104.ile_interdite.util.Utils.Commandes;
import m2104.ile_interdite.cartes.Carte;
import m2104.ile_interdite.cartes.CarteTresor;
import m2104.ile_interdite.util.Message;

/**
*
* @author Thomas JHISTARRY
* @author Joris MARTIN
* @author Tanguy SIGNORET
* @author Mattéo PAPPALARDO
*/
public class VueAventurier {
    private final IHM ihm;
    protected final Integer idAventurier ;
    protected final String nomAventurier ;
    protected final String nomJoueur ;
    protected Color couleurActive ;
    protected Color couleurInactive ;
    private JButton btnAller = null ;
    private JPanel panelBoutons = null;
    private final JFrame window;
    private final JEditorPane labelTitre;
    private final JPanel mainPanel;
    private final JButton btnAssecher;
    private final JButton btnDonner;
    private final JButton btnPrendre;
    private final JButton btnDeplacer;
    private final JButton btnTerminer;
    private boolean titreCliquable;
    
    private JPanel panelCentre, panelCartes, panelText;
    private JLabel actionRestantes;
    private ArrayList<JButton> mainJoueur;
    private JTextArea description;

    public VueAventurier(IHM ihm, Integer id, String nomJoueur, String nomAventurier, Integer num, Integer nbAventuriers, Color couleurActive, Color couleurInactive) {
        this.ihm = ihm;
        this.idAventurier = id ;
        this.nomJoueur = nomJoueur ;
        this.nomAventurier = nomAventurier ;
        this.couleurActive = couleurActive ;
        this.couleurInactive = couleurInactive ;
        this.couleurActive = couleurActive ;
        this.couleurInactive = couleurInactive ;
        this.titreCliquable = false ;
        
        this.mainJoueur = new ArrayList<JButton>();
        

        this.window = new JFrame(nomAventurier);
        //window.setSize(180, Parameters.HAUTEUR_VUE_AVENTURIER);
        window.setSize(375, 400);
        this.window.setUndecorated(Parameters.UNDECORATED);
        this.window.setResizable(Parameters.RESIZABLE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        //int marginLeft = (dim.width - nbAventuriers * 190) / 2;
        
        int marginLeft = 164;
        int marginTop = (dim.height / nbAventuriers) - ((nbAventuriers == 2) ? 215 : 150);
        
        //window.setLocation(marginLeft + num * 190, Parameters.TOP_VUE_AVENTURIER);
        
        switch (num) {
        case 0:
                window.setLocation(marginLeft, marginTop);
            break;
            
        case 1:
                window.setLocation(marginLeft + 435, marginTop);
            break;
                
        case 2:
                window.setLocation(marginLeft, marginTop + 415);
            break;
            
        case 3:
                window.setLocation(marginLeft + 435, marginTop + 415);
            break;
        }
        

        mainPanel = new JPanel(new BorderLayout());
        this.window.add(mainPanel);

        mainPanel.setBackground(new Color(230, 230, 230));
        mainPanel.setBorder(BorderFactory.createLineBorder(this.couleurActive, 2, false)) ;

        // =================================================================================
        // NORD : le titre = nom de l'aventurier + nom du joueur sur la couleurActive du pion

        this.labelTitre = new JEditorPane();
        this.labelTitre.setContentType("text/html");
        this.labelTitre.setText(getTitle(Color.BLACK, this.couleurInactive));
        this.labelTitre.setOpaque(false);
        this.labelTitre.setEditable(false);
        this.labelTitre.setBorder(new MatteBorder(0, 0, 2, 0, this.couleurActive));
        mainPanel.add(this.labelTitre, BorderLayout.NORTH);
        labelTitre.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (titreCliquable) {
                    Message message = new Message(Commandes.RECEVOIR);
                    
                    message.idAventurier = idAventurier;
                    
                    ihm.notifierObservateurs(message);
                }
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
        
        
        // =================================================================================
        // Centre : 
        
        this.panelCentre = new JPanel();
        this.panelCentre.setLayout(new BorderLayout());
        
        this.panelCartes = new JPanel(new GridLayout(0, 4));
        
        this.panelText = new JPanel(new BorderLayout());
        
        this.panelCentre.add(panelCartes, BorderLayout.NORTH);
        
        this.description = new JTextArea("");
        description.setEditable(false);
        description.setOpaque(false);
        
        this.actionRestantes = new JLabel("");
        
        this.panelText.add(description, BorderLayout.NORTH);
        this.panelText.add(actionRestantes, BorderLayout.SOUTH);
        
        this.panelCentre.add(panelText);
        
        this.mainPanel.add(panelCentre, BorderLayout.CENTER);

        
        // =================================================================================
        // SUD : les boutons
        this.panelBoutons = new JPanel(new GridLayout(2,1));
        this.panelBoutons.setOpaque(false);
        mainPanel.add(this.panelBoutons, BorderLayout.SOUTH);

        JPanel panelBoutons_ligne1 = new JPanel(new GridLayout(1, 3));
        panelBoutons_ligne1.setOpaque(false);
        panelBoutons.add(panelBoutons_ligne1);

        this.btnAller = creerBouton(1, "Aller", Utils.Commandes.LANCER_DEPLACEMENT, "Se déplacer vers une tuile") ;
        panelBoutons_ligne1.add(btnAller);

        this.btnAssecher = creerBouton(2, "Sécher", Utils.Commandes.LANCER_ASSECHEMENT, "Assécher une tuile") ;
        panelBoutons_ligne1.add(btnAssecher);

        this.btnDonner = creerBouton(3, "Donner", Utils.Commandes.DONNER, "Donner une carte à un autre joueur") ;
        panelBoutons_ligne1.add(btnDonner);

        JPanel panelBoutons_ligne2 = new JPanel(new GridLayout(1, 3));
        panelBoutons_ligne2.setOpaque(false);
        panelBoutons.add(panelBoutons_ligne2);

        this.btnPrendre = creerBouton(4, "Prendre", Utils.Commandes.RECUPERER_TRESOR, "Récupérer le trésor de la tuile courante") ;
        panelBoutons_ligne2.add(btnPrendre);

        this.btnDeplacer = creerBouton(5, "Bouger", Utils.Commandes.BOUGER, "Déplacer un autre joueur vers une tuile adjacente") ;
        panelBoutons_ligne2.add(btnDeplacer);

        this.btnTerminer = creerBouton(6, "Finir", Utils.Commandes.TERMINER, "Terminer son tour") ;
        panelBoutons_ligne2.add(btnTerminer);

        this.window.setVisible(true);
        mainPanel.repaint();
    }
    
    /**
     * 
     * @param activerMove
     * @param activerDry
     * @param activerDonner
     * @param activerRecuperer
     * @param activerRecevoir
     * @param activerDeplacer
     * @param activerTerminer
     */
    public void activerBoutons(Boolean activerMove, Boolean activerDry, Boolean activerDonner, Boolean activerRecuperer, Boolean activerRecevoir, Boolean activerDeplacer, Boolean activerTerminer) {

        if (activerMove != null) {
            btnAller.setEnabled(activerMove);
            if (!activerMove) {
                btnAller.setForeground(Color.BLACK);
            }
        }

        if (activerDry != null) {
            btnAssecher.setEnabled(activerDry);
            if (!activerDry) {
                btnAssecher.setForeground(Color.BLACK);
            }
        }

        if (activerDonner != null) {
            btnDonner.setEnabled(activerDonner);
            if (!activerDonner) {
                btnDonner.setForeground(Color.BLACK);
            }
        }

        if (activerRecuperer != null) {
            btnPrendre.setEnabled(activerRecuperer);
            if (!activerRecuperer) {
                btnPrendre.setForeground(Color.BLACK);
            }
        }

        if (activerRecevoir != null) {
            this.titreCliquable = activerRecevoir ;
            labelTitre.setCursor(new Cursor((activerRecevoir ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR)));
            labelTitre.setToolTipText(activerRecevoir ? "Recevoir la carte d'un autre joueur" : "");
        }

        if (activerDeplacer != null) {
            btnDeplacer.setEnabled(activerDeplacer);
            if (!activerDeplacer) {
                btnDeplacer.setForeground(Color.BLACK);
            }
        }

        if (activerTerminer != null) {
            btnTerminer.setEnabled(activerTerminer);
            if (!activerTerminer) {
                btnTerminer.setForeground(Color.BLACK);
            }
        }
    }

    // ====================================================== Activation de la carte à son tour
    public void activer() {
        this.labelTitre.setText(getTitle(Color.BLACK, this.couleurActive));
        this.mainPanel.repaint();
    }

    public void desactiver() {
        this.labelTitre.setText(getTitle(Color.WHITE, this.couleurInactive));
        this.mainPanel.repaint();
    }

    // ====================================================== Getters et Setters

    private JButton creerBouton(Integer numBouton, String libelle, Utils.Commandes commande, String tooltip) {
        JButton bouton = new JButton();
        bouton.setOpaque(false);
        bouton.setEnabled(false);
        bouton.setToolTipText(tooltip);
        bouton.setText(libelle);
        bouton.setFont(bouton.getFont().deriveFont(Font.PLAIN));
        bouton.setBorder(new MatteBorder( 0, 0, (numBouton <= 3 ? 1 : 0), (numBouton%3!=0 ? 1 : 0), Color.GRAY));

        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JButton btnClique = (JButton) e.getSource();
                btnAller.setForeground(Color.BLACK);
                btnAssecher.setForeground(Color.BLACK);
                btnDonner.setForeground(Color.BLACK);
                btnPrendre.setForeground(Color.BLACK);

                if (btnClique == btnAller || btnClique == btnAssecher || btnClique == btnDonner || btnClique == btnPrendre) {
                    btnClique.setForeground(couleurActive);
                }
                
                Message m;

                switch (commande) {
                    case LANCER_DEPLACEMENT:
                        ihm.activerActionsTous(false, false, false, false, false, false, false);
                        activerBoutons(false, true, true, true, false, nomAventurier == "Navigateur", true);
                        m = new Message(Commandes.LANCER_DEPLACEMENT);
                        m.idAventurier = idAventurier;
                        
                        ihm.notifierObservateurs(m);
                        break;
                        
                    case LANCER_ASSECHEMENT:
                        ihm.activerActionsTous(false, false, false, false, false, false, false);
                        activerBoutons(true, false, true, true, false, nomAventurier == "Navigateur", true);
                        setDescription("");
                        m = new Message(Commandes.LANCER_ASSECHEMENT);
                        m.idAventurier = idAventurier;
                        
                        ihm.notifierObservateurs(m);
                        break;
                        
                    case DONNER: 
                        
                        ihm.activerActionsTous(false, false, false, false, false, false, false);
                        activerBoutons(true, true, false, true, false, nomAventurier == "Navigateur", true);
                        setDescription("");
                        
                        ihm.majVueJeu();
                        
                        donnerCarte();
                        break;
                        
                    case RECUPERER_TRESOR:
                        ihm.activerActionsTous(false, false, false, false, false, false, false);
                        activerBoutons(true, true, true, false, false, nomAventurier == "Navigateur", true);
                        setDescription("");
                        m = new Message(Commandes.RECUPERER_TRESOR);
                        m.idAventurier = idAventurier;
                        
                        ihm.notifierObservateurs(m);
                        break;
                        
                    case BOUGER:
                        if(nomAventurier == "Navigateur") {
                            activerBoutons(true, true, true, true, false, false, true);
                            m = new Message(Commandes.BOUGER);
                            m.idAventurier = idAventurier;

                            ihm.notifierObservateurs(m);
                        } else {
                            m = new Message(Commandes.LANCER_PVNAVIGATEUR);
                            m.idAventurier = idAventurier;

                            ihm.notifierObservateurs(m);
                        }
                        break;
                        
                    case TERMINER:
                        ihm.activerActionsTous(false, false, false, false, false, false, false);
                        setDescription("");
                        m = new Message(Commandes.TERMINER);
                        m.idAventurier = idAventurier;
                        
                        ihm.notifierObservateurs(m);
                        break;
                        
                    default:
                        break;
                }
                
            }
        });
        
        bouton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setFont(btn.getFont().deriveFont(Font.BOLD));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setFont(btn.getFont().deriveFont(Font.PLAIN));
            }
        });
        
        
        
        return bouton;
    }


    // ====================================================== Utils
    public String getTitle(Color color, Color backgroundColor) {
        return "<html>" +
                    "<h1 style=\"text-align:center; margin : 0 ; color:" + Utils.toRGB(color) + "; " +
                            (backgroundColor == null ? "" :  "background-color:" + Utils.toRGB(backgroundColor) + "; ") +
                            "font-size:120%;\">" +
                        this.nomAventurier +
                    "</h1>" +
                    "<h2 style=\"text-align:center; margin : 0 ; color:" + Utils.toRGB(color) + "; " +
                            (backgroundColor == null ? "" :  "background-color:" + Utils.toRGB(backgroundColor) + "; ") +
                            "font-size:90%;\">" +
                        this.nomJoueur +
                    "</h2>" +
                "</html>" ;
    }

    // ====================================================== Getters et Setters
    public Integer getIdAventurier() {
        return this.idAventurier ;
    }
    
    public String getNomAventurier() {
        return this.nomAventurier ;
    }

    public void resetActionRestantes() {
        this.actionRestantes.setText("");
    }
    
    public void setActionRestantes(Integer actionRestantes) {
        this.actionRestantes.setText("Actions restantes : " + actionRestantes);
    }

    /**
     * 
     * @param main : les cartes en main du joueur
     * @param tropCarte : si le joueur dois deffauser une carte
     * 
     */
    public void actualiserMain(ArrayList<Carte> main) {
    
        this.panelCartes.removeAll();
        this.mainJoueur.clear();
        
        JButton button;
        
        for(Carte carte : main) {
            
                
            button = new JButton();
            if(carte instanceof CarteTresor) {
                button.setEnabled(false);
            }
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            if (button.isEnabled()) {
                button.setBorder(BorderFactory.createLineBorder(this.couleurActive, 2, false));
            } else {
                button.setBorder(null);
            }
            button.setToolTipText(carte.toString());

            
            String path = Parameters.CARTES + carte.toString() + ".png";
            ImageIcon icon = new ImageIcon(path);
            float scale = (int) Math.max(icon.getIconWidth(), icon.getIconHeight());
            int largeur = (int) (icon.getIconWidth() / scale * mainPanel.getSize().width / 3);
            int hauteur = (int) (icon.getIconHeight() / scale * mainPanel.getSize().height / 3);
            Image img = icon.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
            button.setDisabledIcon(new ImageIcon(img));
            
            button.addActionListener(new ActionListener() {
                
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    Message m = new Message(Commandes.JOUE_CARTE);
                    m.idAventurier = idAventurier;
                    m.idCarte = mainJoueur.indexOf(e.getSource());
                    
                    ihm.notifierObservateurs(m);
                }
            });
            
            this.mainJoueur.add(button);
            this.panelCartes.add(button);
        }
        
        this.panelCartes.revalidate();
        this.panelCartes.repaint(); 
    }
    
    public void deffausseCarte() {
        
        for(JButton b : this.mainJoueur) {
            
            b.setEnabled(true);
            b.setBorder(BorderFactory.createLineBorder(this.couleurActive, 2, false));
             
            for(ActionListener action : b.getActionListeners()) {
                b.removeActionListener(action);
            }
            
            b.addActionListener(new ActionListener() {
             
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message(Commandes.DEFAUSSE_CARTE);
                m.idAventurier = idAventurier;
                m.idCarte = mainJoueur.indexOf(e.getSource());
                 
                description.setText("");
                 
                ihm.notifierObservateurs(m);
            }
            });
             
        }
        
        this.description.setText("Vous avez trop de carte !\nCliquez sur celle vous voulez défausser :");
    }
    
    
    public void donnerCarte() {
        
        for(JButton b : this.mainJoueur) {
            
            b.setEnabled(true);
             
            for(ActionListener action : b.getActionListeners()) {
                b.removeActionListener(action);
            }
            
            b.addActionListener(new ActionListener() {
             
            @Override
            public void actionPerformed(ActionEvent e) {
                ihm.setIdCarteADonner(mainJoueur.indexOf(e.getSource()));
                
                ihm.activerActionsTous(false, false, false, false, true, false, false);
                activerBoutons(false, false, false, false, false, false, true);
                 
                description.setText("Cliquez maintenant sur\nle joueur é qui vous\nvoulez donner la carte :");
            }
            });
             
        }
        
        this.description.setText("Cliquez sur la carte carte\nque vous voulez\ndonner :");
        
    }
    
    /**
     * 
     * @param b : true pour activer, false pour desactiver
     */
    public void setEtatBoutonsCartes(boolean b) {
        for(JButton button : getMainJoueur()) {
            button.setEnabled(b);
        }
    }

    public ArrayList<JButton> getMainJoueur() {
        return this.mainJoueur;
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }
    
    public void setVisible(boolean b){
        this.window.setVisible(b);
    }

}
