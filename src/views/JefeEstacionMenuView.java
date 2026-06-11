package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import views.base.VentanaBase;
import views.base.PanelNubeHeader;
import views.base.BotonModerno;

public class JefeEstacionMenuView extends VentanaBase {

    private JButton btnNuevoReporte;
    private JButton btnMisReportes;
    private JButton btnCerrarSesion;
    private JLabel lblEstacionActual;
    private JLabel lblNombreJefe;

    public JefeEstacionMenuView() {
        super("MetroCom - Panel Operativo (Jefe de Estación)", 660, 320, JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        PanelModerno panelPrincipal = new PanelModerno(new BorderLayout(15, 15));

        PanelNubeHeader panelNube = new PanelNubeHeader();
        panelNube.setLayout(new GridLayout(2, 1, 4, 4));

        lblNombreJefe = new JLabel("Bienvenido, Jefe de Estación", SwingConstants.CENTER);
        lblNombreJefe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombreJefe.setForeground(new Color(40, 20, 0)); // Texto contrastante dentro del fondo nube brillante

        lblEstacionActual = new JLabel("Estación Asignada: [Cargando...]", SwingConstants.CENTER);
        lblEstacionActual.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblEstacionActual.setForeground(new Color(60, 30, 0));

        panelNube.add(lblNombreJefe);
        panelNube.add(lblEstacionActual);
        panelPrincipal.add(panelNube, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 15, 15));
        panelBotones.setOpaque(false);

        btnNuevoReporte = new BotonModerno("Levantar Nuevo Reporte de Falla");
        btnMisReportes = new BotonModerno("Consultar Mis Reportes");

        panelBotones.add(btnNuevoReporte);
        panelBotones.add(btnMisReportes);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSalir.setOpaque(false);

        // Botón con color distintivo de la gama de advertencia/atención (Rojo quemado / Naranja Intenso)
        btnCerrarSesion = new BotonModerno("Cerrar Sesión", new Color(220, 70, 0), Color.WHITE);
        panelSalir.add(btnCerrarSesion);
        panelPrincipal.add(panelSalir, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    //Setters para que el controlador inyecte los datos
    public void setNombreJefe(String nombreCompleto) { lblNombreJefe.setText("Bienvenido, " + nombreCompleto); }
    public void setEstacionAsignada(String nombreEstacion) { lblEstacionActual.setText("Estación Asignada: " + nombreEstacion); }

    //Listeners para que el controlador escuche los eventos
    public void escucharBtnNuevoReporte(ActionListener l) { btnNuevoReporte.addActionListener(l); }
    public void escucharBtnMisReportes(ActionListener l) { btnMisReportes.addActionListener(l); }
    public void escucharBtnCerrarSesion(ActionListener l) { btnCerrarSesion.addActionListener(l); }
}