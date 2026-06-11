package views.base;

import javax.swing.*;
import java.awt.*;

public class TarjetaModerna extends JPanel {
    public TarjetaModerna(LayoutManager layout) {
        super(layout);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // FONDO PLANO DE LA TARJETA (#FFD26A) - Ahora con esquinas redondeadas
        g2.setColor(new Color(255, 210, 106));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // BORDE PLANO PERMANENTE (#FFA504) - Ahora redondeado con grosor de 2px
        g2.setColor(new Color(255, 165, 4));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);

        g2.dispose();
        super.paintComponent(g);
    }
}