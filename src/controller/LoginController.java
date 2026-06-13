package controller;

import dao.UsuarioDAO;
import model.*;
import views.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView vista;
    private UsuarioDAO dao;

    public LoginController(LoginView vista, UsuarioDAO dao) {
        this.vista = vista;
        this.dao = dao;

        // Le decimos a la vista que nosotros manejaremos el evento del botón
        this.vista.addLoginListener(new LoginButtonListener());
    }

    // Clase interna que maneja el evento del clic
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String correo = vista.getCorreo();
            String contrasena = vista.getContrasena();

            // Validación simple antes de ir a la base de datos
            if (correo.isEmpty() || contrasena.isEmpty()) {
                vista.mostrarMensajeError("Por favor, rellene todos los campos.");
                return;
            }

            // Consultamos al DAO para verificar credenciales
            Usuario usuarioLogueado = dao.validarLogin(correo, contrasena);

            if (usuarioLogueado != null) {
                // El login fue exitoso. Evaluamos el rol para redirigir
                redireccionarUsuario(usuarioLogueado);
                vista.dispose(); // Cerramos la ventana de Login
            } else {
                vista.mostrarMensajeError("Correo o contraseña incorrectos. Intente de nuevo.");
            }
        }
    }

    private void redireccionarUsuario(Usuario usuario) {
        // Evaluamos el tipo de objeto específico que retornó el Login
        if (usuario instanceof Administrador) {
            Administrador admin = (Administrador) usuario;
            System.out.println("Abriendo Dashboard de Administrador para: " + admin.getNombre());
            // Aquí instanciarás tu AdminDashboardView y su controlador:
            // AdminDashboardView view = new AdminDashboardView(admin);
            // new AdminDashboardController(view, admin);
            // view.setVisible(true);

        } else if (usuario instanceof JefeEstacion) {
            JefeEstacion jefe = (JefeEstacion) usuario;
            System.out.println("Abriendo Dashboard de Jefe de Estación en: " + jefe.getEstacionAsignada());
            // JefeDashboardView view = new JefeDashboardView(jefe);
            // new JefeDashboardController(view, jefe);
            // view.setVisible(true);

        } else if (usuario instanceof GerenteLinea) {
            GerenteLinea gerente = (GerenteLinea) usuario;
            System.out.println("Abriendo Dashboard de Gerente para la Línea: " + gerente.getLineaAsignada());
            // GerenteDashboardView view = new GerenteDashboardView(gerente);
            // new GerenteDashboardController(view, gerente);
            // view.setVisible(true);
        }
    }
}