package m2104.ile_interdite.util;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.GridLayout;

public class Panneau extends JPanel {
    private Image image;
    
    public Panneau(Image image, GridLayout layout) {
        super(layout);
        this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
    }
}