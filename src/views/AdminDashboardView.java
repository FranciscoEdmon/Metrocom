package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminDashboardView extends JFrame {
    private JButton btnGestionarLineas;
    private JButton btnGestionarUsuarios;
    private JButton btnCerrarSesion;
    private JLabel lblBienvenida;

    public AdminDashboardView() {
        // Configuración de la ventana
        setTitle("MetroCom - Panel de Administración");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Componentes
        lblBienvenida = new JLabel("Bienvenido, Administrador", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));

        btnGestionarLineas = new JButton("Gestionar Líneas (CRUD)");
        btnGestionarUsuarios = new JButton("Gestionar Usuarios (CRUD)");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        // Estilo rápido a los botones para que se distingan
        btnGestionarLineas.setPreferredSize(new Dimension(200, 50));
        btnGestionarUsuarios.setPreferredSize(new Dimension(200, 50));

        // Layout de los botones principales
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelBotones.add(btnGestionarLineas, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelBotones.add(btnGestionarUsuarios, gbc);

        // Panel inferior para el botón de cerrar sesión
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.add(btnCerrarSesion);

        // Agregar al contenedor principal
        setLayout(new BorderLayout(20, 20));
        add(lblBienvenida, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    // Método para personalizar el mensaje de bienvenida con el nombre real
    public void setNombreAdministrador(String nombre) {
        lblBienvenida.setText("Bienvenido, Administrador: " + nombre);
    }

    // Listeners para que el controlador escuche las acciones
    public void addGestionarLineasListener(ActionListener listener) {
        btnGestionarLineas.addActionListener(listener);
    }

    public void addGestionarUsuariosListener(ActionListener listener) {
        btnGestionarUsuarios.addActionListener(listener);
    }

    public void addCerrarSesionListener(ActionListener listener) {
        btnCerrarSesion.addActionListener(listener);
    }
}