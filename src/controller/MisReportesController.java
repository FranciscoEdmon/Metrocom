package controller;

import dao.ReporteDAO;
import model.JefeEstacion;
import model.Reporte;
import views.MisReportesView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        // NOTA: Para implementar esto de forma estricta, puedes agregar un método en tu 'ReporteDAO'
        // que filtre mediante: "SELECT * FROM reporte WHERE id_jefeDeEstacion = ?"
        // Por ahora, simulamos la lectura mapeando los objetos del modelo:
        List<Reporte> todos = new ArrayList<>();

        // Si tu base de datos ya tiene registros, aquí consumirías el método de consulta por ID de Jefe:
        // todos = dao.obtenerReportesPorJefe(jefeSesion.getId_jefeDeEstacion());

        for (Reporte r : todos) {
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
                consultarBD();
            } else if (comando.equals("Volver")) {
                vista.dispose();
            }
        }
    }
}