package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Estacion;
import model.JefeEstacion;
import model.Prioridad;
import model.Reporte;
import model.TipoDaño;
import model.TipoInfra;

    // esta funcion crea el INSERT
    public class ReporteDAO {
        public boolean registrarReporte(Reporte reporteActual) {
        
        String sql = "INSERT INTO reporte (fechaCreacion, id_jefeDeEstacion, estado, ubicacionExacta, descripcion, id_prioridad, id_tipoInfra, id_tipoDanio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(reporteActual.getFechaCreacion()));
            ps.setInt(2, reporteActual.getJefeEstacion().getId_jefeDeEstacion());
            ps.setString(3, reporteActual.getEstado());
            ps.setString(4, reporteActual.getUbicacionExacta());
            ps.setString(5, reporteActual.getDescripcion());
            ps.setInt(6, reporteActual.getPrioridad().getId_prioridad());
            ps.setInt(7, reporteActual.getTipoInfra().getId_infra());
            ps.setInt(8, reporteActual.getTipoDaño().getId_TipoDaño());
            
            int filasAfectadas = ps.executeUpdate();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar el reporte en la base de datos: " + e.getMessage());
            return false;
        }
    }

    // esta funcion crea el READ
    public List<Reporte> obtenerTodossLosReportes(){
        List<Reporte> listaReportes = new ArrayList<>();

        String sql = "SELECT r.*, p.prioridad, ti.tipoInfra, td.tipoDanio, j.id_usuario, j.id_estacion, u.nombre, u.apellidoPat, u.apellidoMat, u.correo, u.contraseña, u.fechaDeNacimiento, e.nombreEstacion, e.trasbordo, e.id_linea FROM reporte r INNER JOIN prioridad p ON r.id_prioridad = p.id_prioridad INNER JOIN tipoInfra ti ON r.id_tipoInfra = ti.id_tipoInfra INNER JOIN tipoDanio td ON rid_idtipoDanio = td.id_tipoDanio INNER JOIN jefeEstacion j ON r.id_jefeDeEstacion = j.id_jefeDeEstacion INNER JOIN  usuario u ON j.id_usuario = u.id_usuario INNER JOIN estacion e ON j.id_estacion = e.id_estacion";

        try (Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ) {
            
                while (rs.next()){

                    //datos de categorisacion
                    Prioridad prioridadReal = new Prioridad(rs.getInt("id_prioridad"), rs.getString("prioridad"));
                    TipoInfra infraReal = new TipoInfra(rs.getInt("id_tipoInfra"), rs.getString("tipoInfra"));
                    TipoDaño danioReal = new TipoDaño(rs.getInt("id_tipoDanio"), rs.getString("tipoDanio"));

                    //datos de la estacion
                    Estacion estacionDelJefe = new Estacion(rs.getInt("id_estacion"), rs.getString("nombreEstacion"), rs.getBoolean("trasbordo"), rs.getInt("id_linea"));

                    //datos del jefe de estacion
                    java.time.LocalDate fechaNacimiento = rs.getDate("fechaDeNacimiento").toLocalDate();
                    JefeEstacion jefeEstacion = new JefeEstacion(rs.getInt("id_usuario"), fechaNacimiento, rs.getString("nombre"), rs.getString("apellidoPat"), rs.getString("apellidoMat"), rs.getString("correo"), rs.getString("contrasena"), rs.getInt("id_jefeDeEstacion"), estacionDelJefe);

                    //El reporte creado, instanciando los datos mediante las variables anteriores
                    Reporte reporteArmado = new Reporte(rs.getInt("id_reporte"), rs.getString("estado"), rs.getString("ubicacionExacta"), rs.getString("descripcion"),rs.getTimestamp("fechaCreacion").toLocalDateTime(), jefeEstacion, prioridadReal, infraReal, danioReal);

                    listaReportes.add(reporteArmado);
                }
        } catch (SQLException e) {
            System.err.println("Error al obtener reportes: " + e.getMessage());
        }
        return listaReportes;
    }

    // esta funcion aplica el UPDATE sobre el estado (para gerente de línea)
    public boolean actualizarEstado(int id_reporte, String nuevoEstado){
        String sql = "UPDATE reporte SET estado = ? WHERE id_reporte = ?";

        try(Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
        ){

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id_reporte);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        }catch(SQLException e){
            System.err.println("Error al actualizar reporte: " + e.getMessage());
            
            return false;
        }
    }

    //Esta funcion hace el DELETE permanente
    public boolean borrarReporte(int id_reporte) {
        String sql = "DELATE FROM reporte WHERE id_reporte = ?";

        try(Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setInt(1, id_reporte);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }catch(SQLException e){
            System.err.println("Error al borrar el reporte: " + e.getMessage());
            
            return false;
        }
    }

    //funcion para hacer el UPDATE del reporte completo(para el jefe de estacion).
    public boolean actualizarReporteCompleto(Reporte reporteEditado) {
        String sql = "update reporte set ubicacionExacta = ?, descripcion = ?, id_prioridad = ?, id_tipoInfra = ?,  id_tipoDanio = ? WHERE id_reporte = ?";

        try(Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setString(1, reporteEditado.getUbicacionExacta());            
            ps.setString(2, reporteEditado.getDescripcion());
            ps.setInt(3, reporteEditado.getPrioridad().getId_prioridad());
            ps.setInt (4, reporteEditado.getTipoInfra().getId_infra());
            ps.setInt(5, reporteEditado.getTipoDaño().getId_TipoDaño());
            ps.setInt(6, reporteEditado.getId_Reporte());

            int filasAfectadas = ps.executeUpdate();
            
            return filasAfectadas > 0;

        }catch(SQLException e){
            System.err.println("Error al editar el reporte: " + e.getMessage());

            return false;
        }

    }
}
