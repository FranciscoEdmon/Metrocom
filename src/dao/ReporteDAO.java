package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Estacion;
import model.JefeEstacion;
import model.Prioridad;
import model.Reporte;
import model.TipoDano;
import model.TipoInfra;

public class ReporteDAO {

    // esta funcion crea el INSERT
    public boolean registrarReporte(Reporte reporteActual) {
        // CORREGIDO: Al final se cambió id_tipoDanio por id_tipoDano
        String sql = "INSERT INTO reporte (fechaCreacion, id_jefeDeEstacion, estado, ubicacionExacta, descripcion, id_prioridad, id_tipoInfra, id_tipoDano) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, java.sql.Timestamp.valueOf(reporteActual.getFechaCreacion()));
            ps.setInt(2, reporteActual.getJefeEstacion().getId_jefeDeEstacion());
            ps.setString(3, reporteActual.getEstado());
            ps.setString(4, reporteActual.getUbicacionExacta());
            ps.setString(5, reporteActual.getDescripcion());
            ps.setInt(6, reporteActual.getPrioridad().getId_prioridad());
            ps.setInt(7, reporteActual.getTipoInfra().getId_infra());
            ps.setInt(8, reporteActual.getTipoDaño().getId_TipoDano());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar el reporte en la base de datos: " + e.getMessage());
            return false;
        }
    }

    // esta funcion crea el READ GENERAL
    public List<Reporte> obtenerTodosLosReportes() {
        List<Reporte> listaReportes = new ArrayList<>();
        // CORREGIDO: p.IdPrioridad -> p.id_prioridad
        String sql = "SELECT r.*, p.criterio AS prioridad, ti.tipoInfra, td.nombreDano, j.id_usuario, j.id_estacion, u.nombre, u.apellidoPat, u.apellidoMat, u.correo, u.contrasena, u.fechaNac, e.nombreEstacion, e.transbordo, e.id_linea " +
                "FROM reporte r " +
                "INNER JOIN prioridad p ON r.id_prioridad = p.id_prioridad " +
                "INNER JOIN tipoInfra ti ON r.id_tipoInfra = ti.id_tipoInfra " +
                "INNER JOIN tipoDano td ON r.id_tipoDano = td.id_tipoDano " +
                "INNER JOIN jefeDeEstacion j ON r.id_jefeDeEstacion = j.id_jefeDeEstacion " +
                "INNER JOIN usuarios u ON j.id_usuario = u.id_usuario " +
                "INNER JOIN estacion e ON j.id_estacion = e.id_estacion";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()){
                listaReportes.add(mapearReporte(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reportes: " + e.getMessage());
        }
        return listaReportes;
    }

    // NUEVO: Filtra por línea y estado (Para BandejaEntrada y AtencionReportes)
    public List<Reporte> obtenerReportesPorLineaYEstado(int idLinea, String estado) {
        List<Reporte> listaReportes = new ArrayList<>();
        // CORREGIDO: p.IdPrioridad -> p.id_prioridad
        String sql = "SELECT r.*, p.criterio AS prioridad, ti.tipoInfra, td.nombreDano, j.id_usuario, j.id_estacion, u.nombre, u.apellidoPat, u.apellidoMat, u.correo, u.contrasena, u.fechaNac, e.nombreEstacion, e.transbordo, e.id_linea " +
                "FROM reporte r " +
                "INNER JOIN prioridad p ON r.id_prioridad = p.id_prioridad " +
                "INNER JOIN tipoInfra ti ON r.id_tipoInfra = ti.id_tipoInfra " +
                "INNER JOIN tipoDano td ON r.id_tipoDano = td.id_tipoDano " +
                "INNER JOIN jefeDeEstacion j ON r.id_jefeDeEstacion = j.id_jefeDeEstacion " +
                "INNER JOIN usuarios u ON j.id_usuario = u.id_usuario " +
                "INNER JOIN estacion e ON j.id_estacion = e.id_estacion " +
                "WHERE e.id_linea = ? AND r.estado = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idLinea);
            ps.setString(2, estado);

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    listaReportes.add(mapearReporte(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reportes por línea y estado: " + e.getMessage());
        }
        return listaReportes;
    }

    // NUEVO: Filtra reportes del jefe que los creó (Para MisReportes)
    public List<Reporte> obtenerReportesPorJefe(int idJefeDeEstacion) {
        List<Reporte> listaReportes = new ArrayList<>();
        // CORREGIDO: p.id_prioridad y td.id_tipoDano unificados completamente en minúsculas
        String sql = "SELECT r.*, p.criterio AS prioridad, ti.tipoInfra, td.nombreDano, " +
                "j.id_usuario, j.id_estacion, u.nombre, u.apellidoPat, u.apellidoMat, " +
                "u.correo, u.contrasena, u.fechaNac, e.nombreEstacion, e.transbordo, e.id_linea " +
                "FROM reporte r " +
                "INNER JOIN prioridad p ON r.id_prioridad = p.id_prioridad " +
                "INNER JOIN tipoInfra ti ON r.id_tipoInfra = ti.id_tipoInfra " +
                "INNER JOIN tipoDano td ON r.id_tipoDano = td.id_tipoDano " +
                "INNER JOIN jefeDeEstacion j ON r.id_jefeDeEstacion = j.id_jefeDeEstacion " +
                "INNER JOIN usuarios u ON j.id_usuario = u.id_usuario " +
                "INNER JOIN estacion e ON j.id_estacion = e.id_estacion " +
                "WHERE r.id_jefeDeEstacion = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idJefeDeEstacion);

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    listaReportes.add(mapearReporte(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reportes por Jefe de Estación: " + e.getMessage());
        }
        return listaReportes;
    }

    // Método de apoyo para no repetir código al armar el objeto
    private Reporte mapearReporte(ResultSet rs) throws SQLException {
        Prioridad prioridadReal = new Prioridad(rs.getInt("id_prioridad"), rs.getString("prioridad"));
        TipoInfra infraReal = new TipoInfra(rs.getInt("id_tipoInfra"), rs.getString("tipoInfra"));
        TipoDano danioReal = new TipoDano(rs.getInt("id_tipoDano"), rs.getString("nombreDano"));

        Estacion estacionDelJefe = new Estacion(rs.getInt("id_estacion"), rs.getString("nombreEstacion"), rs.getBoolean("transbordo"), rs.getInt("id_linea"));
        java.time.LocalDate fechaNacimiento = rs.getDate("fechaNac") != null ? rs.getDate("fechaNac").toLocalDate() : java.time.LocalDate.now();

        JefeEstacion jefeEstacion = new JefeEstacion(rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"), rs.getString("apellidoPat"), rs.getString("apellidoMat"), rs.getString("correo"), rs.getString("contrasena"), rs.getInt("id_jefeDeEstacion"), estacionDelJefe);

        return new Reporte(rs.getInt("id_reporte"), rs.getString("estado"), rs.getString("ubicacionExacta"), rs.getString("descripcion"), rs.getTimestamp("fechaCreacion").toLocalDateTime(), jefeEstacion, prioridadReal, infraReal, danioReal);
    }

    // RENOMBRADO para hacer match con los controladores
    public boolean actualizarEstadoReporte(int id_reporte, String nuevoEstado) {
        String sql = "UPDATE reporte SET estado = ? WHERE id_reporte = ?";

        try(Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id_reporte);

            return ps.executeUpdate() > 0;

        } catch(SQLException e) {
            System.err.println("Error al actualizar estado del reporte: " + e.getMessage());
            return false;
        }
    }

    public boolean borrarReporte(int id_reporte) {
        String sql = "DELETE FROM reporte WHERE id_reporte = ?";

        try(Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_reporte);
            return ps.executeUpdate() > 0;

        } catch(SQLException e) {
            System.err.println("Error al borrar el reporte: " + e.getMessage());
            return false;
        }
    }

    // funcion para hacer el UPDATE del reporte completo(para el jefe de estacion).
    public boolean actualizarReporteCompleto(Reporte reporteEditado) {
        // CORREGIDO: id_tipoDanio = ? -> id_tipoDano = ?
        String sql = "UPDATE reporte SET ubicacionExacta = ?, descripcion = ?, id_prioridad = ?, id_tipoInfra = ?, id_tipoDano = ? WHERE id_reporte = ?";

        try(Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reporteEditado.getUbicacionExacta());
            ps.setString(2, reporteEditado.getDescripcion());
            ps.setInt(3, reporteEditado.getPrioridad().getId_prioridad());
            ps.setInt (4, reporteEditado.getTipoInfra().getId_infra());
            ps.setInt(5, reporteEditado.getTipoDaño().getId_TipoDano());
            ps.setInt(6, reporteEditado.getId_Reporte());

            return ps.executeUpdate() > 0;

        } catch(SQLException e) {
            System.err.println("Error al editar el reporte: " + e.getMessage());
            return false;
        }
    }
}