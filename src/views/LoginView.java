package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;

    public LoginView() {
        // Configuración de la ventana básica
        setTitle("MetroCom - Iniciar Sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Inicializar componentes
        txtCorreo = new JTextField(20);
        txtContrasena = new JPasswordField(20);
        btnIngresar = new JButton("Ingresar");

        // Diseño rápido (Layout)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        panel.add(new JLabel("Correo Electrónico:"));
        panel.add(txtCorreo);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasena);
        panel.add(new JLabel("")); // Espacio vacío
        panel.add(btnIngresar);

        add(panel);
    }

    // Métodos para que el controlador extraiga la información de forma segura
    public String getCorreo() {
        return txtCorreo.getText().trim();
    }

    public String getContrasena() {
        return new String(txtContrasena.getPassword());
    }

    // Este método crucial permite al controlador "escuchar" el clic del botón
    public void addLoginListener(ActionListener listener) {
        btnIngresar.addActionListener(listener);
    }

    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
    }
}