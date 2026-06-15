package controller;

import dao.*;
import model.*;
import views.ReporteNuevoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

public class ReporteNuevoController {
    private ReporteNuevoView vista;
    private ReporteDAO reporteDAO;
    private JefeEstacion jefeSesion;

    public ReporteNuevoController(ReporteNuevoView vista, ReporteDAO rDAO, TipoInfraDAO iDAO, TipoDanoDAO dDAO, PrioridadDAO pDAO, JefeEstacion jefe) {
        this.vista = vista;
        this.reporteDAO = rDAO;
        this.jefeSesion = jefe;

        this.vista.addListeners(new AccionesFormularioListener());
        cargarCatalogos(iDAO, dDAO, pDAO);
    }

    private void cargarCatalogos(TipoInfraDAO iDAO, TipoDanoDAO dDAO, PrioridadDAO pDAO) {
        // Carga dinámica usando los métodos nativos de los DAO
        for (TipoInfra ti : iDAO.ObetenerTodosLosTInfra()) {
            vista.agregarInfraestructura(ti);
        }
        for (TipoDano td : dDAO.ObtenerLosTDano()) {
            vista.agregarDaño(td);
        }
        for (Prioridad p : pDAO.ObtenerLasPrioridades()) {
            vista.agregarPrioridad(p);
        }
    }

    private class AccionesFormularioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String op = e.getActionCommand();

            if (op.equals("Enviar Reporte")) {
                if (vista.getUbicacionExacta().isEmpty() || vista.getDescripcion().isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "La ubicación exacta y la descripción son obligatorias.");
                    return;
                }

                // Armar entidad completa respetando el constructor del modelo 'Reporte'
                Reporte nuevoReporte = new Reporte(
                        0,
                        "Pendiente", //Con este le pongo un estado por defecto
                        vista.getUbicacionExacta(),
                        vista.getDescripcion(),
                        LocalDateTime.now(),
                        jefeSesion,
                        vista.getPrioridadSeleccionada(),
                        vista.getInfraSeleccionada(),
                        vista.getDañoSeleccionado()
                );

                boolean exito = reporteDAO.registrarReporte(nuevoReporte);
                if (exito) {
                    JOptionPane.showMessageDialog(vista, "El reporte se cargó al sistema en estado PENDIENTE.");
                    vista.dispose();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error de red o consistencia al insertar en la base de datos.");
                }

            } else if (op.equals("Cancelar")) {
                vista.dispose();
            }
        }
    }
}