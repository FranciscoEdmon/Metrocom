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
                if (lineaDAO.existeLineaConNombre(vista.getNombreLinea(), 0)) {
                    vista.mostrarMensaje("Ya existe una línea con el nombre \"" + vista.getNombreLinea() + "\". Use un nombre diferente.");
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
                if (lineaDAO.existeLineaConNombre(vista.getNombreLinea(), id)) {
                    vista.mostrarMensaje("Ya existe otra línea con el nombre \"" + vista.getNombreLinea() + "\". Use un nombre diferente.");
                    return;
                }
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
                int confirm = JOptionPane.showConfirmDialog(vista,
                        "¿Está seguro de eliminar la línea con ID: " + id + "?\nEsto eliminará también sus estaciones asociadas.",
                        "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (lineaDAO.eliminarLinea(id)) {
                        vista.mostrarMensaje("Línea eliminada correctamente.");
                        actualizarTablaLineas();
                        vista.limpiarFormulario();
                    } else {
                        vista.mostrarMensaje("Error al eliminar la línea. Intente de nuevo.");
                    }
                }

            } else if (comando.equals("Volver al Panel")) {
                vista.dispose();

            } else if (comando.equals("Ver Estaciones →")) {
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

            if (comando.equals("Añadir Estación")) {
                if (vista.getNombreEstacion().isEmpty()) {
                    vista.mostrarMensaje("El nombre de la estación no puede estar vacío.");
                    return;
                }
                if (estacionDAO.existeEstacionConNombre(vista.getNombreEstacion(), idLineaActual, 0)) {
                    vista.mostrarMensaje("Ya existe una estación con el nombre \"" + vista.getNombreEstacion() + "\" en esta línea. Use un nombre diferente.");
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

            } else if (comando.equals("Modificar Est.")) {
                if (vista.getIdEstacion().isEmpty()) {
                    vista.mostrarMensaje("Seleccione una estación de la tabla para modificar.");
                    return;
                }
                int idEst = Integer.parseInt(vista.getIdEstacion());
                if (estacionDAO.existeEstacionConNombre(vista.getNombreEstacion(), idLineaActual, idEst)) {
                    vista.mostrarMensaje("Ya existe otra estación con el nombre \"" + vista.getNombreEstacion() + "\" en esta línea. Use un nombre diferente.");
                    return;
                }
                Estacion editada = new Estacion(
                        idEst,
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

            } else if (comando.equals("Eliminar Est.")) {
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

            } else if (comando.equals("← Volver a Líneas")) {
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