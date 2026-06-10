package views;

import javax.swing.*;
import java.awt.*;

public class viewDashboardA extends JFrame {
    private JTabbedPane pestanas;
    private viewGestionUsuariosA panelUsuarios;
    private viewGestionInfraA panelInfraestructura;

    public viewDashboardA() {
        setTitle("MetroCom - Panel de Administración");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Inicializamos las pestañas
        pestanas = new JTabbedPane();

        // Inicializamos los paneles secundarios
        panelUsuarios = new viewGestionUsuariosA();
        panelInfraestructura = new viewGestionInfraA();

        // Agregamos los paneles a las pestañas
        pestanas.addTab("Gestión de Usuarios", panelUsuarios);
        pestanas.addTab("Control de Infraestructura", panelInfraestructura);

        add(pestanas, BorderLayout.CENTER);
    }

    // Getters para que el controlador pueda acceder a los paneles internos si lo necesita
    public viewGestionUsuariosA getPanelUsuarios() {
        return panelUsuarios;
    }

    public viewGestionInfraA getPanelInfraestructura() {
        return panelInfraestructura;
    }
}