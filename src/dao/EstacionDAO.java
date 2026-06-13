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

        String sql = "Select * FROM estacion WHERE id_linea = ?";

        try{

            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idLinea);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Estacion estacionActual = new Estacion(
                    rs.getInt("id_estacion"), rs.getString("nombreEstacion"), rs.getBoolean("transbordo"), rs.getInt("id_linea")
                );
            

            listaDeEstaciones.add(estacionActual);
            }
        }catch(SQLException e){
            System.err.println("Error al obtener las estaciones: " + e.getMessage());
        }

        return listaDeEstaciones;
    }

    //Metodo para la busqueda e inserccion de la estaccion designada para el login y UsuarioDAO

    public Estacion buscarPoId(int id_busqueda){
        Estacion estacionEncontrada = null;
        // Corregido: id_estacion en lugar de id_esatcion
        String sql = "SELECT * FROM estacion WHERE id_estacion = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_busqueda);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Corregido: transbordo en lugar de trasbordo para hacer match con tu BD
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
}

