package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import views.ViewComponetns.BotonModerno;
import views.ViewComponetns.TarjetitaModerna;
import views.ViewComponetns.VentanaBase;

public class MisReportesView extends VentanaBase {

    private JTable tblMisReportes;
    private JButton btnActualizar;
    private JButton btnRegresar;
    private JLabel lblTotalReportes;

    public MisReportesView() {
        super("MetroCom - Mis Reportes Históricos", 750, 450, JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        TarjetitaModerna panelPrincipal = new TarjetitaModerna(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Norte: Título y contador
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setOpaque(false);
        JLabel lblTitulo = new JLabel("Historial de Fallas Reportadas", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        lblTotalReportes = new JLabel("Total de reportes: 0");
        lblTotalReportes.setForeground(Color.WHITE);

        panelNorte.add(lblTitulo, BorderLayout.WEST);
        panelNorte.add(lblTotalReportes, BorderLayout.EAST);
        panelPrincipal.add(panelNorte, BorderLayout.NORTH);

        // Centro: Tabla
        tblMisReportes = new JTable();
        // Configuraciones visuales básicas para la tabla
        tblMisReportes.setFillsViewportHeight(true);
        JScrollPane scrollTabla = new JScrollPane(tblMisReportes);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // Sur: Botones
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setOpaque(false);
        btnRegresar = new BotonModerno("Regresar al Menú");
        btnActualizar = new BotonModerno("Actualizar Tabla");

        panelSur.add(btnRegresar);
        panelSur.add(btnActualizar);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // Getters para el controlador
    public JTable getTblMisReportes() { return tblMisReportes; }

    // Método para actualizar visualmente el contador
    public void setTotalReportes(int total) {
        lblTotalReportes.setText("Total de reportes: " + total);
    }

    // Listeners
    public void escucharBtnActualizar(ActionListener l) { btnActualizar.addActionListener(l); }
    public void escucharBtnRegresar(ActionListener l) { btnRegresar.addActionListener(l); }
}