package utilities;
import model.Usuario;

/**
 * Clase sencilla para mantener la sesión del usuario en memoria durante la ejecución.
 */
public class SesionUsuario {
    private static Usuario usuarioActual;

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

}


