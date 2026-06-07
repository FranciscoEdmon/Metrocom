package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.Linea;

public class GestionInfraestructuraView extends JFrame {
    // Componentes Línea
    private JTextField txtNombreLinea, txtColorLinea;
    private JButton btnAddLinea, btnEditLinea, btnDelLinea;
    private JTable tblLineas;

    // Componentes Estación
    private JTextField txtNombreEstacion;
    private JComboBox<Linea> cmbLineaEstacion; // Listado cerrado
    private JCheckBox chkTransbordo;
    private JButton btnAddEstacion, btnEditEstacion, btnDelEstacion;
    private JTable tblEstaciones;

    private JButton btnRegresar;

    public GestionInfraestructuraView() {
        setTitle("MetroCom - Control de Infraestructura");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JTabbedPane pestañas = new JTabbedPane();

        // ====== PESTAÑA 1: GESTIÓN DE LÍNEAS ======
        JPanel panelLineas = new JPanel(new BorderLayout(10, 10));
        JPanel formLineas = new JPanel(new GridLayout(3, 2, 5, 5));
        formLineas.setBorder(BorderFactory.createTitledBorder("Datos de Línea"));

        formLineas.add(new JLabel("Nombre de la Línea:"));
        txtNombreLinea = new JTextField();
        formLineas.add(txtNombreLinea);

        formLineas.add(new JLabel("Color de la Línea:"));
        txtColorLinea = new JTextField();
        formLineas.add(txtColorLinea);

        JPanel accionesLineas = new JPanel(new GridLayout(1, 3, 5, 5));
        btnAddLinea = new JButton("Agregar Línea");
        btnEditLinea = new JButton("Editar Línea");
        btnDelLinea = new JButton("Eliminar Línea");
        accionesLineas.add(btnAddLinea);
        accionesLineas.add(btnEditLinea);
        accionesLineas.add(btnDelLinea);

        JPanel contenedorIzquierdoL = new JPanel(new BorderLayout(5, 5));
        contenedorIzquierdoL.add(formLineas, BorderLayout.CENTER);
        contenedorIzquierdoL.add(accionesLineas, BorderLayout.SOUTH);

        tblLineas = new JTable();
        panelLineas.add(contenedorIzquierdoL, BorderLayout.WEST);
        panelLineas.add(new JScrollPane(tblLineas), BorderLayout.CENTER);

        pestañas.addTab("Líneas del Metro", panelLineas);

        // ====== PESTAÑA 2: GESTIÓN DE ESTACIONES ======[cite: 22]
        JPanel panelEstaciones = new JPanel(new BorderLayout(10, 10));
        JPanel formEstaciones = new JPanel(new GridLayout(4, 2, 5, 5));
        formEstaciones.setBorder(BorderFactory.createTitledBorder("Datos de Estación"));

        formEstaciones.add(new JLabel("Nombre de Estación:"));
        txtNombreEstacion = new JTextField();
        formEstaciones.add(txtNombreEstacion);

        formEstaciones.add(new JLabel("Pertenece a la Línea:"));
        cmbLineaEstacion = new JComboBox<>(); // Menú cerrado sin texto libre
        formEstaciones.add(cmbLineaEstacion);

        formEstaciones.add(new JLabel("¿Es estación de transbordo?:"));
        chkTransbordo = new JCheckBox("Sí, cuenta con transbordo");
        formEstaciones.add(chkTransbordo);

        JPanel accionesEstaciones = new JPanel(new GridLayout(1, 3, 5, 5));
        btnAddEstacion = new JButton("Agregar Estación");
        btnEditEstacion = new JButton("Editar Estación");
        btnDelEstacion = new JButton("Eliminar Estación");
        accionesEstaciones.add(btnAddEstacion);
        accionesEstaciones.add(btnEditEstacion);
        accionesEstaciones.add(btnDelEstacion);

        JPanel contenedorIzquierdoE = new JPanel(new BorderLayout(5, 5));
        contenedorIzquierdoE.add(formEstaciones, BorderLayout.CENTER);
        contenedorIzquierdoE.add(accionesEstaciones, BorderLayout.SOUTH);

        tblEstaciones = new JTable();
        panelEstaciones.add(contenedorIzquierdoE, BorderLayout.WEST);
        panelEstaciones.add(new JScrollPane(tblEstaciones), BorderLayout.CENTER);

        pestañas.addTab("Estaciones", panelEstaciones);

        add(pestañas, BorderLayout.CENTER);

        // Botón inferior para volver al menú de administración
        btnRegresar = new JButton("Volver al Menú Principal");
        add(btnRegresar, BorderLayout.SOUTH);
    }

    // Getters de Líneas
    public String getNombreLinea() { return txtNombreLinea.getText().trim(); }
    public String getColorLinea() { return txtColorLinea.getText().trim(); }
    public JTable getTblLineas() { return tblLineas; }

    // Getters de Estaciones
    public String getNombreEstacion() { return txtNombreEstacion.getText().trim(); }
    public Linea getLineaEstacionSeleccionada() { return (Linea) cmbLineaEstacion.getSelectedItem(); }
    public boolean isTransbordo() { return chkTransbordo.isSelected(); }
    public JComboBox<Linea> getCmbLineaEstacion() { return cmbLineaEstacion; }
    public JTable getTblEstaciones() { return tblEstaciones; }

    // Listeners
    public void escucharBtnRegresar(ActionListener l) { btnRegresar.addActionListener(l); }
    public void escucharAccionesLineas(ActionListener add, ActionListener edit, ActionListener del) {
        btnAddLinea.addActionListener(add);
        btnEditLinea.addActionListener(edit);
        btnDelLinea.addActionListener(del);
    }
    public void escucharAccionesEstaciones(ActionListener add, ActionListener edit, ActionListener del) {
        btnAddEstacion.addActionListener(add);
        btnEditEstacion.addActionListener(edit);
        btnDelEstacion.addActionListener(del);
    }

    // Confirmación de borrado/guardado preventivo
    public boolean confirmarAccion(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
}