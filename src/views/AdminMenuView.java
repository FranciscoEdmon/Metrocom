package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminMenuView extends JFrame {
    private JButton btnGestionUsuarios;
    private JButton btnGestionInfraestructura;
    private JButton btnCerrarSesion;

    public AdminMenuView() {
        setTitle("MetroCom - Panel de Administración");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblBienvenida = new JLabel("Bienvenido al Panel de Control", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblBienvenida);

        btnGestionUsuarios = new JButton("Gestión de Personal (Jefes / Gerentes)");
        panel.add(btnGestionUsuarios);

        btnGestionInfraestructura = new JButton("Gestión de Infraestructura (Líneas / Estaciones)");
        panel.add(btnGestionInfraestructura);

        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(255, 102, 102));
        panel.add(btnCerrarSesion);

        add(panel);
    }

    public void escucharBtnUsuarios(ActionListener listen) { btnGestionUsuarios.addActionListener(listen); }
    public void escucharBtnInfraestructura(ActionListener listen) { btnGestionInfraestructura.addActionListener(listen); }
    public void escucharBtnCerrarSesion(ActionListener listen) { btnCerrarSesion.addActionListener(listen); }
}