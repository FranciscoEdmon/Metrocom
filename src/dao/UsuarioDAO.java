package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import model.JefeEstacion;
import model.Administrador;
import model.Estacion;
import model.GerenteLinea;
import model.Linea;

public class UsuarioDAO {

    public Usuario validarLogin(String correoIngresado, String contrasenaIngresada) {
        Usuario usuarioLogueado = null;

        String sql = "SELECT u.id_usuario, u.nombre, u.apellidoPat, u.apellidoMat, u.correo, u.contrasena, u.fechaNac, "
                + "a.id_administrador, j.id_jefeDeEstacion, j.id_estacion, g.id_gerenteDeLinea, g.id_linea "
                + "FROM usuarios u "
                + "LEFT JOIN administrador a ON u.id_usuario = a.id_usuario "
                + "LEFT JOIN jefeDeEstacion j ON u.id_usuario = j.id_usuario "
                + "LEFT JOIN gerenteDeLinea g ON u.id_usuario = g.id_usuario "
                + "WHERE u.correo = ? AND u.contrasena = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correoIngresado);
            ps.setString(2, contrasenaIngresada);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    LocalDate fechaNacimiento = rs.getDate("fechaNac") != null ? rs.getDate("fechaNac").toLocalDate() : LocalDate.now();

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
                        Estacion estacionCompleta = estacionDAO.buscarPoId(rs.getInt("id_estacion"));

                        usuarioLogueado = new JefeEstacion(
                                rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"),
                                rs.getString("apellidoPat"), rs.getString("apellidoMat"),
                                rs.getString("correo"), rs.getString("contrasena"),
                                rs.getInt("id_jefeDeEstacion"), estacionCompleta
                        );
                    }
                }
            }
        } catch(SQLException e) {
            System.err.println("Error validando el login: " + e.getMessage());
        }
        return usuarioLogueado;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre, u.apellidoPat, u.apellidoMat, u.correo, u.contrasena, u.fechaNac, "
                + "a.id_administrador, j.id_jefeDeEstacion, g.id_gerenteDeLinea "
                + "FROM usuarios u "
                + "LEFT JOIN administrador a ON u.id_usuario = a.id_usuario "
                + "LEFT JOIN jefeDeEstacion j ON u.id_usuario = j.id_usuario "
                + "LEFT JOIN gerenteDeLinea g ON u.id_usuario = g.id_usuario";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LocalDate fechaNacimiento = rs.getDate("fechaNac") != null ? rs.getDate("fechaNac").toLocalDate() : LocalDate.now();
                Usuario user = null;

                if (rs.getInt("id_administrador") != 0) {
                    user = new Administrador(rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"), rs.getString("apellidoPat"), rs.getString("apellidoMat"), rs.getString("correo"), rs.getString("contrasena"), rs.getInt("id_administrador"));
                } else if (rs.getInt("id_gerenteDeLinea") != 0) {
                    user = new GerenteLinea(rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"), rs.getString("apellidoPat"), rs.getString("apellidoMat"), rs.getString("correo"), rs.getString("contrasena"), rs.getInt("id_gerenteDeLinea"), null);
                } else if (rs.getInt("id_jefeDeEstacion") != 0) {
                    user = new JefeEstacion(rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"), rs.getString("apellidoPat"), rs.getString("apellidoMat"), rs.getString("correo"), rs.getString("contrasena"), rs.getInt("id_jefeDeEstacion"), null);
                }

                if (user != null) {
                    lista.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
        }
        return lista;
    }

    public boolean registrarUsuarioFormulario(String nom, String pat, String mat, String correo, String pass, String rol) {
        String sqlUsuario = "INSERT INTO usuarios (nombre, apellidoPat, apellidoMat, correo, contrasena, fechaNac) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nom);
            ps.setString(2, pat);
            ps.setString(3, mat);
            ps.setString(4, correo);
            ps.setString(5, pass);
            ps.setDate(6, java.sql.Date.valueOf(LocalDate.now())); // Fecha por defecto

            int afectadas = ps.executeUpdate();
            if (afectadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    return insertarRolEspecifico(con, idGenerado, rol);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
        }
        return false;
    }

    private boolean insertarRolEspecifico(Connection con, int idUsuario, String rol) throws SQLException {
        String sql = "";
        if (rol.equals("Administrador")) {
            sql = "INSERT INTO administrador (id_usuario) VALUES (?)";
        } else if (rol.equals("Jefe de Estación")) {
            sql = "INSERT INTO jefeDeEstacion (id_usuario, id_estacion) VALUES (?, 1)"; // Estacion 1 por defecto, se debe ajustar luego
        } else if (rol.equals("Gerente de Línea")) {
            sql = "INSERT INTO gerenteDeLinea (id_usuario, id_linea) VALUES (?, 1)"; // Linea 1 por defecto
        } else {
            return true; // Sin rol especifico
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizarUsuarioFormulario(int id, String nom, String pat, String mat, String correo, String rol) {
        String sql = "UPDATE usuarios SET nombre = ?, apellidoPat = ?, apellidoMat = ?, correo = ? WHERE id_usuario = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nom);
            ps.setString(2, pat);
            ps.setString(3, mat);
            ps.setString(4, correo);
            ps.setInt(5, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}