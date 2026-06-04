package dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Linea;

public class LineaDAO {

    public List<Linea> obtenerTodasLasLineas(){

        List<Linea> listaDeLineas = new ArrayList<>();

        String sql = "SELECT * FROM linea";

        try{
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Linea lineaActual = new Linea(
                    rs.getInt("id_linea"), rs.getString("nombreLinea"), rs.getString("colorLinea")
                );

                listaDeLineas.add(lineaActual);
                
            }
        }catch(SQLException e){
            System.err.println("Error al obtener las lineas: " + e.getMessage());
        }

            return listaDeLineas;
    }
    
}
