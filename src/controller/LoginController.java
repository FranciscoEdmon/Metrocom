package controller;

import dao.UsuarioDAO;
import dao.LineaDAO;       // Importación agregada
import dao.EstacionDAO;    // Importación agregada
import model.*;
import views.AdminDashboardView;
import views.GerenteLineaDashboardView;
import views.JefeEstacionDashboardView;
import views.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView vista;
    private UsuarioDAO dao;
    private LineaDAO lineaDAO;       // Atributo agregado
    private EstacionDAO estacionDAO; // Atributo agregado

    // Constructor actualizado para recibir todas las dependencias desde el Main
    public LoginController(LoginView vista, UsuarioDAO dao, LineaDAO lineaDAO, EstacionDAO estacionDAO) {
        this.vista = vista;
        this.dao = dao;
        this.lineaDAO = lineaDAO;
        this.estacionDAO = estacionDAO;

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

            // 1. Creamos la vista del Dashboard
            AdminDashboardView dashboardVista = new AdminDashboardView();

            // 2. Creamos su controlador pasándole la vista, el modelo del admin
            // y además los DAOs necesarios para cuando abra la gestión de usuarios u otras ventanas
            new AdminDashboardController(dashboardVista, admin, dao, lineaDAO, estacionDAO);

            // 3. Lo hacemos visible
            dashboardVista.setVisible(true);

        } else if (usuario instanceof JefeEstacion) {
            JefeEstacion jefe = (JefeEstacion) usuario;
            System.out.println("Abriendo Dashboard de Jefe de Estación en: " + jefe.getEstacionAsignada());
            JefeEstacionDashboardView dashboardVista = new JefeEstacionDashboardView(jefe);
            new JefeEstacionDashboardController(dashboardVista, jefe);
            dashboardVista.setVisible(true);

        } else if (usuario instanceof GerenteLinea) {
            GerenteLinea gerente = (GerenteLinea) usuario;
            System.out.println("Abriendo Dashboard de Gerente para la Línea: " + gerente.getLineaAsignada());
            GerenteLineaDashboardView view = new GerenteLineaDashboardView(gerente);
            new GerenteLineaDashboardController(view, gerente);
            view.setVisible(true);
        }
    }
}