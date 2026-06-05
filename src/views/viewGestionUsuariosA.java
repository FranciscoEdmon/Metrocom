package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class viewGestionUsuariosA extends JPanel {
    // Campos de texto y contraseñas
    private JTextField txtNombres, txtApellidos, txtFechaNac, txtCorreo;
    private JPasswordField txtPassword;

    // Listas desplegables (cumpliendo el requerimiento de no texto libre)
    private JComboBox<String> cbRol;
    private JComboBox<String> cbAsignacion; // Para elegir la Estación o la Línea

    // Botones
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar;

    // Tabla para mostrar usuarios
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    public viewGestionUsuariosA() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- PANEL DE FORMULARIO (Norte) ---
        JPanel panelFormulario = new JPanel(new GridLayout(4, 4, 10, 10));

        panelFormulario.add(new JLabel("Nombre(s):"));
        txtNombres = new JTextField();
        panelFormulario.add(txtNombres);

        panelFormulario.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelFormulario.add(txtApellidos);

        panelFormulario.add(new JLabel("Fecha Nac. (DD/MM/AAAA):"));
        txtFechaNac = new JTextField();
        panelFormulario.add(txtFechaNac);

        panelFormulario.add(new JLabel("Correo (@metrocdmx.com):"));
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        txtPassword.setEchoChar('*'); // Aqui oculto la contraseña con asteriscos
        panelFormulario.add(txtPassword);

        panelFormulario.add(new JLabel("Rol:"));
        cbRol = new JComboBox<>(new String[]{"Seleccione...", "Jefe de Estación", "Gerente de Línea"});
        panelFormulario.add(cbRol);

        panelFormulario.add(new JLabel("Asignación (Estación/Línea):"));
        cbAsignacion = new JComboBox<>(new String[]{"Seleccione Rol primero..."});
        panelFormulario.add(cbAsignacion);

        add(panelFormulario, BorderLayout.NORTH);

        // Pues este es el panel de los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnGuardar = new JButton("Guardar Nuevo");
        btnEditar = new JButton("Actualizar Seleccionado");
        btnEliminar = new JButton("Dar de Baja");
        btnLimpiar = new JButton("Limpiar Campos");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.CENTER);

        // Este es el panel para la tabla
        String[] columnas = {"ID", "Nombres", "Apellidos", "Rol", "Asignación", "Correo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);
        scrollTabla.setPreferredSize(new Dimension(800, 300));

        add(scrollTabla, BorderLayout.SOUTH);
    }

    // =========================================================================
    // MÉTODOS GETTER PARA EL CONTROLADOR Y MODIFICADORES DE TABLA/COMBOS
    // =========================================================================

    public String getNombres() { return txtNombres.getText(); }
    public String getApellidos() { return txtApellidos.getText(); }
    public String getFechaNac() { return txtFechaNac.getText(); }
    public String getCorreo() { return txtCorreo.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public String getRolSeleccionado() { return (String) cbRol.getSelectedItem(); }
    public String getAsignacionSeleccionada() { return (String) cbAsignacion.getSelectedItem(); }

    // Métodos para que el controlador escuche los botones
    public void addGuardarListener(ActionListener listener) { btnGuardar.addActionListener(listener); }
    public void addEliminarListener(ActionListener listener) { btnEliminar.addActionListener(listener); }

    // Aqui puse los mensajes de aviso y confirmación
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    public int pedirConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmar acción", JOptionPane.YES_NO_OPTION);
    }
}