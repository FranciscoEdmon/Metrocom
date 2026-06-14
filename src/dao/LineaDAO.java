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