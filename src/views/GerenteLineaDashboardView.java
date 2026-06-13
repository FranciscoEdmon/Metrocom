package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.GerenteLinea;

public class GerenteLineaDashboardView extends JFrame {
    private JButton btnBandejaEntrada, btnReportesAtencion, btnCerrarSesion;
    private JLabel lblBienvenida, lblLinea;

    public GerenteLineaDashboardView(GerenteLinea gerente) {
        setTitle("MetroCom - Panel de Gerencia de Línea");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header
        JPanel panelHeader = new JPanel(new GridLayout(2, 1, 5, 5));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelHeader.setBackground(new Color(255, 245, 230)); // Un tono distinto para diferenciar roles

        lblBienvenida = new JLabel("Bienvenido Gerente: " + gerente.getNombre() + " " + gerente.getApellidoPat());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        String nombreLinea = (gerente.getLineaAsignada() != null) ? gerente.getLineaAsignada().getNombreLinea() : "No Asignada";
        lblLinea = new JLabel("Línea a Cargo: " + nombreLinea);
        lblLinea.setFont(new Font("Arial", Font.PLAIN, 13));

        panelHeader.add(lblBienvenida);
        panelHeader.add(lblLinea);

        // Menú central
        JPanel panelMenu = new JPanel(new GridLayout(2, 1, 15, 15));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        btnBandejaEntrada = new JButton("Bandeja de Entrada (Nuevos Reportes)");
        btnReportesAtencion = new JButton("Reportes en Atención (Actualizar Estatus)");

        panelMenu.add(btnBandejaEntrada);
        panelMenu.add(btnReportesAtencion);

        // Footer
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCerrarSesion = new JButton("Cerrar Sesión");
        panelFooter.add(btnCerrarSesion);

        setLayout(new BorderLayout());
        add(panelHeader, BorderLayout.NORTH);
        add(panelMenu, BorderLayout.CENTER);
        add(panelFooter, BorderLayout.SOUTH);
    }

    public void addListeners(ActionListener listener) {
        btnBandejaEntrada.addActionListener(listener);
        btnReportesAtencion.addActionListener(listener);
        btnCerrarSesion.addActionListener(listener);
    }
}