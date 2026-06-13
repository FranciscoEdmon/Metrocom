package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import model.Usuario;
import model.JefeEstacion;
import model.Administrador;
import model.Estacion;
import model.GerenteLinea;
import model.JefeEstacion;
import model.Linea;
import model.Usuario;

public class UsuarioDAO{

    public Usuario validarLogin(String correoIngresado, String contrasenaIngresada) {
        Usuario usuarioLogueado = null;

        String sql = "SELECT u.id_usuario, u.nombre, u.apellidoPat, u.apellidoMat, u.correo, u.contrasena, u.fechaNac, "
                + "a.id_administrador, j.id_jefeDeEstacion, j.id_estacion, g.id_gerenteDeLinea, g.id_linea "
                + "FROM usuarios u "
                + "LEFT JOIN administrador a ON u.id_usuario = a.id_usuario "
                + "LEFT JOIN jefeDeEstacion j ON u.id_usuario = j.id_usuario "
                + "LEFT JOIN gerenteDeLinea g ON u.id_usuario = g.id_usuario "
                + "WHERE u.correo = ? AND u.contrasena = ?";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correoIngresado);
            ps.setString(2, contrasenaIngresada);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LocalDate fechaNacimiento = rs.getDate("fechaNac").toLocalDate();

                // 1. Es Administrador
                if (rs.getInt("id_administrador") != 0) {
                    usuarioLogueado = new Administrador(
                            rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"),
                            rs.getString("apellidoPat"), rs.getString("apellidoMat"),
                            rs.getString("correo"), rs.getString("contrasena"), rs.getInt("id_administrador")
                    );
                }
                // 2. Es Gerente de Línea
                else if (rs.getInt("id_gerenteDeLinea") != 0) {
                    LineaDAO lineaDAO = new LineaDAO();
                    Linea lineaCompleta = lineaDAO.buscarPorId(rs.getInt("id_linea"));

                    usuarioLogueado = new GerenteLinea(
                            rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"),
                            rs.getString("apellidoPat"), rs.getString("apellidoMat"),
                            rs.getString("correo"), rs.getString("contrasena"),
                            rs.getInt("id_gerenteDeLinea"), lineaCompleta
                    );
                }
                // 3. Es Jefe de Estación
                else if (rs.getInt("id_jefeDeEstacion") != 0) {
                    EstacionDAO estacionDAO = new EstacionDAO();
                    // Buscamos el objeto Estación completo para no dejarlo en null
                    Estacion estacionCompleta = estacionDAO.buscarPoId(rs.getInt("id_estacion"));

                    usuarioLogueado = new JefeEstacion(
                            rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"),
                            rs.getString("apellidoPat"), rs.getString("apellidoMat"),
                            rs.getString("correo"), rs.getString("contrasena"),
                            rs.getInt("id_jefeDeEstacion"), estacionCompleta
                    );
                }
            }
            
        }catch(SQLException e){
        System.err.println("Error validando el login: " + e.getMessage());
        }
        return usuarioLogueado;
    }
}

