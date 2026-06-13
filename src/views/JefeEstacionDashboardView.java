package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.JefeEstacion;

public class JefeEstacionDashboardView extends JFrame {
    private JButton btnNuevoReporte, btnMisReportes, btnCerrarSesion;
    private JLabel lblBienvenida, lblEstacion;

    public JefeEstacionDashboardView(JefeEstacion jefe) {
        setTitle("MetroCom - Panel del Jefe de Estación");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header con datos del usuario firmante
        JPanel panelHeader = new JPanel(new GridLayout(2, 1, 5, 5));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelHeader.setBackground(new Color(230, 242, 250));

        lblBienvenida = new JLabel("Bienvenido: " + jefe.getNombre() + " " + jefe.getApellidoPat());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        String nombreEstacion = (jefe.getEstacionAsignada() != null) ? jefe.getEstacionAsignada().getNombreEstacion() : "No Asignada";
        lblEstacion = new JLabel("Estación a Cargo: " + nombreEstacion);
        lblEstacion.setFont(new Font("Arial", Font.PLAIN, 13));

        panelHeader.add(lblBienvenida);
        panelHeader.add(lblEstacion);

        // Panel de Navegación del Mapa
        JPanel panelMenu = new JPanel(new GridLayout(2, 1, 15, 15));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        btnNuevoReporte = new JButton("Reportar Nueva Falla / Incidente");
        btnNuevoReporte.setFont(new Font("Arial", Font.PLAIN, 14));

        btnMisReportes = new JButton("Ver Mis Reportes Enviados");
        btnMisReportes.setFont(new Font("Arial", Font.PLAIN, 14));

        panelMenu.add(btnNuevoReporte);
        panelMenu.add(btnMisReportes);

        // Botón salir
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCerrarSesion = new JButton("Cerrar Sesión");
        panelFooter.add(btnCerrarSesion);

        setLayout(new BorderLayout());
        add(panelHeader, BorderLayout.NORTH);
        add(panelMenu, BorderLayout.CENTER);
        add(panelFooter, BorderLayout.SOUTH);
    }

    public void addListeners(ActionListener listener) {
        btnNuevoReporte.addActionListener(listener);
        btnMisReportes.addActionListener(listener);
        btnCerrarSesion.addActionListener(listener);
    }
}