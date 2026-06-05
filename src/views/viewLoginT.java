package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class viewLoginT extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public viewLoginT() {
        setTitle("MetroCom - Inicio de Sesión");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelContenido = new JPanel(new GridLayout(3, 1, 10, 10));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel pUsuario = new JPanel(new BorderLayout(5, 5));
        pUsuario.add(new JLabel("Correo Electrónico:"), BorderLayout.NORTH);
        txtCorreo = new JTextField();
        pUsuario.add(txtCorreo, BorderLayout.CENTER);

        JPanel pPassword = new JPanel(new BorderLayout(5, 5));
        pPassword.add(new JLabel("Contraseña:"), BorderLayout.NORTH);
        txtPassword = new JPasswordField();
        txtPassword.setEchoChar('*'); // Enmascaramiento
        pPassword.add(txtPassword, BorderLayout.CENTER);

        btnIngresar = new JButton("Ingresar al Sistema");

        panelContenido.add(pUsuario);
        panelContenido.add(pPassword);
        panelContenido.add(btnIngresar);

        add(panelContenido, BorderLayout.CENTER);
    }

    public String getCorreo() { return txtCorreo.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }

    public void addLoginListener(ActionListener listener) {
        btnIngresar.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Acceso", JOptionPane.ERROR_MESSAGE);
    }
}