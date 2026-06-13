package controller;

import dao.ReporteDAO;
import dao.UsuarioDAO;
import model.GerenteLinea;
import views.GerenteLineaDashboardView;
import views.BandejaEntradaView;
import views.AtencionReportesView;
import views.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenteLineaDashboardController {
    private GerenteLineaDashboardView vista;
    private GerenteLinea gerenteSesion;

    public GerenteLineaDashboardController(GerenteLineaDashboardView vista, GerenteLinea gerenteSesion) {
        this.vista = vista;
        this.gerenteSesion = gerenteSesion;
        this.vista.addListeners(new MenuGerenteListener());
    }

    private class MenuGerenteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();
            ReporteDAO reporteDAO = new ReporteDAO();

            if (comando.equals("Bandeja de Entrada (Nuevos Reportes)")) {
                BandejaEntradaView vistaBandeja = new BandejaEntradaView();
                new BandejaEntradaController(vistaBandeja, reporteDAO, gerenteSesion);
                vistaBandeja.setVisible(true);

            } else if (comando.equals("Reportes en Atención (Actualizar Estatus)")) {
                AtencionReportesView vistaAtencion = new AtencionReportesView();
                new AtencionReportesController(vistaAtencion, reporteDAO, gerenteSesion);
                vistaAtencion.setVisible(true);

            } else if (comando.equals("Cerrar Sesión")) {
                vista.dispose();
                LoginView loginVista = new LoginView();
                new LoginController(loginVista, new UsuarioDAO());
                loginVista.setVisible(true);
            }
        }
    }
}