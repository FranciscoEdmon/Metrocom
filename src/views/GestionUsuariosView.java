package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.Linea;
import model.Estacion;
import views.ViewComponetns.BotonModerno;
import views.ViewComponetns.TarjetitaModerna;
import views.ViewComponetns.VentanaBase;

public class GestionUsuariosView extends VentanaBase {
    // Componentes de formulario
    private JTextField txtNombre, txtApellidoPat, txtApellidoMat, txtCorreo, txtFechaNac;
    private JPasswordField txtContrasena;
    private JComboBox<String> cmbRol;
    private JComboBox<Linea> cmbLinea;      // Uso de clases del modelo para evitar texto libre
    private JComboBox<Estacion> cmbEstacion; // Uso de clases del modelo para evitar texto libre

    // Botones de operaciones CRUD[cite: 22]
    private JButton btnRegistrar, btnEditar, btnEliminar, btnRegresar;
    private JTable tblUsuarios; // Para listar los usuarios existentes

    public GestionUsuariosView() {
        super("MetroCom - Gestión de Usuarios", 850, 600, JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        TarjetitaModerna container = new TarjetitaModerna(new BorderLayout(10,10));

        // --- PANEL IZQUIERDO: FORMULARIO ---
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setOpaque(false);
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Operativo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: Rol
        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("Rol de Usuario:"), gbc);
        cmbRol = new JComboBox<>(new String[]{"Gerente de Línea", "Jefe de Estación"});
        gbc.gridx = 1; panelForm.add(cmbRol, gbc);

        // Fila 1: Nombre (Máx 40 caracteres)[cite: 22]
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Nombre(s):"), gbc);
        txtNombre = new JTextField(15);
        gbc.gridx = 1; panelForm.add(txtNombre, gbc);

        // Fila 2: Apellido Paterno
        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Apellido Paterno:"), gbc);
        txtApellidoPat = new JTextField(15);
        gbc.gridx = 1; panelForm.add(txtApellidoPat, gbc);

        // Fila 3: Apellido Materno
        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Apellido Materno:"), gbc);
        txtApellidoMat = new JTextField(15);
        gbc.gridx = 1; panelForm.add(txtApellidoMat, gbc);

        // Fila 4: Fecha de Nacimiento (DD/MM/AAAA)
        gbc.gridx = 0; gbc.gridy = 4; panelForm.add(new JLabel("F. Nacimiento (DD/MM/AAAA):"), gbc);
        txtFechaNac = new JTextField(15);
        gbc.gridx = 1; panelForm.add(txtFechaNac, gbc);

        // Fila 5: Correo Electrónico
        gbc.gridx = 0; gbc.gridy = 5; panelForm.add(new JLabel("Correo Institucional:"), gbc);
        txtCorreo = new JTextField(15);
        gbc.gridx = 1; panelForm.add(txtCorreo, gbc);

        // Fila 6: Contraseña
        gbc.gridx = 0; gbc.gridy = 6; panelForm.add(new JLabel("Contraseña:"), gbc);
        txtContrasena = new JPasswordField(15);
        gbc.gridx = 1; panelForm.add(txtContrasena, gbc);

        // Fila 7: Asignación de Línea (Para Gerentes/Jefes)
        gbc.gridx = 0; gbc.gridy = 7; panelForm.add(new JLabel("Línea Asignada:"), gbc);
        cmbLinea = new JComboBox<>();
        gbc.gridx = 1; panelForm.add(cmbLinea, gbc);

        // Fila 8: Asignación de Estación (Exclusivo Jefe de Estación)
        gbc.gridx = 0; gbc.gridy = 8; panelForm.add(new JLabel("Estación Asignada:"), gbc);
        cmbEstacion = new JComboBox<>();
        gbc.gridx = 1; panelForm.add(cmbEstacion, gbc);

        // Botonera de Acciones
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 5, 5));
        panelBotones.setOpaque(false);
        btnRegistrar = new BotonModerno("Registrar");
        btnEditar = new BotonModerno("Editar");
        btnEliminar = new BotonModerno("Dar de Baja");
        btnRegresar = new BotonModerno("Regresar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRegresar);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        panelForm.add(panelBotones, gbc);

        container.add(panelForm, BorderLayout.WEST);

        // --- PANEL DERECHO: TABLA DE VISUALIZACIÓN ---
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);
        panelTabla.setBorder(BorderFactory.createTitledBorder("Personal Registrado"));
        tblUsuarios = new JTable(); // Aquí mapearás el modelo de tabla en tu controlador
        panelTabla.add(new JScrollPane(tblUsuarios), BorderLayout.CENTER);

        container.add(panelTabla, BorderLayout.CENTER);

        add(container);
    }

    // Getters para recuperar la información del formulario
    public String getRolSeleccionado() { return cmbRol.getSelectedItem().toString(); }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getApellidoPat() { return txtApellidoPat.getText().trim(); }
    public String getApellidoMat() { return txtApellidoMat.getText().trim(); }
    public String getFechaNac() { return txtFechaNac.getText().trim(); }
    public String getCorreo() { return txtCorreo.getText().trim(); }
    public String getContrasena() { return new String(txtContrasena.getPassword()); }
    public Linea getLineaSeleccionada() { return (Linea) cmbLinea.getSelectedItem(); }
    public Estacion getEstacionSeleccionada() { return (Estacion) cmbEstacion.getSelectedItem(); }

    public JComboBox<Linea> getCmbLinea() { return cmbLinea; }
    public JComboBox<Estacion> getCmbEstacion() { return cmbEstacion; }
    public JTable getTblUsuarios() { return tblUsuarios; }

    // Control de eventos de botones
    public void escucharBtnRegistrar(ActionListener l) { btnRegistrar.addActionListener(l); }
    public void escucharBtnEditar(ActionListener l) { btnEditar.addActionListener(l); }
    public void escucharBtnEliminar(ActionListener l) { btnEliminar.addActionListener(l); }
    public void escucharBtnRegresar(ActionListener l) { btnRegresar.addActionListener(l); }
    public void escucharCmbRol(ActionListener l) { cmbRol.addActionListener(l); }
    public void escucharCmbLinea(ActionListener l) { cmbLinea.addActionListener(l); }

    // Requerimiento No Funcional: Mensajes de aviso / confirmación
    public boolean confirmarAccion(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(this, mensaje, "Confirmar acción", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
}