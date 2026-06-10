package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BandejaEntradaView extends JFrame {

    private JTable tblPendientes;
    private JButton btnAtenderReporte;
    private JButton btnActualizar;
    private JButton btnRegresar;

    public BandejaEntradaView() {
        setTitle("MetroCom - Bandeja de Entrada (Fallas Pendientes)");
        setSize(800, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Norte: Encabezado descriptivo
        JLabel lblTitulo = new JLabel("Nuevas Fallas Reportadas en la Línea", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Centro: Tabla de pendientes
        tblPendientes = new JTable();
        tblPendientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPendientes.setFillsViewportHeight(true);
        JScrollPane scrollTabla = new JScrollPane(tblPendientes);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // Sur: Acciones operativas
        JPanel panelAcciones = new JPanel(new BorderLayout());

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRegresar = new JButton("Regresar al Menú");
        panelIzquierdo.add(btnRegresar);

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnActualizar = new JButton("Actualizar");

        btnAtenderReporte = new JButton("Atender Reporte Seleccionado");
        btnAtenderReporte.setBackground(new Color(241, 196, 15)); // Amarillo preventivo / operativo
        btnAtenderReporte.setFont(new Font("Arial", Font.BOLD, 12));

        panelDerecho.add(btnActualizar);
        panelDerecho.add(btnAtenderReporte);

        panelAcciones.add(panelIzquierdo, BorderLayout.WEST);
        panelAcciones.add(panelDerecho, BorderLayout.EAST);

        panelPrincipal.add(panelAcciones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    // Getters
    public JTable getTblPendientes() { return tblPendientes; }

    public int getIdReporteSeleccionado() {
        int fila = tblPendientes.getSelectedRow();
        if (fila == -1) return -1;
        // Asumiendo que el ID del reporte está siempre en la columna 0
        return (int) tblPendientes.getValueAt(fila, 0);
    }

    public void mostrarMensaje(String msg, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, msg, titulo, tipo);
    }

    // Listeners
    public void escucharBtnRegresar(ActionListener l) { btnRegresar.addActionListener(l); }
    public void escucharBtnActualizar(ActionListener l) { btnActualizar.addActionListener(l); }
    public void escucharBtnAtender(ActionListener l) { btnAtenderReporte.addActionListener(l); }
}