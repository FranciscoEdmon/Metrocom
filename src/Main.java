import dao.UsuarioDAO;
import dao.LineaDAO;
import dao.EstacionDAO;
import controller.LoginController;
import views.LoginView;

public class Main {
    public static void main(String[] args) {
        // Ejecución segura de la interfaz gráfica en el hilo de Swing
        java.awt.EventQueue.invokeLater(() -> {
            // 1. Instanciar todos los DAOs centrales del sistema
            UsuarioDAO daoUsuario = new UsuarioDAO();
            LineaDAO daoLinea = new LineaDAO();
            EstacionDAO daoEstacion = new EstacionDAO();

            // 2. Instanciar la vista de inicio de sesión
            LoginView vistaLogin = new LoginView();

            // 3. Pasar la vista y todos los DAOs al LoginController
            // para que tenga acceso a ellos al abrir los siguientes páneles
            new LoginController(vistaLogin, daoUsuario, daoLinea, daoEstacion);

            // 4. Mostrar la ventana de Login
            vistaLogin.setVisible(true);
        });
    }
}