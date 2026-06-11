package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import views.base.VentanaBase;
import views.base.PanelNubeHeader;
import views.base.BotonModerno;

public class GerenteLineaMenuView extends VentanaBase {

    private JButton btnBandejaEntrada;
    private JButton btnReportesAtencion;
    private JButton btnCerrarSesion;
    private JLabel lblNombreGerente;
    private JLabel lblLineaAsignada;

    public GerenteLineaMenuView() {
        super("MetroCom - Panel de Control Gerencial", 660, 320, JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        PanelModerno panelPrincipal = new PanelModerno(new BorderLayout(15, 15));

        PanelNubeHeader panelNube = new PanelNubeHeader();
        panelNube.setLayout(new GridLayout(2, 1, 4, 4));

        lblNombreGerente = new JLabel("Bienvenido, Gerente de Línea", SwingConstants.CENTER);
        lblNombreGerente.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombreGerente.setForeground(new Color(40, 20, 0));

        lblLineaAsignada = new JLabel("Línea a Cargo: [Cargando...]", SwingConstants.CENTER);
        lblLineaAsignada.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblLineaAsignada.setForeground(new Color(60, 30, 0));

        panelNube.add(lblNombreGerente);
        panelNube.add(lblLineaAsignada);
        panelPrincipal.add(panelNube, BorderLayout.NORTH);

        JPanel panelMenu = new JPanel(new GridLayout(1, 2, 15, 15));
        panelMenu.setOpaque(false);

        btnBandejaEntrada = new BotonModerno("Bandeja de Entrada (Reportes Pendientes)");
        btnReportesAtencion = new BotonModerno("Reportes en Atención (En Curso)");

        panelMenu.add(btnBandejaEntrada);
        panelMenu.add(btnReportesAtencion);
        panelPrincipal.add(panelMenu, BorderLayout.CENTER);

        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFooter.setOpaque(false);

        btnCerrarSesion = new BotonModerno("Cerrar Sesión", new Color(220, 70, 0), Color.WHITE);
        panelFooter.add(btnCerrarSesion);
        panelPrincipal.add(panelFooter, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    //Setters para que el controlador inyecte los datos
    public void setNombreGerente(String nombre) { lblNombreGerente.setText("Bienvenido, " + nombre); }
    public void setLineaAsignada(String linea) { lblLineaAsignada.setText("Línea a Cargo: " + linea); }

    //Listeners para que el contronlador maneje las acciones
    public void escucharBtnBandeja(ActionListener l) { btnBandejaEntrada.addActionListener(l); }
    public void escucharBtnAtencion(ActionListener l) { btnReportesAtencion.addActionListener(l); }
    public void escucharBtnCerrarSesion(ActionListener l) { btnCerrarSesion.addActionListener(l); }
}