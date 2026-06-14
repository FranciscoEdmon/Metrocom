package controller;

import dao.ReporteDAO;
import model.GerenteLinea;
import model.Reporte;
import views.BandejaEntradaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;

public class BandejaEntradaController {
    private BandejaEntradaView vista;
    private ReporteDAO dao;
    private GerenteLinea gerente;
    private DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public BandejaEntradaController(BandejaEntradaView vista, ReporteDAO dao, GerenteLinea gerente) {
        this.vista = vista;
        this.dao = dao;
        this.gerente = gerente;

        this.vista.addListeners(new AccionesBandejaListener());
        cargarReportesPendientes();
    }

    private void cargarReportesPendientes() {
        vista.getModeloTabla().setRowCount(0);

        // Consulta real filtrando por la línea asignada al gerente y estado 'Pendiente'
        int idLinea = gerente.getLineaAsignada().getId_Linea();
        List<Reporte> pendientes = dao.obtenerReportesPorLineaYEstado(idLinea, "Pendiente");

        for (Reporte r : pendientes) {
            Object[] fila = {
                    r.getId_Reporte(),
                    r.getJefeEstacion().getEstacionAsignada().getNombreEstacion(),
                    r.getTipoInfra().getTipoInfra(),
                    r.getTipoDaño().getNombreDano(),
                    r.getPrioridad().getPrioridad(),
                    r.getFechaCreacion().format(formateador)
            };
            vista.getModeloTabla().addRow(fila);
        }
    }

    private class AccionesBandejaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.equals("Aceptar y Pasar a 'En Curso'")) {
                int fila = vista.getTablaPendientes().getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(vista, "Seleccione un reporte de la tabla primero.");
                    return;
                }

                int idReporte = Integer.parseInt(vista.getTablaPendientes().getValueAt(fila, 0).toString());

                // Actualización real en la Base de Datos
                boolean exito = dao.actualizarEstadoReporte(idReporte, "En Curso");

                if (exito) {
                    JOptionPane.showMessageDialog(vista, "Reporte " + idReporte + " aceptado. Ahora está 'En Curso'.");
                    cargarReportesPendientes(); // Recarga real de la tabla
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al actualizar el estado del reporte en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else if (comando.equals("Volver")) {
                vista.dispose();
            }
        }
    }
}