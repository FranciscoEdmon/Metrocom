package views.ViewComponetns;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonModerno extends JButton {
    // Definición de la paleta de colores para los estados del botón
    private final Color colorReposo = new Color(0x1107A9);
    private final Color colorHover = new Color(0x1A0ACF);
    private final Color colorPresionado = new Color(0x220DF5);
    private Color colorActual;

    public BotonModerno(String texto) {
        super(texto);
        this.colorActual = colorReposo;

        // Remueve la apariencia nativa y rígida de Swing
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        // Tipografía y estilo del texto
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Gestión de eventos del mouse para transiciones de color
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                colorActual = colorHover;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                colorActual = colorReposo;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                colorActual = colorPresionado;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (getBounds().contains(e.getPoint())) {
                    colorActual = colorHover;
                } else {
                    colorActual = colorReposo;
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        // 1. Dibuja un efecto de sombra difuminada en la base (Efecto flotante)
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRoundRect(2, 3, ancho - 4, alto - 3, 12, 12);

        // 2. Dibuja el cuerpo principal del botón con el color del estado activo
        g2.setColor(colorActual);
        g2.fillRoundRect(0, 0, ancho - 2, alto - 2, 12, 12);

        super.paintComponent(g);
        g2.dispose();
    }
}