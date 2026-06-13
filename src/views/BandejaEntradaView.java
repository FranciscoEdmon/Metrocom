package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class BandejaEntradaView extends JFrame {
    private JTable tablaPendientes;
    private DefaultTableModel modeloTabla;
    private JButton btnAceptarReporte, btnVolver;

    public BandejaEntradaView() {
        setTitle("MetroCom - Bandeja de Entrada (Reportes Pendientes)");
        setSize(850, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnas = {"Folio ID", "Estación", "Infraestructura", "Daño", "Prioridad", "Fecha"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPendientes = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaPendientes);
        scroll.setBorder(BorderFactory.createTitledBorder("Folios Pendientes de Aprobación"));

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnAceptarReporte = new JButton("Aceptar y Pasar a 'En Curso'");
        btnVolver = new JButton("Volver");

        panelAcciones.add(btnVolver);
        panelAcciones.add(btnAceptarReporte);

        setLayout(new BorderLayout(10, 10));
        add(scroll, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaPendientes() { return tablaPendientes; }

    public void addListeners(ActionListener l) {
        btnAceptarReporte.addActionListener(l);
        btnVolver.addActionListener(l);
    }
}