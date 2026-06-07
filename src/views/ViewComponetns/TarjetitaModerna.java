package views.ViewComponetns;

import javax.swing.*;
import java.awt.*;

public class TarjetitaModerna extends JPanel{
    public TarjetitaModerna(LayoutManager layout){
        super(layout);
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0x090384));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
        g2.dispose();
    }
}
