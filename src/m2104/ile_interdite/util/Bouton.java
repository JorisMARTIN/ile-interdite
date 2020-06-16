package m2104.ile_interdite.util;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Image;

public class Bouton extends JButton {
    private Image image;
    
    public Bouton(Image image, GridLayout layout) {
        super(layout);
        this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this);
    }
}