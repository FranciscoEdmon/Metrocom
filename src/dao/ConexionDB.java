package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/metrocom";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "R9m!xT2#qL7v";
    private ConexionDB() {}
    public static Connection getConexion() throws SQLException {
        try {
            // Mantengo la validación del driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establezco la conexion de vuelo
            Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión a la base de datos establecida con éxito."); // Opcional: verás muchos logs
            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el Driver de MySQL. ¿Agregaste el .jar?");
            throw new SQLException("Driver no encontrado", e);
        }
    }
}