package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Linea;

public class LineaDAO {

    public List<Linea> obtenerTodasLasLineas() {
        List<Linea> listaDeLineas = new ArrayList<>();
        String sql = "SELECT * FROM linea";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listaDeLineas.add(new Linea(
                        rs.getInt("id_linea"), rs.getString("nombreLinea"), rs.getString("colorLinea")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las lineas: " + e.getMessage());
        }
        return listaDeLineas;
    }

    public boolean registrarLinea(Linea linea) {
        String sql = "INSERT INTO linea (nombreLinea, colorLinea) VALUES (?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, linea.getNombreLinea());
            ps.setString(2, linea.getColorLinea());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar línea: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarLinea(Linea linea) {
        String sql = "UPDATE linea SET nombreLinea = ?, colorLinea = ? WHERE id_linea = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, linea.getNombreLinea());
            ps.setString(2, linea.getColorLinea());
            ps.setInt(3, linea.getId_Linea());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar línea: " + e.getMessage());
            return false;
        }
    }

    public Linea buscarPorId(int id_busqueda) {
        Linea lineaEncontrada = null;
        String sql = "SELECT * FROM linea WHERE id_linea = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_busqueda);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lineaEncontrada = new Linea(rs.getInt("id_linea"), rs.getString("nombreLinea"), rs.getString("colorLinea"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar línea: " + e.getMessage());
        }
        return lineaEncontrada;
    }
}