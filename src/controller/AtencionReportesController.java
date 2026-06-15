package controller;

import dao.ReporteDAO;
import model.GerenteLinea;
import model.Reporte;
import views.AtencionReportesView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

public class AtencionReportesController {
    private AtencionReportesView vista;
    private ReporteDAO dao;
    private GerenteLinea gerente;

    public AtencionReportesController(AtencionReportesView vista, ReporteDAO dao, GerenteLinea gerente) {
        this.vista = vista;
        this.dao = dao;
        this.gerente = gerente;

        this.vista.addListeners(new AccionesAtencionListener());
        cargarReportesEnCurso();
    }

    private void cargarReportesEnCurso() {
        vista.getModeloTabla().setRowCount(0);

        // Consulta a la base de datos de los reportes activos 'En Curso'
        int idLinea = gerente.getLineaAsignada().getId_Linea();
        List<Reporte> enCurso = dao.obtenerReportesPorLineaYEstado(idLinea, "En Curso");

        for (Reporte r : enCurso) {
            Object[] fila = {
                    r.getId_Reporte(),
                    r.getJefeEstacion().getEstacionAsignada().getNombreEstacion(),
                    r.getTipoInfra().getTipoInfra(),
                    r.getDescripcion(),
                    r.getPrioridad().getPrioridad()
            };
            vista.getModeloTabla().addRow(fila);
        }
    }

    private class AccionesAtencionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.equals("Marcar Mantenimiento como 'Completado'")) {
                int fila = vista.getTablaEnCurso().getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Seleccione un reporte para finalizar.");
                    return;
                }

                int idReporte = Integer.parseInt(vista.getTablaEnCurso().getValueAt(fila, 0).toString());

                // Actualización en la base de datos
                boolean exito = dao.actualizarEstadoReporte(idReporte, "Completado");

                if (exito) {
                    JOptionPane.showMessageDialog(vista, "Mantenimiento " + idReporte + " registrado como Completado.");
                    cargarReportesEnCurso(); // Refrescar componentes visuales con la BD limpia
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al finalizar el mantenimiento en el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else if (comando.equals("Volver")) {
                vista.dispose();
            }
        }
    }
}