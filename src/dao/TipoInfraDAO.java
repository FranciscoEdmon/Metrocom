package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.TipoInfra;

public class TipoInfraDAO {

    public List<TipoInfra> ObetenerTodosLosTInfra (){

        List<TipoInfra> ListaDeInfra = new ArrayList<>();

        String sql = "SELECT * FROM tipoInfra";

        try{

            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TipoInfra infra = new TipoInfra(rs.getInt("tipo_infra"), rs.getString("tipoInfra"));
                
                ListaDeInfra.add(infra);
            }

        }catch(SQLException e){
            System.err.println("Error al obtener los tipos de Infraestructura: " + e.getMessage());
        }

        return ListaDeInfra;

    }
}
