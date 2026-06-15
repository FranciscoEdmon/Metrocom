package controller;

import dao.LineaDAO;
import dao.EstacionDAO; // Agregamos la importación del DAO
import model.Linea;
import views.GestionLineasView;
import views.GestionEstacionesView; // Agregamos la importación de la Vista
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionLineasController {
    private GestionLineasView vista;
    private LineaDAO dao;

    public GestionLineasController(GestionLineasView vista, LineaDAO dao) {
        this.vista = vista;
        this.dao = dao;

        // Escuchadores de eventos
        this.vista.addListeners(new BotonesListener(), new TablaClickListener());

        // Carga inicial de datos en la tabla
        actualizarTabla();
    }

    private void actualizarTabla() {
        vista.getModeloTabla().setRowCount(0); // Reiniciar filas
        List<Linea> lista = dao.obtenerTodasLasLineas();
        for (Linea l : lista) {
            Object[] fila = {l.getId_Linea(), l.getNombreLinea(), l.getColorLinea()};
            vista.getModeloTabla().addRow(fila);
        }
    }

    private class BotonesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.equals("Agregar")) {
                if (vista.getNombreLinea().isEmpty() || vista.getColorLinea().isEmpty()) {
                    vista.mostrarMensaje("Por favor, complete todos los campos.");
                    return;
                }
                Linea nueva = new Linea(0, vista.getNombreLinea(), vista.getColorLinea());
                // boolean exito = dao.registrarLinea(nueva); // Implementar en DAO si falta
                vista.mostrarMensaje("Línea guardada exitosamente (Simulado)");
                actualizarTabla();
                vista.limpiarFormulario();

            } else if (comando.equals("Modificar")) {
                if (vista.getIdLinea().isEmpty()) {
                    vista.mostrarMensaje("Seleccione una línea de la tabla para modificar.");
                    return;
                }
                int id = Integer.parseInt(vista.getIdLinea());
                Linea editada = new Linea(id, vista.getNombreLinea(), vista.getColorLinea());
                // dao.actualizarLinea(editada);
                vista.mostrarMensaje("Línea actualizada exitosamente");
                actualizarTabla();
                vista.limpiarFormulario();

            } else if (comando.equals("Eliminar")) {
                if (vista.getIdLinea().isEmpty()) {
                    vista.mostrarMensaje("Seleccione una línea de la tabla para eliminar.");
                    return;
                }
                int id = Integer.parseInt(vista.getIdLinea());
                // dao.eliminarLinea(id);
                vista.mostrarMensaje("Línea eliminada.");
                actualizarTabla();
                vista.limpiarFormulario();

            } else if (comando.equals("Volver al Panel")) {
                vista.dispose();

            } else if (comando.equals("Ver Estaciones →")) {
                if (vista.getIdLinea().isEmpty()) {
                    vista.mostrarMensaje("Por favor, seleccione una línea de la tabla para ver sus estaciones.");
                    return;
                }
                int idLineaSeleccionada = Integer.parseInt(vista.getIdLinea());
                GestionEstacionesView vistaEstaciones = new GestionEstacionesView();
                EstacionDAO estacionDAO = new EstacionDAO();
                new GestionEstacionesController(vistaEstaciones, estacionDAO, idLineaSeleccionada);
                vistaEstaciones.setVisible(true);
            }
        }
    }

    private class TablaClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int filaSeleccionada = vista.getTablaLineas().getSelectedRow();
            if (filaSeleccionada != -1) {
                String id = vista.getTablaLineas().getValueAt(filaSeleccionada, 0).toString();
                String nombre = vista.getTablaLineas().getValueAt(filaSeleccionada, 1).toString();
                String color = vista.getTablaLineas().getValueAt(filaSeleccionada, 2).toString();
                vista.llenarCamposFormulario(id, nombre, color);
            }
        }
    }
}