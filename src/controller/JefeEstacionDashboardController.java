package controller;

import model.JefeEstacion;
import views.JefeEstacionDashboardView;
import views.ReporteNuevoView;
import views.MisReportesView;
import views.LoginView;
import dao.UsuarioDAO;
import dao.ReporteDAO;
import dao.TipoInfraDAO;
import dao.TipoDanoDAO;
import dao.PrioridadDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JefeEstacionDashboardController {
    private JefeEstacionDashboardView vista;
    private JefeEstacion jefeSesion;

    public JefeEstacionDashboardController(JefeEstacionDashboardView vista, JefeEstacion jefeSesion) {
        this.vista = vista;
        this.jefeSesion = jefeSesion;
        this.vista.addListeners(new DashboardMenuListener());
    }

    private class DashboardMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.equals("Reportar Nueva Falla / Incidente")) {
                // Instanciar componentes del sub-módulo Reporte Nuevo
                ReporteNuevoView vistaNuevo = new ReporteNuevoView();
                ReporteDAO rDAO = new ReporteDAO();
                TipoInfraDAO infraDAO = new TipoInfraDAO();
                TipoDanoDAO danoDAO = new TipoDanoDAO();
                PrioridadDAO prioDAO = new PrioridadDAO();

                new ReporteNuevoController(vistaNuevo, rDAO, infraDAO, danoDAO, prioDAO, jefeSesion);
                vistaNuevo.setVisible(true);

            } else if (comando.equals("Ver Mis Reportes Enviados")) {
                // Instanciar componentes del sub-módulo Mis Reportes
                MisReportesView vistaMisReportes = new MisReportesView();
                ReporteDAO rDAO = new ReporteDAO();

                new MisReportesController(vistaMisReportes, rDAO, jefeSesion);
                vistaMisReportes.setVisible(true);

            } else if (comando.equals("Cerrar Sesión")) {
                vista.dispose();
                LoginView loginVista = new LoginView();
                new LoginController(loginVista, new UsuarioDAO());
                loginVista.setVisible(true);
            }
        }
    }
}