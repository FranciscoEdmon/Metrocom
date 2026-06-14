package controller;

import dao.UsuarioDAO;
import model.Usuario;
import model.Administrador;
import model.JefeEstacion;
import model.GerenteLinea;
import views.GestionUsuariosView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
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

        // Consulta directa a la base de datos
        List<Usuario> lista = dao.obtenerTodosLosUsuarios();
        for (Usuario u : lista) {
            String nombreCompleto = u.getNombre() + " " + u.getApellidoPat() + " " + u.getApellidoMat();
            String rol = "Usuario";

            if (u instanceof Administrador) rol = "Administrador";
            else if (u instanceof JefeEstacion) rol = "Jefe de Estación";
            else if (u instanceof GerenteLinea) rol = "Gerente de Línea";

            Object[] fila = { u.getId_Usuario(), nombreCompleto, u.getCorreo(), rol };
            vista.getModeloTabla().addRow(fila);
        }
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

                // Mapear datos de la vista a una estructura temporal (Se asume persistencia polimórfica en el DAO)
                boolean exito = dao.registrarUsuarioFormulario(
                        vista.getNombre(), vista.getApPaterno(), vista.getApMaterno(),
                        vista.getCorreo(), vista.getContrasena(), vista.getRolSeleccionado()
                );

                if(exito) {
                    JOptionPane.showMessageDialog(vista, "Usuario registrado de manera exitosa.");
                    vista.limpiarCampos();
                    cargarListaDeUsuarios();
                }

            } else if (comando.equals("Modificar")) {
                if (vista.getIdUsuario().isEmpty()) return;

                boolean exito = dao.actualizarUsuarioFormulario(
                        Integer.parseInt(vista.getIdUsuario()),
                        vista.getNombre(), vista.getApPaterno(), vista.getApMaterno(),
                        vista.getCorreo(), vista.getRolSeleccionado()
                );

                if(exito) {
                    JOptionPane.showMessageDialog(vista, "Datos de usuario modificados correctamente.");
                    vista.limpiarCampos();
                    cargarListaDeUsuarios();
                }

            } else if (comando.equals("Dar de Baja")) {
                if (vista.getIdUsuario().isEmpty()) return;

                int id = Integer.parseInt(vista.getIdUsuario());
                int confirmar = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar al usuario con ID: " + id + "?", "Confirmar baja", JOptionPane.YES_NO_OPTION);

                if (confirmar == JOptionPane.YES_OPTION) {
                    boolean exito = dao.eliminarUsuario(id);
                    if(exito) {
                        JOptionPane.showMessageDialog(vista, "Usuario removido del sistema.");
                        vista.limpiarCampos();
                        cargarListaDeUsuarios();
                    }
                }

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

                String[] partes = nombreCompleto.split(" ");
                String nom = partes[0];
                String pat = partes.length > 1 ? partes[1] : "";
                String mat = partes.length > 2 ? partes[2] : "";

                vista.cargarFormulario(id, nom, pat, mat, correo, rol);
            }
        }
    }
}