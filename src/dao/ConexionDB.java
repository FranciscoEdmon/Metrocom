package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static Connection conexion = null;

    // 2. Credenciales de tu base de datos (Ajusta estos textos a tu entorno)
    private static final String URL = "jdbc:mysql://localhost:3306/metrocom";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "R9m!xT2#qL7v";

    // 3. Constructor privado: Evita que alguien haga "new ConexionDB()" en otro lado
    private ConexionDB() {
        try {
            // Carga el driver de MySQL en la memoria
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establece la conexión física
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión a la base de datos establecida con éxito.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el Driver de MySQL. ¿Agregaste el .jar?");
        } catch (SQLException e) {
            System.err.println("Error SQL: Fallo al conectar con la base de datos.");
            e.printStackTrace();
        }
    }

    public static Connection getConexion() {
        if (conexion == null) {
            new ConexionDB();
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión.");
            }
        }
    }
    
}
