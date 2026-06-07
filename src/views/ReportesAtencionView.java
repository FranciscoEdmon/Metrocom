package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import views.ViewComponetns.BotonModerno;
import views.ViewComponetns.TarjetitaModerna;
import views.ViewComponetns.VentanaBase;

public class ReportesAtencionView extends VentanaBase {

    private JTable tblEnCurso;
    private JButton btnCompletarReporte;
    private JButton btnActualizar;
    private JButton btnRegresar;

    public ReportesAtencionView() {
        super("MetroCom - Reportes en Proceso de Reparación", 800, 480, JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        TarjetitaModerna panelPrincipal = new TarjetitaModerna(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Norte: Título
        JLabel lblTitulo = new JLabel("Infraestructura en Mantenimiento / Atención Activa", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(41, 128, 185)); // Azul de seguimiento técnico
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Centro: Tabla de trabajo
        tblEnCurso = new JTable();
        tblEnCurso.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblEnCurso.setFillsViewportHeight(true);
        JScrollPane scrollTabla = new JScrollPane(tblEnCurso);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // Sur: Acciones
        JPanel panelAcciones = new JPanel(new BorderLayout());
        panelAcciones.setOpaque(false);

        JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelIzquierdo.setOpaque(false);
        btnRegresar = new BotonModerno("Regresar al Menú");
        panelIzquierdo.add(btnRegresar);

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelDerecho.setOpaque(false);
        btnActualizar = new BotonModerno("Actualizar");

        btnCompletarReporte = new BotonModerno("Marcar como Completado");
        panelDerecho.add(btnActualizar);
        panelDerecho.add(btnCompletarReporte);

        panelAcciones.add(panelIzquierdo, BorderLayout.WEST);
        panelAcciones.add(panelDerecho, BorderLayout.EAST);

        panelPrincipal.add(panelAcciones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    // Getters
    public JTable getTblEnCurso() { return tblEnCurso; }

    public int getIdReporteSeleccionado() {
        int fila = tblEnCurso.getSelectedRow();
        if (fila == -1) return -1;
        return (int) tblEnCurso.getValueAt(fila, 0);
    }

    public void mostrarMensaje(String msg, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, msg, titulo, tipo);
    }

    // Listeners
    public void escucharBtnRegresar(ActionListener l) { btnRegresar.addActionListener(l); }
    public void escucharBtnActualizar(ActionListener l) { btnActualizar.addActionListener(l); }
    public void escucharBtnCompletar(ActionListener l) { btnCompletarReporte.addActionListener(l); }
}