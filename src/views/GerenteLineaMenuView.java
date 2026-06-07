package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import views.ViewComponetns.BotonModerno;
import views.ViewComponetns.TarjetitaModerna;
import views.ViewComponetns.VentanaBase;

public class GerenteLineaMenuView extends VentanaBase {

    private JButton btnBandejaEntrada;
    private JButton btnReportesAtencion;
    private JButton btnCerrarSesion;
    private JLabel lblNombreGerente;
    private JLabel lblLineaAsignada;

    public GerenteLineaMenuView() {
        super("MetroCom - Panel de Control Gerencial", 500, 350, JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        TarjetitaModerna panelPrincipal = new TarjetitaModerna(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // --- Norte: Datos de la Sesión ---
        JPanel panelHeader = new JPanel(new GridLayout(2, 1, 5, 5));
        panelHeader.setOpaque(false);
        lblNombreGerente = new JLabel("Bienvenido, Gerente de Línea", SwingConstants.CENTER);
        lblNombreGerente.setFont(new Font("Arial", Font.BOLD, 16));

        lblLineaAsignada = new JLabel("Línea a Cargo: [Cargando...]", SwingConstants.CENTER);
        lblLineaAsignada.setFont(new Font("Arial", Font.ITALIC, 14));
        lblLineaAsignada.setForeground(new Color(230, 126, 34)); // Color naranja/gerencial descriptivo

        panelHeader.add(lblNombreGerente);
        panelHeader.add(lblLineaAsignada);
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // --- Centro: Botones de Gestión ---
        JPanel panelMenu = new JPanel(new GridLayout(2, 1, 15, 15));
        panelMenu.setOpaque(false);
        panelMenu.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        btnBandejaEntrada = new BotonModerno("Bandeja de Entrada (Reportes Pendientes)");
        btnReportesAtencion = new BotonModerno("Reportes en Atención (En Curso)");

        panelMenu.add(btnBandejaEntrada);
        panelMenu.add(btnReportesAtencion);
        panelPrincipal.add(panelMenu, BorderLayout.CENTER);

        // --- Sur: Salida ---
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFooter.setOpaque(false);
        btnCerrarSesion = new BotonModerno("Cerrar Sesión");
        panelFooter.add(btnCerrarSesion);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // Setters dinámicos
    public void setNombreGerente(String nombre) { lblNombreGerente.setText("Bienvenido, " + nombre); }
    public void setLineaAsignada(String linea) { lblLineaAsignada.setText("Línea a Cargo: " + linea); }

    // Listeners
    public void escucharBtnBandeja(ActionListener l) { btnBandejaEntrada.addActionListener(l); }
    public void escucharBtnAtencion(ActionListener l) { btnReportesAtencion.addActionListener(l); }
    public void escucharBtnCerrarSesion(ActionListener l) { btnCerrarSesion.addActionListener(l); }
}