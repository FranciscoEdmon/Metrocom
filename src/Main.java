import dao.UsuarioDAO;
import dao.LineaDAO;
import dao.EstacionDAO;
import controller.LoginController;
import views.LoginView;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // Primero instancio todos los DAOs centrales del sistema
            UsuarioDAO daoUsuario = new UsuarioDAO();
            LineaDAO daoLinea = new LineaDAO();
            EstacionDAO daoEstacion = new EstacionDAO();

            // Despues instancio la vista del Login
            LoginView vistaLogin = new LoginView();

            // Luego paso la vista y todos los DAOs al LoginController para que tenga acceso a ellos al abrir los siguientes páneles
            new LoginController(vistaLogin, daoUsuario, daoLinea, daoEstacion);

            // Y por utlimo muestro la Vista Login
            vistaLogin.setVisible(true);
        });
    }
}