package controller;

import dao.EstacionDAO;
import dao.LineaDAO;
import model.Estacion;
import model.Linea;
import views.GestionLineasView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionLineasController {
    private GestionLineasView vista;
    private LineaDAO lineaDAO;
    private EstacionDAO estacionDAO;
    private int idLineaActual; // Línea donde las estaciones se están gestionando

    public GestionLineasController(GestionLineasView vista, LineaDAO lineaDAO, EstacionDAO estacionDAO) {
        this.vista = vista;
        this.lineaDAO = lineaDAO;
        this.estacionDAO = estacionDAO;

        this.vista.addListeners(new BotonesLineaListener(), new TablaLineaClickListener());
        this.vista.addEstacionesListeners(new BotonesEstacionListener(), new TablaEstacionClickListener());

        actualizarTablaLineas();
    }

    // ==========================================
    // TABLA DE LÍNEAS
    // ==========================================

    private void actualizarTablaLineas() {
        vista.getModeloTabla().setRowCount(0);
        List<Linea> lista = lineaDAO.obtenerTodasLasLineas();
        for (Linea l : lista) {
            vista.getModeloTabla().addRow(new Object[]{l.getId_Linea(), l.getNombreLinea(), l.getColorLinea()});
        }
    }

    private class BotonesLineaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.equals("Agregar")) {
                if (vista.getNombreLinea().isEmpty() || vista.getColorLinea().isEmpty()) {
                    vista.mostrarMensaje("Por favor, complete todos los campos.");
                    return;
                }
                Linea nueva = new Linea(0, vista.getNombreLinea(), vista.getColorLinea());
                if (lineaDAO.registrarLinea(nueva)) {
                    vista.mostrarMensaje("Línea guardada exitosamente.");
                    actualizarTablaLineas();
                    vista.limpiarFormulario();
                } else {
                    vista.mostrarMensaje("Error al guardar la línea. Intente de nuevo.");
                }

            } else if (comando.equals("Modificar")) {
                if (vista.getIdLinea().isEmpty()) {
                    vista.mostrarMensaje("Seleccione una línea de la tabla para modificar.");
                    return;
                }
                int id = Integer.parseInt(vista.getIdLinea());
                Linea editada = new Linea(id, vista.getNombreLinea(), vista.getColorLinea());
                if (lineaDAO.actualizarLinea(editada)) {
                    vista.mostrarMensaje("Línea actualizada exitosamente.");
                    actualizarTablaLineas();
                    vista.limpiarFormulario();
                } else {
                    vista.mostrarMensaje("Error al actualizar la línea. Intente de nuevo.");
                }

            } else if (comando.equals("Eliminar")) {
                if (vista.getIdLinea().isEmpty()) {
                    vista.mostrarMensaje("Seleccione una línea de la tabla para eliminar.");
                    return;
                }
                int id = Integer.parseInt(vista.getIdLinea());
                // dao.eliminarLinea(id); // Pendiente de implementar en LineaDAO
                vista.mostrarMensaje("Línea eliminada.");
                actualizarTablaLineas();
                vista.limpiarFormulario();

            } else if (comando.equals("Volver al Panel")) {
                vista.dispose();

            } else if (comando.equals("Ver Estaciones")) {
                if (vista.getIdLinea().isEmpty()) {
                    vista.mostrarMensaje("Seleccione una línea de la tabla para ver sus estaciones.");
                    return;
                }
                idLineaActual = Integer.parseInt(vista.getIdLinea());
                actualizarTablaEstaciones();
                vista.alternarVistaEstaciones(true);
            }
        }
    }

    private class TablaLineaClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int fila = vista.getTablaLineas().getSelectedRow();
            if (fila != -1) {
                String id     = vista.getTablaLineas().getValueAt(fila, 0).toString();
                String nombre = vista.getTablaLineas().getValueAt(fila, 1).toString();
                String color  = vista.getTablaLineas().getValueAt(fila, 2).toString();
                vista.llenarCamposFormulario(id, nombre, color);
            }
        }
    }

    private void actualizarTablaEstaciones() {
        vista.getModeloTablaEstaciones().setRowCount(0);
        List<Estacion> lista = estacionDAO.obtenerEstacionesPorLinea(idLineaActual);
        for (Estacion est : lista) {
            vista.getModeloTablaEstaciones().addRow(new Object[]{
                    est.getId_Estacion(),
                    est.getNombreEstacion(),
                    est.isTransbordo() ? "Sí" : "No"
            });
        }
    }

    private class BotonesEstacionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.equals("AgregarEstacion")) {
                if (vista.getNombreEstacion().isEmpty()) {
                    vista.mostrarMensaje("El nombre de la estación no puede estar vacío.");
                    return;
                }
                Estacion nueva = new Estacion(0, vista.getNombreEstacion(), vista.getIsTransbordo(), idLineaActual);
                if (estacionDAO.registrarEstacion(nueva)) {
                    vista.mostrarMensaje("Estación agregada con éxito.");
                    actualizarTablaEstaciones();
                    vista.limpiarFormularioEstacion();
                } else {
                    vista.mostrarMensaje("Error al registrar la estación.");
                }

            } else if (comando.equals("ModificarEstacion")) {
                if (vista.getIdEstacion().isEmpty()) {
                    vista.mostrarMensaje("Seleccione una estación de la tabla para modificar.");
                    return;
                }
                Estacion editada = new Estacion(
                        Integer.parseInt(vista.getIdEstacion()),
                        vista.getNombreEstacion(),
                        vista.getIsTransbordo(),
                        idLineaActual
                );
                if (estacionDAO.actualizarEstacion(editada)) {
                    vista.mostrarMensaje("Estación modificada con éxito.");
                    actualizarTablaEstaciones();
                    vista.limpiarFormularioEstacion();
                } else {
                    vista.mostrarMensaje("Error al actualizar la estación.");
                }

            } else if (comando.equals("EliminarEstacion")) {
                if (vista.getIdEstacion().isEmpty()) {
                    vista.mostrarMensaje("Seleccione una estación de la tabla para eliminar.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(vista,
                        "¿Está seguro de eliminar esta estación?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(vista.getIdEstacion());
                    if (estacionDAO.eliminarEstacion(id)) {
                        vista.mostrarMensaje("Estación eliminada.");
                        actualizarTablaEstaciones();
                        vista.limpiarFormularioEstacion();
                    } else {
                        vista.mostrarMensaje("Error al eliminar la estación.");
                    }
                }

            } else if (comando.equals("RegresarALineas")) {
                vista.limpiarFormularioEstacion();
                vista.alternarVistaEstaciones(false);
            }
        }
    }

    private class TablaEstacionClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int fila = vista.getTablaEstaciones().getSelectedRow();
            if (fila != -1) {
                String id        = vista.getTablaEstaciones().getValueAt(fila, 0).toString();
                String nombre    = vista.getTablaEstaciones().getValueAt(fila, 1).toString();
                String transbordo = vista.getTablaEstaciones().getValueAt(fila, 2).toString();
                vista.llenarCamposEstacion(id, nombre, transbordo.equals("Sí"));
            }
        }
    }
}