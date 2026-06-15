package controller;

import model.Administrador;
import views.AdminDashboardView;
import views.LoginView;
import dao.UsuarioDAO;
import dao.LineaDAO;
import dao.EstacionDAO; // Asegúrate de incluir esta importación
import views.GestionUsuariosView;
import views.GestionLineasView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardController {
    private AdminDashboardView vista;
    private Administrador adminLogueado;
    private UsuarioDAO usuarioDAO;
    private LineaDAO lineaDAO;
    private EstacionDAO estacionDAO;

    // CORRECCIÓN: El constructor ahora acepta y almacena todas las dependencias compartidas
    public AdminDashboardController(AdminDashboardView vista, Administrador admin, UsuarioDAO uDAO, LineaDAO lDAO, EstacionDAO eDAO) {
        this.vista = vista;
        this.adminLogueado = admin;
        this.usuarioDAO = uDAO;
        this.lineaDAO = lDAO;
        this.estacionDAO = eDAO;

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
            new GestionLineasController(vistaLineas, lineaDAO);
            vistaLineas.setVisible(true);
        }
    }

    // Escuchador para abrir la gestión de usuarios
    private class UsuariosButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GestionUsuariosView vistaUsuarios = new GestionUsuariosView();

            // CORRECCIÓN: Ahora pasamos las 4 dependencias requeridas por el nuevo constructor
            new GestionUsuariosController(vistaUsuarios, usuarioDAO, lineaDAO, estacionDAO);
            vistaUsuarios.setVisible(true);
        }
    }

    // Escuchador para regresar al Login
    private class CerrarSesionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
            LoginView loginVista = new LoginView();
            UsuarioDAO loginDAO = new UsuarioDAO();
            LineaDAO lDAO = new LineaDAO();
            EstacionDAO eDAO = new EstacionDAO();
            new LoginController(loginVista, loginDAO, lDAO, eDAO);
            loginVista.setVisible(true);
        }
    }
}