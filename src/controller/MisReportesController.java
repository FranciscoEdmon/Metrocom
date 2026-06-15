package controller;

import dao.ReporteDAO;
import model.JefeEstacion;
import model.Reporte;
import views.MisReportesView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MisReportesController {
    private MisReportesView vista;
    private ReporteDAO dao;
    private JefeEstacion jefeSesion;
    private DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MisReportesController(MisReportesView vista, ReporteDAO dao, JefeEstacion jefe) {
        this.vista = vista;
        this.dao = dao;
        this.jefeSesion = jefe;

        this.vista.addListeners(new HistorialAccionesListener());
        consultarBD();
    }

    private void consultarBD() {
        vista.getModeloTabla().setRowCount(0);

        // Consulta para un filtrado por el ID de la sesión del Jefe de Estación actual
        List<Reporte> misReportes = dao.obtenerReportesPorJefe(jefeSesion.getId_jefeDeEstacion());

        for (Reporte r : misReportes) {
            Object[] fila = {
                    r.getId_Reporte(),
                    r.getFechaCreacion().format(formateador),
                    r.getTipoInfra().getTipoInfra(),
                    r.getTipoDaño().getNombreDano(),
                    r.getUbicacionExacta(),
                    r.getPrioridad().getPrioridad(),
                    r.getEstado()
            };
            vista.getModeloTabla().addRow(fila);
        }
    }

    private class HistorialAccionesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();
            if (comando.equals("Sincronizar Tabla")) {
                consultarBD(); // Hace una petición síncrona real a MySQL para traer datos nuevos
            } else if (comando.equals("Volver")) {
                vista.dispose();
            }
        }
    }
}