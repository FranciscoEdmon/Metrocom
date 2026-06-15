package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Prioridad;

public class PrioridadDAO {

    public List<Prioridad> ObtenerLasPrioridades() {
        List<Prioridad> ListaPrioridades = new ArrayList<>();
        String sql = "SELECT * FROM prioridad";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ListaPrioridades.add(new Prioridad(rs.getInt("id_prioridad"), rs.getString("criterio")));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar los tipos de prioridad: " + e.getMessage());
        }
        return ListaPrioridades;
    }
}