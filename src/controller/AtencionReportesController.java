package controller;

import dao.ReporteDAO;
import model.GerenteLinea;
import views.AtencionReportesView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        // Aquí consumirás el DAO para buscar por línea y estado = 'En Curso'
        // Ejemplo visual de inserción mock:
        Object[] filaMock = {"101", "Balderas", "Elevador", "Reemplazo de motor de puerta", "Alta"};
        vista.getModeloTabla().addRow(filaMock);
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

                String idReporte = vista.getTablaEnCurso().getValueAt(fila, 0).toString();

                // NOTA TÉCNICA: Usar el mismo método agregado en ReporteDAO
                // boolean exito = dao.actualizarEstadoReporte(Integer.parseInt(idReporte), "Completado");

                JOptionPane.showMessageDialog(vista, "Mantenimiento " + idReporte + " registrado como Completado.");
                cargarReportesEnCurso();

            } else if (comando.equals("Volver")) {
                vista.dispose();
            }
        }
    }
}