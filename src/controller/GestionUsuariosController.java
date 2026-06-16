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
                    u.getId_Usuario(), nombreCompleto, u.getCorreo(), u.getContrasena(), rol
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

            if (comando.equals("Agregar")) {
                ejecutarRegistro();
            } else if (comando.equals("Modificar")) {
                ejecutarActualizacion();
            } else if (comando.equals("Eliminar")) {
                ejecutarEliminacion();
            } else if (comando.equals("Volver al Panel")) {
                vista.dispose();
            }
        }
    }

    // ==========================================
    // MÉTODOS AUXILIARES DE VALIDACIÓN
    // ==========================================

    /**
     * Verifica que un texto contenga SOLO letras (incluyendo acentos y ñ).
     * Rechaza números y caracteres especiales como @, #, %, etc.
     */
    private boolean soloLetras(String texto) {
        // La expresión regular [\\p{L}\\s]+ acepta:
        //   \\p{L}  → cualquier letra de cualquier idioma (a-z, á, é, ñ, etc.)
        //   \\s     → espacios (por si el nombre tiene dos palabras)
        //   +       → al menos un carácter
        return texto != null && !texto.trim().isEmpty() && texto.matches("[\\p{L}\\s]+");
    }

    /**
     * Genera automáticamente el correo institucional con el formato:
     *   nombreapellidopaterno@metrocdmx.com
     * Todo en minúsculas y sin acentos.
     * Ejemplo: "Juan Pérez" → "juanperez@metrocdmx.com"
     */
    private String generarCorreo(String nombre, String apellidoPat) {
        // Normalizamos: quitamos acentos, pasamos a minúsculas y eliminamos espacios
        String base = (nombre.trim() + apellidoPat.trim())
                .toLowerCase()
                .replaceAll("[áàä]", "a")
                .replaceAll("[éèë]", "e")
                .replaceAll("[íìï]", "i")
                .replaceAll("[óòö]", "o")
                .replaceAll("[úùü]", "u")
                .replaceAll("[ñ]", "n")
                .replaceAll("\\s+", ""); // elimina espacios internos
        return base + "@metrocdmx.com";
    }

    // ==========================================
    // LÓGICA DE CRUD
    // ==========================================

    private void ejecutarRegistro() {
        String nombre    = vista.getNombre();
        String apPaterno = vista.getApPaterno();
        String apMaterno = vista.getApMaterno();

        // --- VALIDACIÓN: solo letras en nombre y apellidos ---
        if (!soloLetras(nombre)) {
            JOptionPane.showMessageDialog(vista,
                    "El nombre solo puede contener letras.\nNo se permiten números ni caracteres especiales.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!soloLetras(apPaterno)) {
            JOptionPane.showMessageDialog(vista,
                    "El apellido paterno solo puede contener letras.\nNo se permiten números ni caracteres especiales.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!soloLetras(apMaterno)) {
            JOptionPane.showMessageDialog(vista,
                    "El apellido materno solo puede contener letras.\nNo se permiten números ni caracteres especiales.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- GENERACIÓN AUTOMÁTICA DEL CORREO ---
        String correoGenerado = generarCorreo(nombre, apPaterno);
        vista.setCorreo(correoGenerado); // ponemos el correo en el campo de la vista

        if (vista.getContrasena().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, llene los campos obligatorios.");
            return;
        }

        // Validación de nombre duplicado
        if (usuarioDAO.existeUsuarioConNombre(vista.getNombre(), vista.getApPaterno(), vista.getApMaterno(), 0)) {
            JOptionPane.showMessageDialog(vista,
                    "Ya existe un usuario con el nombre \"" + vista.getNombre() + " " + vista.getApPaterno() + " " + vista.getApMaterno() + "\".\nVerifique los datos ingresados.",
                    "Nombre duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validación de correo duplicado
        if (usuarioDAO.existeUsuarioConCorreo(vista.getCorreo(), 0)) {
            JOptionPane.showMessageDialog(vista,
                    "El correo \"" + vista.getCorreo() + "\" ya está registrado en el sistema.\nUse un correo diferente.",
                    "Correo duplicado", JOptionPane.WARNING_MESSAGE);
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

            // Restricción de seguridad: un Administrador no puede crear otro Administrador
            if (rol.equals("Administrador")) {
                JOptionPane.showMessageDialog(vista,
                        "No está permitido crear usuarios con rol Administrador desde este panel.",
                        "Acción no permitida", JOptionPane.WARNING_MESSAGE);
                return;
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

        // --- VALIDACIÓN: solo letras en nombre y apellidos ---
        if (!soloLetras(vista.getNombre())) {
            JOptionPane.showMessageDialog(vista,
                    "El nombre solo puede contener letras.\nNo se permiten números ni caracteres especiales.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!soloLetras(vista.getApPaterno())) {
            JOptionPane.showMessageDialog(vista,
                    "El apellido paterno solo puede contener letras.\nNo se permiten números ni caracteres especiales.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!soloLetras(vista.getApMaterno())) {
            JOptionPane.showMessageDialog(vista,
                    "El apellido materno solo puede contener letras.\nNo se permiten números ni caracteres especiales.",
                    "Dato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Restricción de seguridad: no se puede asignar el rol Administrador mediante este formulario
        if (vista.getRolSeleccionado().equals("Administrador")) {
            JOptionPane.showMessageDialog(vista,
                    "No está permitido asignar el rol Administrador desde este panel.",
                    "Acción no permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idUsuario = Integer.parseInt(vista.getIdUsuario());

        // Validación de nombre duplicado (excluyendo el usuario actual)
        if (usuarioDAO.existeUsuarioConNombre(vista.getNombre(), vista.getApPaterno(), vista.getApMaterno(), idUsuario)) {
            JOptionPane.showMessageDialog(vista,
                    "Ya existe otro usuario con el nombre \"" + vista.getNombre() + " " + vista.getApPaterno() + " " + vista.getApMaterno() + "\".\nVerifique los datos ingresados.",
                    "Nombre duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validación de correo duplicado (excluyendo el usuario actual)
        if (usuarioDAO.existeUsuarioConCorreo(vista.getCorreo(), idUsuario)) {
            JOptionPane.showMessageDialog(vista,
                    "El correo \"" + vista.getCorreo() + "\" ya está registrado por otro usuario.\nUse un correo diferente.",
                    "Correo duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LocalDate fecha = LocalDate.parse(vista.getFechaNacimiento());

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
                // Los índices de columna ahora son:
                // 0 = ID, 1 = Nombre Completo, 2 = Correo, 3 = Contraseña, 4 = Rol
                String id = vista.getTablaUsuarios().getValueAt(fila, 0).toString();
                String nombreCompleto = vista.getTablaUsuarios().getValueAt(fila, 1).toString();
                String correo = vista.getTablaUsuarios().getValueAt(fila, 2).toString();
                String rol = vista.getTablaUsuarios().getValueAt(fila, 4).toString(); // columna 4, no 3

                String[] partes = nombreCompleto.split(" ");
                String nom = partes[0];
                String pat = partes.length > 1 ? partes[1] : "";
                String mat = partes.length > 2 ? partes[2] : "";

                vista.cargarFormulario(id, nom, pat, mat, correo, rol);
            }
        }
    }
}