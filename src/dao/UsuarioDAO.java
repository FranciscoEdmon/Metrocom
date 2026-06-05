package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;
import model.JefeEstacion;
import model.Administrador;
import model.GerenteLinea;

    public class UsuarioDAO {
        
        public Usuario validarLogin(String correoIngresado, String contrasenaIngresada){
            Usuario usuarioLogeuado = null;

            String sql = "SELECT u.*, a.id_administrador, j.id_jefeDeEstacion, j.id_estacion, g.id_gerenteDeLinea, g.id_linea " + "FROM usuarios u" + "LEFT JOIN administrador a ON u.id_usuario = a.id_usuario " + "LEFT JOIN jefeDeEstacion j ON u.id_usuario = j.id_usuario " + "LEFT JOIN gerenteDeLinea g ON u.id_usuario = g.id_usuario " + "WHERE u.correo = ?, AND u.contraseña = ?";

            try{
                Connection con = ConexionDB.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, correoIngresado);
                ps.setString(2, contrasenaIngresada);

                ResultSet rs = ps.executeQuery();

                if(rs.next()) {

                    java.time.LocalDate fechaNacimiento = rs.getDate("fechaNac").toLocalDate();

                        if(rs.getInt("id_administrador") != 0) {

                        usuarioLogeuado = new Administrador(
                            rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"), rs.getString("apellidoPat"), rs.getString("apellidoMat"), rs.getString("correo"), rs.getString("contrasena"), rs.getInt("id_administrador")
                        );

                    }else if (rs.getInt("id_jefeDeEstacion") != 0) {

                        usuarioLogeuado = new JefeEstacion(
                            rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"), rs.getString("apellidoPat"), rs.getString("apellidoMat"), rs.getString("correo"), rs.getString("contrasena"), rs.getInt("id_jefeDeEstacion"), null
                        );
                        
                    }else if(rs.getInt("id_gerenteDeLinea") != 0) {

                        usuarioLogeuado = new GerenteLinea(
                            rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"), rs.getString("apellidoPat"), rs.getString("apellidoMat"), rs.getString("correo"), rs.getString("contrasena"), rs.getInt("id_gerenteDeLinea"), null
                        );

                    }
                }
            }catch(SQLException e ){
            System.err.println("Error validando el login: " + e.getMessage());
            }

            return usuarioLogeuado;
    }
}
