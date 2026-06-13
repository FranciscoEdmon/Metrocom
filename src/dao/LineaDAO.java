package dao;

import java.util.List;

import model.Linea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    //Metodo para la busqueda e inserccion de la linea designada para el login y UsuarioDAO
    public Linea buscarPorId(int id_busqueda){
        Linea lineaEncontrada = null;
        String sql = "SELECT * FROM linea WHERE id_linea = ?";

        try{

            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id_busqueda);

            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){

                lineaEncontrada = new Linea(rs.getInt("id_linea"), rs.getString("nombreLinea"), rs.getString("colorLinea"));

            }
        }catch(SQLException e){
            System.err.println("Error al buscar la linea por ID: " + e.getMessage());
        }

        return lineaEncontrada;

    }
    
}
