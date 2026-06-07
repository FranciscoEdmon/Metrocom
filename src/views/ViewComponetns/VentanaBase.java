package views.ViewComponetns;
import java.awt.*;
import javax.swing.*;

public class VentanaBase extends JFrame{
    public VentanaBase(String title, int alto, int ancho, int OperacionCierre){
        setTitle(title);
        setSize(alto,ancho);
        setDefaultCloseOperation(OperacionCierre);

        getContentPane().setBackground(new Color(0x00005E));
    }
}
