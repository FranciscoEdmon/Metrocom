package views.base;

import javax.swing.*;
import java.awt.*;

public class VentanaBase extends JFrame {

    public VentanaBase(String titulo, int ancho, int alto, int operacionCierre) {
        setTitle(titulo);
        setSize(ancho, alto);
        setDefaultCloseOperation(operacionCierre);
        setLocationRelativeTo(null);
        System.setProperty("awtextra.absoluteLayoutClass", "AbsoluteLayout");
    }

    public static class PanelModerno extends JPanel {
        public PanelModerno(LayoutManager layout) {
            super(layout);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // FONTO PLANO SÓLIDO (Lienzo claro de la paleta #FFE99D)
            g2.setColor(new Color(255, 233, 157));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.dispose();
            super.paintComponent(g);
        }
    }
}