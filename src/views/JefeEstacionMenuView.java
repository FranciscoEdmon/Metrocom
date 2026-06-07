package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import views.ViewComponetns.BotonModerno;
import views.ViewComponetns.TarjetitaModerna;
import views.ViewComponetns.VentanaBase;

public class JefeEstacionMenuView extends VentanaBase {

    private JButton btnNuevoReporte;
    private JButton btnMisReportes;
    private JButton btnCerrarSesion;
    private JLabel lblEstacionActual;
    private JLabel lblNombreJefe;

    public JefeEstacionMenuView() {
        super("MetroCom - Panel Operativo (Jefe de Estación)", 450, 300, JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Panel principal con un margen para que no se vea pegado a los bordes
        TarjetitaModerna panelPrincipal = new TarjetitaModerna(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- Norte: Información del Usuario y Estación ---
        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 5, 5));
        panelInfo.setOpaque(false);

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
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        btnNuevoReporte = new BotonModerno("Levantar Nuevo Reporte");
        btnMisReportes = new BotonModerno("Consultar Mis Reportes");

        panelBotones.add(btnNuevoReporte);
        panelBotones.add(btnMisReportes);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        // --- Sur: Botón de Cerrar Sesión ---
        JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSalir.setOpaque(false);
        btnCerrarSesion = new BotonModerno("Cerrar Sesión");

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