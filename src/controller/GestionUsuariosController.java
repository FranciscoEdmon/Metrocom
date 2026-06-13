package controller;

import dao.UsuarioDAO;
import views.GestionUsuariosView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class GestionUsuariosController {
    private GestionUsuariosView vista;
    private UsuarioDAO dao;

    public GestionUsuariosController(GestionUsuariosView vista, UsuarioDAO dao) {
        this.vista = vista;
        this.dao = dao;

        this.vista.addListeners(new BotonesHandler(), new FilaSeleccionadaHandler());
        cargarListaDeUsuarios();
    }

    private void cargarListaDeUsuarios() {
        vista.getModeloTabla().setRowCount(0);
        // Aquí consumirás un método del DAO como dao.obtenerTodosLosUsuarios()
        // Ejemplo mock para estructurar la tabla:
        Object[] ejemplo = {"1", "Juan Pérez Gómez", "juan@metro.cdmx.gob.mx", "Jefe de Estación"};
        vista.getModeloTabla().addRow(ejemplo);
    }

    private class BotonesHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            if (comando.equals("Registrar")) {
                if(vista.getNombre().isEmpty() || vista.getCorreo().isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Los campos esenciales no pueden ir vacíos.");
                    return;
                }
                System.out.println("Enviando a la Base de Datos el rol: " + vista.getRolSeleccionado());
                // Lógica de mapeo a clases hijas según rol seleccionado
                vista.limpiarCampos();
                cargarListaDeUsuarios();

            } else if (comando.equals("Actualizar")) {
                if (vista.getIdUsuario().isEmpty()) return;
                System.out.println("Actualizando ID: " + vista.getIdUsuario());
                vista.limpiarCampos();
                cargarListaDeUsuarios();

            } else if (comando.equals("Dar de Baja")) {
                if (vista.getIdUsuario().isEmpty()) return;
                System.out.println("Removiendo del sistema ID: " + vista.getIdUsuario());
                vista.limpiarCampos();
                cargarListaDeUsuarios();

            } else if (comando.equals("Volver al Panel")) {
                vista.dispose();
            }
        }
    }

    private class FilaSeleccionadaHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int fila = vista.getTablaUsuarios().getSelectedRow();
            if (fila != -1) {
                String id = vista.getTablaUsuarios().getValueAt(fila, 0).toString();
                String nombreCompleto = vista.getTablaUsuarios().getValueAt(fila, 1).toString();
                String correo = vista.getTablaUsuarios().getValueAt(fila, 2).toString();
                String rol = vista.getTablaUsuarios().getValueAt(fila, 3).toString();

                // División rápida del nombre completo para rellenar los componentes separados
                String[] partes = nombreCompleto.split(" ");
                String nom = partes[0];
                String pat = partes.length > 1 ? partes[1] : "";
                String mat = partes.length > 2 ? partes[2] : "";

                vista.cargarFormulario(id, nom, pat, mat, correo, rol);
            }
        }
    }
}