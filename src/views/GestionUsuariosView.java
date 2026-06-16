package views;

import model.Estacion;
import model.Linea;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class GestionUsuariosView extends JFrame {
    private JTextField txtId, txtNombre, txtApPaterno, txtApMaterno, txtCorreo, txtFechaNacimiento;
    private JPasswordField txtContrasena;
    private JComboBox<String> cbRol;
    private JComboBox<Linea> cbLineaAsignada;
    private JComboBox<Estacion> cbEstacionAsignada;
    private JButton btnAgregar, btnModificar, btnEliminar, btnVolver;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    public GestionUsuariosView() {
        setTitle("MetroCom - Control de Usuarios Operativos");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- FORMULARIO IZQUIERDO (FICHA DE IDENTIDAD) ---
        JPanel panelForm = new JPanel(new GridLayout(10, 2, 8, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Ficha de Identidad"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombre = new JTextField();
        txtApPaterno = new JTextField();
        txtApMaterno = new JTextField();
        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();
        txtFechaNacimiento = new JTextField();

        cbRol = new JComboBox<>(new String[]{"Jefe de Estación", "Gerente de Línea"});
        cbLineaAsignada = new JComboBox<>();
        cbEstacionAsignada = new JComboBox<>();

        panelForm.add(new JLabel(" ID Usuario:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel(" Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel(" Apellido Paterno:"));
        panelForm.add(txtApPaterno);
        panelForm.add(new JLabel(" Apellido Materno:"));
        panelForm.add(txtApMaterno);
        panelForm.add(new JLabel(" Correo:"));
        panelForm.add(txtCorreo);
        panelForm.add(new JLabel(" Contraseña:"));
        panelForm.add(txtContrasena);
        panelForm.add(new JLabel(" F. Nacimiento (AAAA-MM-DD):"));
        panelForm.add(txtFechaNacimiento);
        panelForm.add(new JLabel(" Rol de Usuario:"));
        panelForm.add(cbRol);
        panelForm.add(new JLabel(" Línea Asignada:"));
        panelForm.add(cbLineaAsignada);
        panelForm.add(new JLabel(" Estación Asignada:"));
        panelForm.add(cbEstacionAsignada);

        // --- TABLA CENTRAL ---
        String[] columnas = {"ID", "Nombre Completo", "Correo", "Rol"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tablaUsuarios = new JTable(modeloTabla);

        // --- PANEL DE ACCIONES (BOTONES INFERIORES) ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnVolver = new JButton("Volver al Panel");

        panelAcciones.add(btnAgregar);
        panelAcciones.add(btnModificar);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(btnVolver);

        // --- DISTRIBUCIÓN GENERAL ---
        setLayout(new BorderLayout());
        add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);
        add(panelForm, BorderLayout.WEST);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    // --- MÉTODOS DE FORMULARIO ---
    public void cargarFormulario(String id, String nom, String pat, String mat, String mail, String rol) {
        txtId.setText(id);
        txtNombre.setText(nom);
        txtApPaterno.setText(pat);
        txtApMaterno.setText(mat);
        txtCorreo.setText(mail);
        cbRol.setSelectedItem(rol);
    }

    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApPaterno.setText("");
        txtApMaterno.setText("");
        txtCorreo.setText("");
        txtContrasena.setText("");
        txtFechaNacimiento.setText("");
        cbRol.setSelectedIndex(0);
        if (cbLineaAsignada.getItemCount() > 0) cbLineaAsignada.setSelectedIndex(0);
        if (cbEstacionAsignada.getItemCount() > 0) cbEstacionAsignada.setSelectedIndex(0);
    }

    // --- GETTERS PARA EL CONTROLADOR ---
    public String getIdUsuario() { return txtId.getText(); }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getApPaterno() { return txtApPaterno.getText().trim(); }
    public String getApMaterno() { return txtApMaterno.getText().trim(); }
    public String getCorreo() { return txtCorreo.getText().trim(); }
    public String getContrasena() { return new String(txtContrasena.getPassword()); }
    public String getRolSeleccionado() { return cbRol.getSelectedItem().toString(); }
    public String getFechaNacimiento() { return txtFechaNacimiento.getText().trim(); }

    public JComboBox<Linea> getCbLinea() { return cbLineaAsignada; }
    public JComboBox<Estacion> getCbEstacion() { return cbEstacionAsignada; }
    public Linea getLineaSeleccionada() { return (Linea) cbLineaAsignada.getSelectedItem(); }
    public Estacion getEstacionSeleccionada() { return (Estacion) cbEstacionAsignada.getSelectedItem(); }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaUsuarios() { return tablaUsuarios; }

    // --- MÉTODOS PARA LLENAR LOS COMBOBOX ---
    public void setModeloLineas(DefaultComboBoxModel<Linea> modelo) {
        cbLineaAsignada.setModel(modelo);
    }

    public void setModeloEstaciones(DefaultComboBoxModel<Estacion> modelo) {
        cbEstacionAsignada.setModel(modelo);
    }

    // --- VINCULACIÓN DE ESCUCHADORES ---
    public void addListeners(ActionListener btnL, MouseAdapter mouseL, ActionListener comboL) {
        btnAgregar.addActionListener(btnL);
        btnModificar.addActionListener(btnL);
        btnEliminar.addActionListener(btnL);
        btnVolver.addActionListener(btnL);
        tablaUsuarios.addMouseListener(mouseL);
        cbLineaAsignada.addActionListener(comboL);
    }
}