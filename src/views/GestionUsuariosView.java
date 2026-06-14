package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class GestionUsuariosView extends JFrame {
    private JTextField txtId, txtNombre, txtApPaterno, txtApMaterno, txtCorreo;
    private JPasswordField txtContrasena;
    private JComboBox<String> cbRol;
    private JButton btnAgregar, btnModificar, btnEliminar, btnVolver;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    public GestionUsuariosView() {
        setTitle("MetroCom - Control de Usuarios Operativos");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- FORMULARIO IZQUIERDO ---
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 8, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Ficha de Identidad"));

        txtId = new JTextField(); txtId.setEditable(false);
        txtNombre = new JTextField();
        txtApPaterno = new JTextField();
        txtApMaterno = new JTextField();
        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();

        String[] roles = {"Jefe de Estación", "Gerente de Línea"};
        cbRol = new JComboBox<>(roles);

        panelForm.add(new JLabel("ID de Sistema:")); panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre(s):")); panelForm.add(txtNombre);
        panelForm.add(new JLabel("Apellido Paterno:")); panelForm.add(txtApPaterno);
        panelForm.add(new JLabel("Apellido Materno:")); panelForm.add(txtApMaterno);
        panelForm.add(new JLabel("Correo Electrónico:")); panelForm.add(txtCorreo);
        panelForm.add(new JLabel("Contraseña Acceso:")); panelForm.add(txtContrasena);
        panelForm.add(new JLabel("Rol Asignado:")); panelForm.add(cbRol);

        // --- ACCIONES ---
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        btnAgregar = new JButton("Registrar");
        btnModificar = new JButton("Actualizar");
        btnEliminar = new JButton("Dar de Baja");
        panelAcciones.add(btnAgregar); panelAcciones.add(btnModificar); panelAcciones.add(btnEliminar);

        JPanel contenedorIzquierdo = new JPanel(new BorderLayout());
        contenedorIzquierdo.add(panelForm, BorderLayout.CENTER);
        contenedorIzquierdo.add(panelAcciones, BorderLayout.SOUTH);

        // --- TABLA DE REGISTROS ---
        String[] columnas = {"ID", "Nombre Completo", "Correo", "Rol Técnico"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBorder(BorderFactory.createTitledBorder("Plantilla de Personal Activo"));

        // --- CIERRE ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVolver = new JButton("Volver al Panel");
        panelSur.add(btnVolver);

        setLayout(new BorderLayout(15, 15));
        add(contenedorIzquierdo, BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }

    public void limpiarCampos() {
        txtId.setText(""); txtNombre.setText(""); txtApPaterno.setText("");
        txtApMaterno.setText(""); txtCorreo.setText(""); txtContrasena.setText("");
        cbRol.setSelectedIndex(0);
    }

    public void cargarFormulario(String id, String nom, String apPat, String apMat, String mail, String rol) {
        txtId.setText(id); txtNombre.setText(nom); txtApPaterno.setText(apPat);
        txtApMaterno.setText(apMat); txtCorreo.setText(mail);
        cbRol.setSelectedItem(rol);
    }

    // Getters
    public String getIdUsuario() { return txtId.getText(); }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getApPaterno() { return txtApPaterno.getText().trim(); }
    public String getApMaterno() { return txtApMaterno.getText().trim(); }
    public String getCorreo() { return txtCorreo.getText().trim(); }
    public String getContrasena() { return new String(txtContrasena.getPassword()); }
    public String getRolSeleccionado() { return cbRol.getSelectedItem().toString(); }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaUsuarios() { return tablaUsuarios; }

    public void addListeners(ActionListener btnL, MouseAdapter mouseL) {
        btnAgregar.addActionListener(btnL);
        btnModificar.addActionListener(btnL);
        btnEliminar.addActionListener(btnL);
        btnVolver.addActionListener(btnL);
        tablaUsuarios.addMouseListener(mouseL);
    }
}