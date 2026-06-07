package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GerenteLineaMenuView extends JFrame {

    private JButton btnBandejaEntrada;
    private JButton btnReportesAtencion;
    private JButton btnCerrarSesion;
    private JLabel lblNombreGerente;
    private JLabel lblLineaAsignada;

    public GerenteLineaMenuView() {
        setTitle("MetroCom - Panel de Control Gerencial");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // --- Norte: Datos de la Sesión ---
        JPanel panelHeader = new JPanel(new GridLayout(2, 1, 5, 5));
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
        panelMenu.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        btnBandejaEntrada = new JButton("Bandeja de Entrada (Reportes Pendientes)");
        btnBandejaEntrada.setFont(new Font("Arial", Font.PLAIN, 14));

        btnReportesAtencion = new JButton("Reportes en Atención (En Curso)");
        btnReportesAtencion.setFont(new Font("Arial", Font.PLAIN, 14));

        panelMenu.add(btnBandejaEntrada);
        panelMenu.add(btnReportesAtencion);
        panelPrincipal.add(panelMenu, BorderLayout.CENTER);

        // --- Sur: Salida ---
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(255, 102, 102));
        btnCerrarSesion.setForeground(Color.WHITE);

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