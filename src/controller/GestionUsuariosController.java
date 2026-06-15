package controller;

import dao.UsuarioDAO;
import dao.LineaDAO;
import dao.EstacionDAO;
import model.*;
import views.GestionUsuariosView;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class GestionUsuariosController {
    private GestionUsuariosView vista;
    private UsuarioDAO usuarioDAO;
    private LineaDAO lineaDAO;
    private EstacionDAO estacionDAO;

    public GestionUsuariosController(GestionUsuariosView vista, UsuarioDAO uDAO, LineaDAO lDAO, EstacionDAO eDAO) {
        this.vista = vista;
        this.usuarioDAO = uDAO;
        this.lineaDAO = lDAO;
        this.estacionDAO = eDAO;

        // Primero hago una carga inicial de datos desde la base de datos
        cargarLineas();
        cargarListaDeUsuarios();

        // Despues configuro los listeners de eventos
        this.vista.addListeners(new BotonesHandler(), new FilaSeleccionadaHandler(), new LineaComboHandler());
    }

    // ==========================================
    // MÉTODOS DE CARGA DE DATOS
    // ==========================================

    private void cargarLineas() {
        DefaultComboBoxModel<Linea> model = new DefaultComboBoxModel<>();
        List<Linea> lista = lineaDAO.obtenerTodasLasLineas();
        for (Linea l : lista) {
            model.addElement(l);
        }
        vista.setModeloLineas(model);

        // Al cargar las líneas, actualizamos automáticamente las estaciones de la primera línea
        actualizarEstaciones();
    }

    private void actualizarEstaciones() {
        Linea seleccionada = vista.getLineaSeleccionada();
        if (seleccionada != null) {
            DefaultComboBoxModel<Estacion> model = new DefaultComboBoxModel<>();
            List<Estacion> lista = estacionDAO.obtenerEstacionesPorLinea(seleccionada.getId_Linea());
            for (Estacion e : lista) {
                model.addElement(e);
            }
            vista.setModeloEstaciones(model);
        }
    }

    private void cargarListaDeUsuarios() {
        vista.getModeloTabla().setRowCount(0);
        List<Usuario> lista = usuarioDAO.obtenerTodosLosUsuarios();

        for (Usuario u : lista) {
            String nombreCompleto = u.getNombre() + " " + u.getApellidoPat() + " " + u.getApellidoMat();
            String rol = "Usuario";

            if (u instanceof Administrador) rol = "Administrador";
            else if (u instanceof JefeEstacion) rol = "Jefe de Estación";
            else if (u instanceof GerenteLinea) rol = "Gerente de Línea";

            vista.getModeloTabla().addRow(new Object[]{
                    u.getId_Usuario(), nombreCompleto, u.getCorreo(), rol
            });
        }
    }

    // ==========================================
    // ESCUCHADORES DE EVENTOS
    // ==========================================

    private class LineaComboHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Este se dispara cada vez que el usuario elige una línea diferente en el ComboBox
            actualizarEstaciones();
        }
    }

    private class BotonesHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.equals("Registrar")) {
                ejecutarRegistro();
            } else if (comando.equals("Actualizar") || comando.equals("Modificar")) {
                ejecutarActualizacion();
            } else if (comando.equals("Dar de Baja")) {
                ejecutarEliminacion();
            } else if (comando.equals("Volver al Panel")) {
                vista.dispose();
            }
        }
    }

    // ==========================================
    // LÓGICA DE CRUD
    // ==========================================

    private void ejecutarRegistro() {
        if (vista.getNombre().isEmpty() || vista.getCorreo().isEmpty() || vista.getContrasena().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, llene los campos obligatorios.");
            return;
        }

        try {
            LocalDate fecha = LocalDate.parse(vista.getFechaNacimiento());
            String rol = vista.getRolSeleccionado();

            // Extraer IDs de los objetos seleccionados en los ComboBox
            int idLinea = 0;
            int idEstacion = 0;

            if (vista.getLineaSeleccionada() != null) {
                idLinea = vista.getLineaSeleccionada().getId_Linea();
            }
            if (vista.getEstacionSeleccionada() != null) {
                idEstacion = vista.getEstacionSeleccionada().getId_Estacion();
            }

            // Validaciones de seguridad para roles
            if (rol.equals("Gerente de Línea") && idLinea <= 0) {
                JOptionPane.showMessageDialog(vista, "Seleccione una línea válida para el Gerente.");
                return;
            }
            if (rol.equals("Jefe de Estación") && idEstacion <= 0) {
                JOptionPane.showMessageDialog(vista, "Seleccione una estación válida para el Jefe de Estación.");
                return;
            }

            boolean exito = usuarioDAO.registrarUsuarioFormulario(
                    vista.getNombre(), vista.getApPaterno(), vista.getApMaterno(),
                    vista.getCorreo(), vista.getContrasena(), rol, fecha, idLinea, idEstacion
            );

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Usuario registrado exitosamente.");
                vista.limpiarCampos();
                cargarListaDeUsuarios();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar en la base de datos.");
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(vista, "Formato de fecha inválido. Asegúrese de usar YYYY-MM-DD.");
        }
    }

    private void ejecutarActualizacion() {
        if (vista.getIdUsuario().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Seleccione un usuario de la tabla para actualizar.");
            return;
        }

        try {
            LocalDate fecha = LocalDate.parse(vista.getFechaNacimiento());
            int idUsuario = Integer.parseInt(vista.getIdUsuario());

            boolean exito = usuarioDAO.actualizarUsuarioFormulario(
                    idUsuario,
                    vista.getNombre(), vista.getApPaterno(), vista.getApMaterno(),
                    vista.getCorreo(), vista.getRolSeleccionado(), fecha
            );

            if (exito) {
                JOptionPane.showMessageDialog(vista, "Datos de usuario actualizados correctamente.");
                vista.limpiarCampos();
                cargarListaDeUsuarios();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar en la base de datos.");
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(vista, "Formato de fecha inválido. Use YYYY-MM-DD.");
        }
    }

    private void ejecutarEliminacion() {
        if (vista.getIdUsuario().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Seleccione un usuario de la tabla para dar de baja.");
            return;
        }

        int id = Integer.parseInt(vista.getIdUsuario());
        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Está seguro de que desea eliminar al usuario con ID: " + id + "?",
                "Confirmar Baja", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (usuarioDAO.eliminarUsuario(id)) {
                JOptionPane.showMessageDialog(vista, "Usuario removido del sistema.");
                vista.limpiarCampos();
                cargarListaDeUsuarios();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo eliminar al usuario.");
            }
        }
    }

    // ==========================================
    // SELECCIÓN EN LA TABLA
    // ==========================================

    private class FilaSeleccionadaHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int fila = vista.getTablaUsuarios().getSelectedRow();
            if (fila != -1) {
                String id = vista.getTablaUsuarios().getValueAt(fila, 0).toString();
                String nombreCompleto = vista.getTablaUsuarios().getValueAt(fila, 1).toString();
                String correo = vista.getTablaUsuarios().getValueAt(fila, 2).toString();
                String rol = vista.getTablaUsuarios().getValueAt(fila, 3).toString();

                String[] partes = nombreCompleto.split(" ");
                String nom = partes[0];
                String pat = partes.length > 1 ? partes[1] : "";
                String mat = partes.length > 2 ? partes[2] : "";

                vista.cargarFormulario(id, nom, pat, mat, correo, rol);
            }
        }
    }
}