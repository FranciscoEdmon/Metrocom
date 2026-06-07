package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class JefeEstacionMenuView extends JFrame {

    private JButton btnNuevoReporte;
    private JButton btnMisReportes;
    private JButton btnCerrarSesion;
    private JLabel lblEstacionActual;
    private JLabel lblNombreJefe;

    public JefeEstacionMenuView() {
        setTitle("MetroCom - Panel Operativo (Jefe de Estación)");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Al cerrar el menú, se cierra toda la app
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // Panel principal con un margen para que no se vea pegado a los bordes
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- Norte: Información del Usuario y Estación ---
        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 5, 5));

        lblNombreJefe = new JLabel("Bienvenido, Jefe de Estación", SwingConstants.CENTER);
        lblNombreJefe.setFont(new Font("Arial", Font.BOLD, 16));

        lblEstacionActual = new JLabel("Estación Asignada: [Cargando...]", SwingConstants.CENTER);
        lblEstacionActual.setFont(new Font("Arial", Font.ITALIC, 14));
        lblEstacionActual.setForeground(new Color(50, 50, 150)); // Un azul oscuro elegante

        panelInfo.add(lblNombreJefe);
        panelInfo.add(lblEstacionActual);

        panelPrincipal.add(panelInfo, BorderLayout.NORTH);

        // --- Centro: Botones de Acción (El Menú) ---
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btnNuevoReporte = new JButton("Levantar Nuevo Reporte");
        btnNuevoReporte.setFont(new Font("Arial", Font.PLAIN, 14));

        btnMisReportes = new JButton("Consultar Mis Reportes");
        btnMisReportes.setFont(new Font("Arial", Font.PLAIN, 14));

        panelBotones.add(btnNuevoReporte);
        panelBotones.add(btnMisReportes);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        // --- Sur: Botón de Cerrar Sesión ---
        JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(255, 102, 102)); // Rojo tenue para indicar salida
        btnCerrarSesion.setForeground(Color.WHITE);

        panelSalir.add(btnCerrarSesion);

        panelPrincipal.add(panelSalir, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // --- Setters para que el Controlador inyecte los datos de la sesión ---

    public void setNombreJefe(String nombreCompleto) {
        lblNombreJefe.setText("Bienvenido, " + nombreCompleto);
    }

    public void setEstacionAsignada(String nombreEstacion) {
        lblEstacionActual.setText("Estación Asignada: " + nombreEstacion);
    }

    // --- Listeners para el Controlador ---

    public void escucharBtnNuevoReporte(ActionListener l) {
        btnNuevoReporte.addActionListener(l);
    }

    public void escucharBtnMisReportes(ActionListener l) {
        btnMisReportes.addActionListener(l);
    }

    public void escucharBtnCerrarSesion(ActionListener l) {
        btnCerrarSesion.addActionListener(l);
    }
}