package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class GestionLineasView extends JFrame {
    private JTextField txtId, txtNombre, txtColor;
    private JButton btnAgregar, btnModificar, btnEliminar, btnVolver;
    private JTable tablaLineas;
    private DefaultTableModel modeloTabla;

    public GestionLineasView() {
        setTitle("MetroCom - Control de Líneas");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- PANEL DE FORMULARIO (IZQUIERDA) ---
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 15));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Línea"));

        txtId = new JTextField();
        txtId.setEditable(false); // El ID no se edita manualmente
        txtNombre = new JTextField();
        txtColor = new JTextField();

        panelFormulario.add(new JLabel("ID Línea:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Nombre de Línea:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Color Distintivo:"));
        panelFormulario.add(txtColor);

        // --- PANEL DE BOTONES ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        JPanel panelIzquierdoContenedor = new JPanel(new BorderLayout());
        panelIzquierdoContenedor.add(panelFormulario, BorderLayout.CENTER);
        panelIzquierdoContenedor.add(panelBotones, BorderLayout.SOUTH);

        // --- PANEL DE TABLA (DERECHA) ---
        String[] columnas = {"ID", "Nombre de Línea", "Color"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaLineas = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaLineas);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Líneas Registradas en la Red"));

        // --- BOTÓN VOLVER ---
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVolver = new JButton("Volver al Panel");
        panelInferior.add(btnVolver);

        // --- DISTRIBUCIÓN PRINCIPAL ---
        setLayout(new BorderLayout(15, 15));
        add(panelIzquierdoContenedor, BorderLayout.WEST);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    // Métodos para interactuar con los campos
    public void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtColor.setText("");
    }

    public void llenarCamposFormulario(String id, String nombre, String color) {
        txtId.setText(id);
        txtNombre.setText(nombre);
        txtColor.setText(color);
    }

    // Getters de datos
    public String getIdLinea() { return txtId.getText(); }
    public String getNombreLinea() { return txtNombre.getText().trim(); }
    public String getColorLinea() { return txtColor.getText().trim(); }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaLineas() { return tablaLineas; }

    // Vincular Eventos
    public void addListeners(ActionListener btnListener, MouseAdapter tablaListener) {
        btnAgregar.addActionListener(btnListener);
        btnModificar.addActionListener(btnListener);
        btnEliminar.addActionListener(btnListener);
        btnVolver.addActionListener(btnListener);
        tablaLineas.addMouseListener(tablaListener);
    }

    public void mostrarMensaje(String msg) { JOptionPane.showMessageDialog(this, msg); }
}