package controller;

import dao.EstacionDAO;
import model.Estacion;
import views.GestionEstacionesView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionEstacionesController {
    private GestionEstacionesView vista;
    private EstacionDAO dao;
    private int idLineaContexto; // La línea a la que pertenecen estas estaciones

    public GestionEstacionesController(GestionEstacionesView vista, EstacionDAO dao, int idLineaContexto) {
        this.vista = vista;
        this.dao = dao;
        this.idLineaContexto = idLineaContexto;

        // Aqui se inyecta el ID de la línea en el formulario visual
        this.vista.setIdLinea(String.valueOf(idLineaContexto));

        // Vinculación de eventos
        this.vista.addListeners(new BotonesListener(), new TablaClickListener());

        // Carga inicial
        actualizarTabla();
    }

    private void actualizarTabla() {
        vista.getModeloTabla().setRowCount(0);
        // Filtrado automático usando el ID de línea en el DAO
        List<Estacion> lista = dao.obtenerEstacionesPorLinea(idLineaContexto);

        for (Estacion e : lista) {
            Object[] fila = {
                    e.getId_Estacion(),
                    e.getNombreEstacion(),
                    e.isTransbordo() ? "Sí" : "No",
                    e.getId_linea()
            };
            vista.getModeloTabla().addRow(fila);
        }
    }

    private class BotonesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.equals("Agregar")) {
                if (vista.getNombreEstacion().isEmpty()) {
                    vista.mostrarMensaje("El nombre de la estación no puede estar vacío.");
                    return;
                }
                if (dao.existeEstacionConNombre(vista.getNombreEstacion(), idLineaContexto, 0)) {
                    vista.mostrarMensaje("Ya existe una estación con el nombre \"" + vista.getNombreEstacion() + "\" en esta línea. Use un nombre diferente.");
                    return;
                }
                Estacion nueva = new Estacion(
                        0, // ID autoincremental en BD
                        vista.getNombreEstacion(),
                        vista.isTransbordo(),
                        idLineaContexto
                );

                if (dao.registrarEstacion(nueva)) {
                    vista.mostrarMensaje("Estación agregada con éxito.");
                    actualizarTabla();
                    vista.limpiarFormulario();
                } else {
                    vista.mostrarMensaje("Error al registrar la estación.");
                }

            } else if (comando.equals("Modificar")) {
                if (vista.getIdEstacion().isEmpty()) {
                    vista.mostrarMensaje("Seleccione una estación de la tabla para modificar.");
                    return;
                }
                int idEst = Integer.parseInt(vista.getIdEstacion());
                if (dao.existeEstacionConNombre(vista.getNombreEstacion(), idLineaContexto, idEst)) {
                    vista.mostrarMensaje("Ya existe otra estación con el nombre \"" + vista.getNombreEstacion() + "\" en esta línea. Use un nombre diferente.");
                    return;
                }
                Estacion editada = new Estacion(
                        idEst,
                        vista.getNombreEstacion(),
                        vista.isTransbordo(),
                        idLineaContexto
                );

                if (dao.actualizarEstacion(editada)) {
                    vista.mostrarMensaje("Estación modificada con éxito.");
                    actualizarTabla();
                    vista.limpiarFormulario();
                } else {
                    vista.mostrarMensaje("Error al actualizar la estación.");
                }

            } else if (comando.equals("Eliminar")) {
                if (vista.getIdEstacion().isEmpty()) {
                    vista.mostrarMensaje("Seleccione una estación de la tabla para eliminar.");
                    return;
                }

                int confirmar = JOptionPane.showConfirmDialog(vista,
                        "¿Está seguro de eliminar esta estación?", "Confirmar", JOptionPane.YES_NO_OPTION);

                if (confirmar == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(vista.getIdEstacion());
                    if (dao.eliminarEstacion(id)) {
                        vista.mostrarMensaje("Estación eliminada.");
                        actualizarTabla();
                        vista.limpiarFormulario();
                    } else {
                        vista.mostrarMensaje("Error al eliminar la estación.");
                    }
                }

            } else if (comando.equals("Volver a Líneas")) {
                vista.dispose();
            }
        }
    }

    private class TablaClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int filaSeleccionada = vista.getTablaEstaciones().getSelectedRow();
            if (filaSeleccionada != -1) {
                String id = vista.getTablaEstaciones().getValueAt(filaSeleccionada, 0).toString();
                String nombre = vista.getTablaEstaciones().getValueAt(filaSeleccionada, 1).toString();
                String transbordoStr = vista.getTablaEstaciones().getValueAt(filaSeleccionada, 2).toString();

                vista.setIdEstacion(id);
                vista.setNombreEstacion(nombre);
                vista.setTransbordo(transbordoStr.equals("Sí"));
            }
        }
    }
}