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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
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
    protected final String power ;
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
    private Boolean titreCliquable;
    private boolean cartesActivees;
    
    private JPanel panelCentre, panelCartes, panelText;
    private JLabel actionRestantes;
    private ArrayList<JButton> mainJoueur;
    private JTextArea defausseCarte;

    public VueAventurier(IHM ihm, Integer id, String nomJoueur, String nomAventurier, String power, Integer num, Integer nbAventuriers, Color couleurActive, Color couleurInactive){
        this.ihm = ihm;
        this.idAventurier = id ;
        this.nomJoueur = nomJoueur ;
        this.nomAventurier = nomAventurier ;
        this.couleurActive = couleurActive ;
        this.couleurInactive = couleurInactive ;
        this.power = power ;
        this.couleurActive = couleurActive ;
        this.couleurInactive = couleurInactive ;
        this.titreCliquable = false ;
        this.cartesActivees = false;
        
        this.mainJoueur = new ArrayList<JButton>();
        

        this.window = new JFrame(nomAventurier);
        window.setSize(180, Parameters.HAUTEUR_VUE_AVENTURIER);
        this.window.setUndecorated(Parameters.UNDECORATED);
        this.window.setResizable(Parameters.RESIZABLE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int marginLeft = (dim.width - nbAventuriers * 190) / 2 ;
        window.setLocation(marginLeft + num * 190, Parameters.TOP_VUE_AVENTURIER);

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
                    System.out.println(message);
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
        
        this.panelCartes = new JPanel();
        this.panelText = new JPanel(new BorderLayout());
        
        
        this.panelCentre.add(panelCartes, BorderLayout.NORTH);
        
        this.defausseCarte = new JTextArea("");
        defausseCarte.setEditable(false);
        defausseCarte.setOpaque(false);
        
        this.actionRestantes = new JLabel("");
        
        this.panelText.add(defausseCarte, BorderLayout.CENTER);
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

        this.btnDeplacer = creerBouton(5, "Bouger", Utils.Commandes.DEPLACER, "Déplacer un autre joueur vers une tuile adjacente") ;
        panelBoutons_ligne2.add(btnDeplacer);

        this.btnTerminer = creerBouton(6, "Finir", Utils.Commandes.TERMINER, "Terminer son tour") ;
        panelBoutons_ligne2.add(btnTerminer);

        this.window.setVisible(true);
        mainPanel.repaint();
    }

    public void activerBoutons(Boolean activerMove, Boolean activerDry, Boolean activerDonner, Boolean activerRecuperer, Boolean activerRecevoir, Boolean activerDeplacer, Boolean activerTerminer) {
        if (Parameters.LOGS) {
            System.out.println(this.nomAventurier + " : VueAventurier.activerBoutons(activerMove=" + activerMove + ", activerDry=" + activerDry + ", activerDonner=" + activerDonner + ", activerRecuperer=" + activerRecuperer + ", activerRecevoir=" + activerRecevoir + ", activerTerminer=" + activerTerminer + ")");
        }
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
            if (Parameters.LOGS) {
                System.out.println("VueAventurier_nopic.activerBoutons.activerRecevoir = " + activerRecevoir);
            }
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
        if (Parameters.LOGS) {
            System.out.println("VueAventurier.activer() de " + this.nomAventurier);
        }
        this.labelTitre.setText(getTitle(Color.BLACK, this.couleurActive));
        this.mainPanel.repaint();
    }

    public void desactiver() {
        if (Parameters.LOGS) {
            System.out.println(this.nomAventurier + " : VueAventurier.desactiver()");
        }
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
                        activerBoutons(false, true, true, true, true, true, true);
                        m = new Message(Commandes.LANCER_DEPLACEMENT);
                        m.idAventurier = idAventurier;
                        
                        ihm.notifierObservateurs(m);
                        break;
                        
                    case LANCER_ASSECHEMENT:
                        m = new Message(Commandes.LANCER_ASSECHEMENT);
                        m.idAventurier = idAventurier;
                        
                        ihm.notifierObservateurs(m);
                        break;
                        
                    case DONNER:
                        
                        m = new Message(Commandes.DONNER);
                        m.idAventurier = idAventurier;
                        
                        ihm.notifierObservateurs(m);
                        break;
                        
                    case RECUPERER_TRESOR:
                        
                        m = new Message(Commandes.RECUPERER_TRESOR);
                        m.idAventurier = idAventurier;
                        
                        ihm.notifierObservateurs(m);
                        break;
                        
                    case DEPLACER:
                        
                        m = new Message(Commandes.DEPLACER);
                        m.idAventurier = idAventurier;
                        
                        ihm.notifierObservateurs(m);
                        break;
                        
                    case TERMINER:
                        activerBoutons(false, false, false, false, false, false, false);
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

    public void resetActionRestantes() {
        this.actionRestantes.setText("");
    }
    
    public void setActionRestantes(Integer actionRestantes) {
        this.actionRestantes.setText("Action restantes : " + actionRestantes);
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
            
                
            button = new JButton(carte.toString());
            if(carte instanceof CarteTresor) {
                button.setEnabled(false);
            }
                
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
        }
        
        
        switch(this.mainJoueur.size()) {
        
            case 1:
                this.panelCartes.setLayout(new GridLayout(1,1));
                this.panelCartes.add(this.mainJoueur.get(0));
                break;
                
            case 2:
                
                this.panelCartes.setLayout(new GridLayout(1,2));
                
                this.panelCartes.add(this.mainJoueur.get(0));
                this.panelCartes.add(this.mainJoueur.get(1));
                break;
                
            case 3:
                
                this.panelCartes.setLayout(new GridLayout(1,3));
                
                this.panelCartes.add(this.mainJoueur.get(0));
                this.panelCartes.add(this.mainJoueur.get(1));
                this.panelCartes.add(this.mainJoueur.get(2));
                break;
                
            case 4:
                this.panelCartes.setLayout(new GridLayout(2,2));
                
                this.panelCartes.add(this.mainJoueur.get(0));
                this.panelCartes.add(this.mainJoueur.get(1));
                this.panelCartes.add(this.mainJoueur.get(2));
                this.panelCartes.add(this.mainJoueur.get(3));
                
                break;
                
            case 5:
                this.panelCartes.setLayout(new GridLayout(3,3));
                
                this.panelCartes.add(new JLabel(""));
                this.panelCartes.add(this.mainJoueur.get(0));
                this.panelCartes.add(new JLabel(""));
                this.panelCartes.add(this.mainJoueur.get(1));
                this.panelCartes.add(this.mainJoueur.get(2));
                this.panelCartes.add(this.mainJoueur.get(3));
                this.panelCartes.add(new JLabel(""));
                this.panelCartes.add(this.mainJoueur.get(4));
                this.panelCartes.add(new JLabel(""));
                
                break;
            
            case 6:
                this.panelCartes.setLayout(new GridLayout(2,3));
                
                this.panelCartes.add(this.mainJoueur.get(0));
                this.panelCartes.add(this.mainJoueur.get(1));
                this.panelCartes.add(this.mainJoueur.get(2));
                this.panelCartes.add(this.mainJoueur.get(3));
                this.panelCartes.add(this.mainJoueur.get(4));
                this.panelCartes.add(this.mainJoueur.get(5));
                
                break;
                
            default:
                this.panelCartes.setLayout(new GridLayout(3,3));
                
                for(JButton b : this.mainJoueur) {
                    this.panelCartes.add(b);
                }
                
                break;
        }
        
        this.panelCartes.revalidate();
            
    }
    
    public void deffausseCarte() {
        
        for(JButton b : this.mainJoueur) {
            
            b.setEnabled(true);
             
            for(ActionListener action : b.getActionListeners()) {
                b.removeActionListener(action);
            }
            
            b.addActionListener(new ActionListener() {
             
             @Override
             public void actionPerformed(ActionEvent e) {
                 Message m = new Message(Commandes.DEFAUSSE_CARTE);
                 m.idAventurier = idAventurier;
                 m.idCarte = mainJoueur.indexOf(e.getSource());
                 
                 defausseCarte.setText("");
                 
                 ihm.notifierObservateurs(m);
            }
            });
             
        }
        
        this.defausseCarte.setText("Vous avez trop de carte !\nCliquez sur laquel vous voulez\ndefausser :");
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

}
