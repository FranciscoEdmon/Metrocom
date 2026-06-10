package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class viewGestionInfraA extends JPanel {

    // Estos son los componentes para la linea
    private JTextField txtNombreLinea, txtColorLinea;
    private JButton btnGuardarLinea, btnEliminarLinea;
    private JComboBox<String> cbLineasParaEliminar;

    // Estos son los componentes para la estación
    private JTextField txtNombreEstacion;
    private JCheckBox chkEsTransbordo;
    private JComboBox<String> cbLineaPertenece; // Se llenará dinámicamente desde la BD
    private JButton btnGuardarEstacion, btnEliminarEstacion;

    public viewGestionInfraA() {
        // Aqui dividi el panel en 1 fila y 2 columnas con un espacio de 20 píxeles entre ellas
        setLayout(new GridLayout(1, 2, 20, 0));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // =====================================================================
        // COLUMNA IZQUIERDA: GESTIÓN DE LÍNEAS
        // =====================================================================
        JPanel panelLineas = new JPanel(new BorderLayout(10, 10));
        panelLineas.setBorder(BorderFactory.createTitledBorder("Control de Líneas Oficiales"));

        JPanel formLineas = new JPanel(new GridLayout(2, 2, 10, 15));
        formLineas.add(new JLabel("Nombre de la Línea:"));
        txtNombreLinea = new JTextField();
        formLineas.add(txtNombreLinea);

        formLineas.add(new JLabel("Color Distintivo:"));
        txtColorLinea = new JTextField();
        formLineas.add(txtColorLinea);

        panelLineas.add(formLineas, BorderLayout.NORTH);

        // Zona de acciones para líneas
        JPanel accionesLineas = new JPanel(new GridLayout(3, 1, 5, 10));
        btnGuardarLinea = new JButton("Registrar Nueva Línea");

        cbLineasParaEliminar = new JComboBox<>(new String[]{"Seleccione línea para borrar..."});
        btnEliminarLinea = new JButton("Eliminar Línea Seleccionada");

        accionesLineas.add(btnGuardarLinea);
        accionesLineas.add(cbLineasParaEliminar);
        accionesLineas.add(btnEliminarLinea);

        panelLineas.add(accionesLineas, BorderLayout.CENTER);


        // =====================================================================
        // COLUMNA DERECHA: GESTIÓN DE ESTACIONES
        // =====================================================================
        JPanel panelEstaciones = new JPanel(new BorderLayout(10, 10));
        panelEstaciones.setBorder(BorderFactory.createTitledBorder("Control de Estaciones"));

        JPanel formEstaciones = new JPanel(new GridLayout(3, 2, 10, 15));
        formEstaciones.add(new JLabel("Nombre de Estación:"));
        txtNombreEstacion = new JTextField();
        formEstaciones.add(txtNombreEstacion);

        formEstaciones.add(new JLabel("Asociar a Línea:"));
        // Este ComboBox recibirá el catálogo de las líneas que existan en la BD
        cbLineaPertenece = new JComboBox<>(new String[]{"Seleccione línea..."});
        formEstaciones.add(cbLineaPertenece);

        formEstaciones.add(new JLabel("¿Es Transbordo?"));
        chkEsTransbordo = new JCheckBox("Sí, conecta con otra línea");
        formEstaciones.add(chkEsTransbordo);

        panelEstaciones.add(formEstaciones, BorderLayout.NORTH);

        // Esta es la zona de acciones para estaciones
        JPanel accionesEstaciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnGuardarEstacion = new JButton("Guardar Estación");
        btnEliminarEstacion = new JButton("Eliminar Estación");

        accionesEstaciones.add(btnGuardarEstacion);
        accionesEstaciones.add(btnEliminarEstacion);

        panelEstaciones.add(accionesEstaciones, BorderLayout.CENTER);

        // --- AGREGAR AMBAS COLUMNAS AL PANEL PRINCIPAL ---
        add(panelLineas);
        add(panelEstaciones);
    }

    // =========================================================================
    // GETTERS Y LISTENERS PARA EL CONTROLADOR
    // =========================================================================

    // Getters de Líneas
    public String getNombreLinea() { return txtNombreLinea.getText(); }
    public String getColorLinea() { return txtColorLinea.getText(); }
    public String getLineaAEliminar() { return (String) cbLineasParaEliminar.getSelectedItem(); }

    // Getters de Estaciones
    public String getNombreEstacion() { return txtNombreEstacion.getText(); }
    public String getLineaPertenencia() { return (String) cbLineaPertenece.getSelectedItem(); }
    public boolean isTransbordo() { return chkEsTransbordo.isSelected(); }

    // Listeners para que el Controlador detecte los clics
    public void addGuardarLineaListener(ActionListener l) { btnGuardarLinea.addActionListener(l); }
    public void addEliminarLineaListener(ActionListener l) { btnEliminarLinea.addActionListener(l); }
    public void addGuardarEstacionListener(ActionListener l) { btnGuardarEstacion.addActionListener(l); }
    public void addEliminarEstacionListener(ActionListener l) { btnEliminarEstacion.addActionListener(l); }

    // Métodos para actualizar los ComboBox dinámicamente desde el Controlador
    public void actualizarComboLineas(String[] lineas) {
        cbLineaPertenece.setModel(new DefaultComboBoxModel<>(lineas));
        cbLineasParaEliminar.setModel(new DefaultComboBoxModel<>(lineas));
    }

    public void mostrarMensaje(String m) {
        JOptionPane.showMessageDialog(this, m, "Infraestructura MetroCom", JOptionPane.INFORMATION_MESSAGE);
    }
}