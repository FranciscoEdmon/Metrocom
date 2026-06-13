package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class MisReportesView extends JFrame {
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar, btnRegresar;

    public MisReportesView() {
        setTitle("MetroCom - Historial de Incidencias Levantadas");
        setSize(850, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnas = {"Folio ID", "Fecha Registro", "Infraestructura", "Especificación Daño", "Ubicación", "Prioridad", "Estatus"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaHistorial = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaHistorial);
        scroll.setBorder(BorderFactory.createTitledBorder("Folios asociados a su Estación de Adscripción"));

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnActualizar = new JButton("Sincronizar Tabla");
        btnRegresar = new JButton("Volver");
        panelAcciones.add(btnActualizar);
        panelAcciones.add(btnRegresar);

        setLayout(new BorderLayout(10, 10));
        add(scroll, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }

    public void addListeners(ActionListener l) {
        btnActualizar.addActionListener(l);
        btnRegresar.addActionListener(l);
    }
}