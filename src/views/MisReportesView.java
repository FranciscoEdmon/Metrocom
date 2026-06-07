package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MisReportesView extends JFrame {

    private JTable tblMisReportes;
    private JButton btnActualizar;
    private JButton btnRegresar;
    private JLabel lblTotalReportes;

    public MisReportesView() {
        setTitle("MetroCom - Mis Reportes Históricos");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Norte: Título y contador
        JPanel panelNorte = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Historial de Fallas Reportadas", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        lblTotalReportes = new JLabel("Total de reportes: 0");
        lblTotalReportes.setForeground(Color.DARK_GRAY);

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
        btnRegresar = new JButton("Regresar al Menú");
        btnActualizar = new JButton("Actualizar Tabla");
        btnActualizar.setBackground(new Color(153, 204, 255));

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