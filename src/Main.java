import dao.UsuarioDAO;
import controller.LoginController;
import views.LoginView;

public class Main {
    public static void main(String[] args) {
        // Ejecución segura de la interfaz gráfica en el hilo de Swing
        java.awt.EventQueue.invokeLater(() -> {
            LoginView vistaLogin = new LoginView();
            UsuarioDAO daoUsuario = new UsuarioDAO();

            // El controlador une ambas partes y arranca la lógica
            new LoginController(vistaLogin, daoUsuario);

            vistaLogin.setVisible(true);
        });
    }
}