package views.base;

import javax.swing.*;
import java.awt.*;

public class PanelNubeHeader extends JPanel {

    public PanelNubeHeader() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // BLOQUE RECTANGULAR PLANO (#FF7B00)
        g2.setColor(new Color(255, 123, 0));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
        super.paintComponent(g);
    }
}