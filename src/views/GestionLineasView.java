package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class GestionLineasView extends JFrame {
    // --- COMPONENTES ORIGINALES (LÍNEAS) ---
    private JTextField txtId, txtNombre, txtColor;
    private JButton btnAgregar, btnModificar, btnEliminar, btnVolver;
    private JTable tablaLineas;
    private DefaultTableModel modeloTabla;

    // --- COMPONENTES NUEVOS (ESTACIONES) ---
    private JPanel panelCartasIzquierda; // Contenedor CardLayout
    private CardLayout navegadorCartas;

    private JTextField txtIdEstacion, txtNombreEstacion;
    private JCheckBox chkTransbordo;
    private JTable tablaEstaciones;
    private DefaultTableModel modeloTablaEstaciones;
    private JButton btnAgregarEstacion, btnModificarEstacion, btnEliminarEstacion, btnRegresarALineas;
    private JButton btnVerEstaciones; // Botón gatillo en la zona de líneas

    public GestionLineasView() {
        setTitle("MetroCom - Control de Infraestructura");
        setSize(950, 500); // Incrementamos ligeramente el ancho para que la sub-tabla quepa cómoda
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        navegadorCartas = new CardLayout();
        panelCartasIzquierda = new JPanel(navegadorCartas);

        // ==========================================
        // CARTA 1: CRUD LÍNEAS (Tu diseño original)
        // ==========================================
        JPanel panelFormularioLinea = new JPanel(new GridLayout(4, 2, 10, 15));
        panelFormularioLinea.setBorder(BorderFactory.createTitledBorder("Datos de la Línea"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombre = new JTextField();
        txtColor = new JTextField();

        panelFormularioLinea.add(new JLabel("ID Línea:"));
        panelFormularioLinea.add(txtId);
        panelFormularioLinea.add(new JLabel("Nombre de Línea:"));
        panelFormularioLinea.add(txtNombre);
        panelFormularioLinea.add(new JLabel("Color Distintivo:"));
        panelFormularioLinea.add(txtColor);

        JPanel panelBotonesLinea = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnVerEstaciones = new JButton("Ver Estaciones →");
        btnVerEstaciones.setBackground(new Color(0, 122, 255));
        btnVerEstaciones.setForeground(Color.WHITE);

        panelBotonesLinea.add(btnAgregar);
        panelBotonesLinea.add(btnModificar);
        panelBotonesLinea.add(btnEliminar);
        panelBotonesLinea.add(btnVerEstaciones);

        JPanel contenedorCartaLineas = new JPanel(new BorderLayout());
        contenedorCartaLineas.add(panelFormularioLinea, BorderLayout.CENTER);
        contenedorCartaLineas.add(panelBotonesLinea, BorderLayout.SOUTH);

        // ==========================================
        // CARTA 2: NUEVO SUB-CRUD ESTACIONES
        // ==========================================
        JPanel contenedorCartaEstaciones = new JPanel(new BorderLayout(5, 10));
        contenedorCartaEstaciones.setBorder(BorderFactory.createTitledBorder("Estaciones de la Línea Seleccionada"));

        // Sub-tabla interna de estaciones
        String[] columnasEst = {"ID", "Nombre Estación", "Transbordo"};
        modeloTablaEstaciones = new DefaultTableModel(columnasEst, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaEstaciones = new JTable(modeloTablaEstaciones);
        JScrollPane scrollEstaciones = new JScrollPane(tablaEstaciones);
        scrollEstaciones.setPreferredSize(new Dimension(300, 150));

        // Formulario Estación
        JPanel panelCamposEstacion = new JPanel(new GridLayout(3, 2, 5, 10));
        txtIdEstacion = new JTextField();
        txtIdEstacion.setEditable(false);
        txtNombreEstacion = new JTextField();
        chkTransbordo = new JCheckBox("¿Es correspondencia / transbordo?");

        panelCamposEstacion.add(new JLabel("ID Estación:"));
        panelCamposEstacion.add(txtIdEstacion);
        panelCamposEstacion.add(new JLabel("Nombre:"));
        panelCamposEstacion.add(txtNombreEstacion);
        panelCamposEstacion.add(new JLabel("Tipo:"));
        panelCamposEstacion.add(chkTransbordo);

        // Botones Estación
        JPanel panelBotonesEstacion = new JPanel(new GridLayout(2, 2, 5, 5));
        btnAgregarEstacion = new JButton("Añadir Estación");
        btnModificarEstacion = new JButton("Modificar Est.");
        btnEliminarEstacion = new JButton("Eliminar Est.");
        btnRegresarALineas = new JButton("← Volver a Líneas");
        btnRegresarALineas.setBackground(new Color(230, 75, 75));
        btnRegresarALineas.setForeground(Color.WHITE);

        panelBotonesEstacion.add(btnAgregarEstacion);
        panelBotonesEstacion.add(btnModificarEstacion);
        panelBotonesEstacion.add(btnEliminarEstacion);
        panelBotonesEstacion.add(btnRegresarALineas);

        contenedorCartaEstaciones.add(scrollEstaciones, BorderLayout.NORTH);
        contenedorCartaEstaciones.add(panelCamposEstacion, BorderLayout.CENTER);
        contenedorCartaEstaciones.add(panelBotonesEstacion, BorderLayout.SOUTH);

        // Agregar ambas cartas al contenedor izquierdo
        panelCartasIzquierda.add(contenedorCartaLineas, "PanelLineas");
        panelCartasIzquierda.add(contenedorCartaEstaciones, "PanelEstaciones");

        // --- PANEL DE TABLA GENERAL (DERECHA) ---
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
        add(panelCartasIzquierda, BorderLayout.WEST);
        add(scrollTabla, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    // --- MÉTODOS DE NAVEGACIÓN ENTRE VISTAS ---
    public void alternarVistaEstaciones(boolean verEstaciones) {
        if (verEstaciones) {
            navegadorCartas.show(panelCartasIzquierda, "PanelEstaciones");
        } else {
            navegadorCartas.show(panelCartasIzquierda, "PanelLineas");
        }
    }

    // --- MÉTODOS PARA INTERACTUAR CON FORMULARIOS ---
    public void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtColor.setText("");
    }

    public void limpiarFormularioEstacion() {
        txtIdEstacion.setText("");
        txtNombreEstacion.setText("");
        chkTransbordo.setSelected(false);
    }

    public void llenarCamposFormulario(String id, String nombre, String color) {
        txtId.setText(id);
        txtNombre.setText(nombre);
        txtColor.setText(color);
    }

    public void llenarCamposEstacion(String id, String nombre, boolean esTransbordo) {
        txtIdEstacion.setText(id);
        txtNombreEstacion.setText(nombre);
        chkTransbordo.setSelected(esTransbordo);
    }

    // --- GETTERS DE DATOS (LÍNEAS) ---
    public String getIdLinea() { return txtId.getText(); }
    public String getNombreLinea() { return txtNombre.getText().trim(); }
    public String getColorLinea() { return txtColor.getText().trim(); }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaLineas() { return tablaLineas; }

    // --- GETTERS DE DATOS (ESTACIONES) ---
    public String getIdEstacion() { return txtIdEstacion.getText(); }
    public String getNombreEstacion() { return txtNombreEstacion.getText().trim(); }
    public boolean getIsTransbordo() { return chkTransbordo.isSelected(); }
    public DefaultTableModel getModeloTablaEstaciones() { return modeloTablaEstaciones; }
    public JTable getTablaEstaciones() { return tablaEstaciones; }

    // --- VINCULAR EVENTOS ---
    public void addListeners(ActionListener btnListener, MouseAdapter tablaListener) {
        // Eventos Línea
        btnAgregar.addActionListener(btnListener);
        btnModificar.addActionListener(btnListener);
        btnEliminar.addActionListener(btnListener);
        btnVolver.addActionListener(btnListener);
        btnVerEstaciones.addActionListener(btnListener);
        tablaLineas.addMouseListener(tablaListener);
    }

    // Sobrecarga para enlazar los eventos específicos del sub-CRUD de Estaciones
    public void addEstacionesListeners(ActionListener btnEstacionListener, MouseAdapter tablaEstacionListener) {
        btnAgregarEstacion.addActionListener(btnEstacionListener);
        btnModificarEstacion.addActionListener(btnEstacionListener);
        btnEliminarEstacion.addActionListener(btnEstacionListener);
        btnRegresarALineas.addActionListener(btnEstacionListener);
        tablaEstaciones.addMouseListener(tablaEstacionListener);
    }

    public void mostrarMensaje(String msg) { JOptionPane.showMessageDialog(this, msg); }
}