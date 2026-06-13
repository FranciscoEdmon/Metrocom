package controller;

import model.Administrador;
import views.AdminDashboardView;
import views.LoginView;
import dao.UsuarioDAO;
import dao.LineaDAO;
import views.GestionUsuariosView;
import views.GestionLineasView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardController {
    private AdminDashboardView vista;
    private Administrador adminLogueado;

    public AdminDashboardController(AdminDashboardView vista, Administrador admin) {
        this.vista = vista;
        this.adminLogueado = admin;

        // Personalizar la vista con los datos del modelo
        this.vista.setNombreAdministrador(adminLogueado.getNombre() + " " + adminLogueado.getApellidoPat());

        // Vincular los eventos de los botones
        this.vista.addGestionarLineasListener(new LineasButtonListener());
        this.vista.addGestionarUsuariosListener(new UsuariosButtonListener());
        this.vista.addCerrarSesionListener(new CerrarSesionButtonListener());
    }

    // Escuchador para abrir la gestión de líneas
    private class LineasButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GestionLineasView vistaLineas = new GestionLineasView();
            LineaDAO lineaDAO = new LineaDAO();
            new GestionLineasController(vistaLineas, lineaDAO);
            vistaLineas.setVisible(true);
        }
    }

    // Escuchador para abrir la gestión de usuarios
    private class UsuariosButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GestionUsuariosView vistaUsuarios = new GestionUsuariosView();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            new GestionUsuariosController(vistaUsuarios, usuarioDAO);
            vistaUsuarios.setVisible(true);
        }
    }

    // Escuchador para regresar al Login
    private class CerrarSesionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Cerramos el panel actual
            vista.dispose();

            // Reabrimos el Login de forma limpia
            LoginView loginVista = new LoginView();
            UsuarioDAO loginDAO = new UsuarioDAO();
            new LoginController(loginVista, loginDAO);
            loginVista.setVisible(true);
        }
    }
}