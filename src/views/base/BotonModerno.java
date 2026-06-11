package views.base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonModerno extends JButton {
    private Color colorFondo = new Color(255, 165, 4);     // #FFA504 (Naranja Base)
    private Color colorHover = new Color(255, 188, 55);    // #FFBC37 (Naranja Claro)
    private Color colorClick = new Color(255, 123, 0);     // #FF7B00 (Naranja Oscuro)
    private Color colorTexto = new Color(40, 20, 0);       // Marrón muy oscuro para alto contraste
    private boolean mouseOver = false;

    public BotonModerno(String texto) {
        super(texto);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(colorTexto);
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { mouseOver = true; repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { mouseOver = false; repaint(); }
        });
    }

    public BotonModerno(String texto, Color fondoPersonalizado, Color textoPersonalizado) {
        this(texto);
        this.colorFondo = fondoPersonalizado;
        this.colorHover = fondoPersonalizado.brighter();
        this.colorClick = fondoPersonalizado.darker();
        this.colorTexto = textoPersonalizado;
        setForeground(colorTexto);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // PINTADO TOTALMENTE PLANO PERO CON ESQUINAS REDONDEADAS
        if (getModel().isPressed()) {
            g2.setColor(colorClick);
        } else if (mouseOver) {
            g2.setColor(colorHover);
        } else {
            g2.setColor(colorFondo);
        }

        // Cambiado de fillRect a fillRoundRect (arco de 12 píxeles)
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

        g2.dispose();
        super.paintComponent(g);
    }
}