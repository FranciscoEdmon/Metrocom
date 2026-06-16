package dao;

import java.util.ArrayList;
import java.util.List;
import model.Estacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstacionDAO {

    public List<Estacion> obtenerEstacionesPorLinea(int idLinea){
        List<Estacion> listaDeEstaciones = new ArrayList<>();
        String sql = "SELECT * FROM estacion WHERE id_linea = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idLinea);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    Estacion estacionActual = new Estacion(
                            rs.getInt("id_estacion"),
                            rs.getString("nombreEstacion"),
                            rs.getBoolean("transbordo"),
                            rs.getInt("id_linea")
                    );
                    listaDeEstaciones.add(estacionActual);
                }
            }
        } catch(SQLException e){
            System.err.println("Error al obtener las estaciones: " + e.getMessage());
        }
        return listaDeEstaciones;
    }

    /**
     * Verifica si ya existe una estación con el mismo nombre dentro de la misma línea.
     * Para modificaciones, excluye el propio registro usando excludeId.
     */
    public boolean existeEstacionConNombre(String nombre, int idLinea, int excludeId) {
        String sql = "SELECT COUNT(*) FROM estacion WHERE LOWER(nombreEstacion) = LOWER(?) AND id_linea = ? AND id_estacion <> ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre.trim());
            ps.setInt(2, idLinea);
            ps.setInt(3, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar nombre de estación: " + e.getMessage());
        }
        return false;
    }

    public Estacion buscarPoId(int id_busqueda){
        Estacion estacionEncontrada = null;
        String sql = "SELECT * FROM estacion WHERE id_estacion = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_busqueda);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    estacionEncontrada = new Estacion(
                            rs.getInt("id_estacion"),
                            rs.getString("nombreEstacion"),
                            rs.getBoolean("transbordo"),
                            rs.getInt("id_linea")
                    );
                }
            }
        } catch(SQLException e){
            System.err.println("Error al buscar estación por ID: " + e.getMessage());
        }
        return estacionEncontrada;
    }

    // =================================================================
    // NUEVOS MÉTODOS ANEXADOS PARA EL SUB-CRUD DE LA MISMA VENTANA
    // =================================================================

    /**
     * Inserta una nueva estación ligada a una línea específica.
     */
    public boolean registrarEstacion(Estacion estacion) {
        String sql = "INSERT INTO estacion (nombreEstacion, transbordo, id_linea) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estacion.getNombreEstacion());
            ps.setBoolean(2, estacion.isTransbordo());
            ps.setInt(3, estacion.getId_linea());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar estación: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza el nombre o estado de transbordo de una estación existente.
     */
    public boolean actualizarEstacion(Estacion estacion) {
        String sql = "UPDATE estacion SET nombreEstacion = ?, transbordo = ? WHERE id_estacion = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estacion.getNombreEstacion());
            ps.setBoolean(2, estacion.isTransbordo());
            ps.setInt(3, estacion.getId_Estacion());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estación: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una estación por su ID primario.
     */
    public boolean eliminarEstacion(int idEstacion) {
        String sql = "DELETE FROM estacion WHERE id_estacion = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEstacion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar estación: " + e.getMessage());
            return false;
        }
    }
}