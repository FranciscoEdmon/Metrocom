package controller;

import dao.ReporteDAO;
import model.GerenteLinea;
import views.BandejaEntradaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class BandejaEntradaController {
    private BandejaEntradaView vista;
    private ReporteDAO dao;
    private GerenteLinea gerente;

    public BandejaEntradaController(BandejaEntradaView vista, ReporteDAO dao, GerenteLinea gerente) {
        this.vista = vista;
        this.dao = dao;
        this.gerente = gerente;

        this.vista.addListeners(new AccionesBandejaListener());
        cargarReportesPendientes();
    }

    private void cargarReportesPendientes() {
        vista.getModeloTabla().setRowCount(0);
        // Aquí consumirás el DAO para buscar por línea y estado = 'Pendiente'
        // Ejemplo visual de inserción mock:
        Object[] filaMock = {"101", "Balderas", "Elevador", "Puerta atascada", "Alta", "25/05/2026"};
        vista.getModeloTabla().addRow(filaMock);
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

                String idReporte = vista.getTablaPendientes().getValueAt(fila, 0).toString();

                // NOTA TÉCNICA: Debes agregar este método en tu ReporteDAO
                // boolean exito = dao.actualizarEstadoReporte(Integer.parseInt(idReporte), "En Curso");

                JOptionPane.showMessageDialog(vista, "Reporte " + idReporte + " aceptado. Ahora está 'En Curso'.");
                cargarReportesPendientes(); // Recarga la tabla para que desaparezca el aceptado

            } else if (comando.equals("Volver")) {
                vista.dispose();
            }
        }
    }
}