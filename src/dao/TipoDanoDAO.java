package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.TipoDano;

public class TipoDanoDAO {

    public List<TipoDano> ObtenerLosTDano() {
        List<TipoDano> ListaTDano = new ArrayList<>();
        String sql = "SELECT * FROM tipoDano";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ListaTDano.add(new TipoDano(rs.getInt("id_Tipodano"), rs.getString("nombreDano")));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los tipos de daño: " + e.getMessage());
        }
        return ListaTDano;
    }
}