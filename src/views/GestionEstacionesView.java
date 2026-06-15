package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class GestionEstacionesView extends JFrame {
    private JTextField txtIdEstacion, txtNombreEstacion, txtIdLinea;
    private JCheckBox chkTransbordo;
    private JButton btnAgregar, btnModificar, btnEliminar, btnVolver;
    private JTable tablaEstaciones;
    private DefaultTableModel modeloTabla;

    public GestionEstacionesView() {
        setTitle("MetroCom - Gestión de Estaciones por Línea");
        setSize(850, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- FORMULARIO IZQUIERDO ---
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 8, 12));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Estación"));

        txtIdEstacion = new JTextField();
        txtIdEstacion.setEditable(false); // Autoincremental
        txtNombreEstacion = new JTextField();

        txtIdLinea = new JTextField();
        txtIdLinea.setEditable(false); // Viene dado por la línea seleccionada

        chkTransbordo = new JCheckBox("¿Es estación de transbordo?");

        panelForm.add(new JLabel("ID Estación:"));
        panelForm.add(txtIdEstacion);
        panelForm.add(new JLabel("Nombre Estación:"));
        panelForm.add(txtNombreEstacion);
        panelForm.add(new JLabel("ID Línea asignada:"));
        panelForm.add(txtIdLinea);
        panelForm.add(new JLabel("Propiedades:"));
        panelForm.add(chkTransbordo);

        // --- TABLA DERECHA ---
        String[] columnas = {"ID Estación", "Nombre", "Transbordo", "ID Línea"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaEstaciones = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaEstaciones);

        // --- BOTONES INFERIORES ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnVolver = new JButton("Volver a Líneas");

        panelAcciones.add(btnAgregar);
        panelAcciones.add(btnModificar);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(btnVolver);

        // --- DISTRIBUCIÓN GENERAL ---
        setLayout(new BorderLayout(10, 10));
        add(panelForm, BorderLayout.WEST);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    // --- GETTERS Y SETTERS PARA EL CONTROLADOR ---
    public String getIdEstacion() { return txtIdEstacion.getText().trim(); }
    public void setIdEstacion(String id) { txtIdEstacion.setText(id); }

    public String getNombreEstacion() { return txtNombreEstacion.getText().trim(); }
    public void setNombreEstacion(String nombre) { txtNombreEstacion.setText(nombre); }

    public String getIdLinea() { return txtIdLinea.getText().trim(); }
    public void setIdLinea(String id) { txtIdLinea.setText(id); }

    public boolean isTransbordo() { return chkTransbordo.isSelected(); }
    public void setTransbordo(boolean t) { chkTransbordo.setSelected(t); }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaEstaciones() { return tablaEstaciones; }

    public void limpiarFormulario() {
        txtIdEstacion.setText("");
        txtNombreEstacion.setText("");
        chkTransbordo.setSelected(false);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Vincular Eventos
    public void addListeners(ActionListener btnL, MouseAdapter mouseL) {
        btnAgregar.addActionListener(btnL);
        btnModificar.addActionListener(btnL);
        btnEliminar.addActionListener(btnL);
        btnVolver.addActionListener(btnL);
        tablaEstaciones.addMouseListener(mouseL);
    }
}