package dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Estacion;

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
 }

