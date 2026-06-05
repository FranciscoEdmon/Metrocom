package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.TipoDano;

public class TipoDanoDAO {

    public List<TipoDano> ObtenerLosTDano (){

        List<TipoDano> ListaTDano = new ArrayList<>();

        String sql = "SELECT * FROM tipoDaño";

        try{

            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TipoDano tipoDano = new TipoDano(rs.getInt("idTipodano"), rs.getString("nombreDano"));
                ListaTDano.add(tipoDano);

            }
        }catch(SQLException e){
            System.err.println("Error al obtener los tipos de daño: " + e.getMessage());
        }

        return ListaTDano;

    }
}
