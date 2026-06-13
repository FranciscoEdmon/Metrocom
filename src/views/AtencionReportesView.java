package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class AtencionReportesView extends JFrame {
    private JTable tablaEnCurso;
    private DefaultTableModel modeloTabla;
    private JButton btnFinalizarTrabajo, btnVolver;

    public AtencionReportesView() {
        setTitle("MetroCom - Reportes en Atención Activa");
        setSize(850, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnas = {"Folio ID", "Estación", "Infraestructura", "Descripción Tarea", "Prioridad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaEnCurso = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaEnCurso);
        scroll.setBorder(BorderFactory.createTitledBorder("Mantenimientos y Reparaciones En Curso"));

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnFinalizarTrabajo = new JButton("Marcar Mantenimiento como 'Completado'");
        btnFinalizarTrabajo.setBackground(new Color(200, 240, 200)); // Color verde tenue para finalizar
        btnVolver = new JButton("Volver");

        panelAcciones.add(btnVolver);
        panelAcciones.add(btnFinalizarTrabajo);

        setLayout(new BorderLayout(10, 10));
        add(scroll, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaEnCurso() { return tablaEnCurso; }

    public void addListeners(ActionListener l) {
        btnFinalizarTrabajo.addActionListener(l);
        btnVolver.addActionListener(l);
    }
}