package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import views.base.VentanaBase;
import views.base.BotonModerno;

public class AdminMenuView extends VentanaBase {
    private JButton btnGestionUsuarios;
    private JButton btnGestionInfraestructura;
    private JButton btnCerrarSesion;

    public AdminMenuView() {
        // 1. DIMENSIONES HORIZONTALES: Ancho de 680 y Alto de 320
        super("MetroCom - Panel de Administración", 680, 320, JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Usamos el PanelModerno base que ya tiene tu degradado estilizado
        PanelModerno panelPrincipal = new PanelModerno(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15); // Márgenes internos entre componentes

        // =====================================================================
        //   COLUMNA IZQUIERDA: IMAGEN DEL METRO (Ocupa todo el alto disponible)
        // =====================================================================
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;          // Se extiende verticalmente junto al formulario
        gbc.weightx = 0.35;          // Toma el 35% del ancho de la ventana
        gbc.fill = GridBagConstraints.CENTER;

        try {
            // Intentamos cargar la imagen del metro desde tu carpeta de recursos
            ImageIcon metroIcon = new ImageIcon(getClass().getResource("/multimedia/MetroIcon.png"));
            Image img = metroIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            JLabel lblImagenMetro = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            panelPrincipal.add(lblImagenMetro, gbc);
        } catch (Exception e) {
            // Fallback visual por si aún no tienes el archivo físico en multimedia
            JLabel lblFallback = new JLabel("🚇", SwingConstants.CENTER);
            lblFallback.setFont(new Font("Segoe UI", Font.PLAIN, 80));
            panelPrincipal.add(lblFallback, gbc);
        }

        // =====================================================================
        //   COLUMNA DERECHA: TEXTO DE BIENVENIDA Y BOTONES DE ACCIÓN
        // =====================================================================
        gbc.gridheight = 1;          // Restauramos la altura de celda estándar
        gbc.weightx = 0.65;          // Toma el 65% restante del ancho de la ventana
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Fila 0 de la derecha: Título de Bienvenida ---
        gbc.gridx = 1;
        gbc.gridy = 0;
        JLabel lblBienvenida = new JLabel("Panel de Control General", SwingConstants.LEFT);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBienvenida.setForeground(new Color(255, 233, 157)); // Color claro texturizado #FFE99D
        panelPrincipal.add(lblBienvenida, gbc);

        // --- Fila 1 de la derecha: Subpanel contenedor para los botones ---
        gbc.gridx = 1;
        gbc.gridy = 1;

        // Un GridLayout interno de 3 filas y 1 columna para apilar los botones ordenadamente
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setOpaque(false); // Transparente para no tapar el fondo degradado

        // Instanciamos usando tu clase de botones personalizados
        btnGestionUsuarios = new BotonModerno("Gestión de Personal (Jefes / Gerentes)");
        btnGestionInfraestructura = new BotonModerno("Gestión de Infraestructura (Líneas / Estaciones)");

        // Botón de cierre de sesión con color de advertencia integrado
        btnCerrarSesion = new BotonModerno("Cerrar Sesión", new Color(220, 70, 0), Color.WHITE);

        panelBotones.add(btnGestionUsuarios);
        panelBotones.add(btnGestionInfraestructura);
        panelBotones.add(btnCerrarSesion);

        panelPrincipal.add(panelBotones, gbc);

        // Asignamos el panel principal estructurado a la ventana
        add(panelPrincipal);
    }

    // Métodos para que los Controladores capturen los clics de forma limpia
    public void escucharBtnUsuarios(ActionListener listen) { btnGestionUsuarios.addActionListener(listen); }
    public void escucharBtnInfraestructura(ActionListener listen) { btnGestionInfraestructura.addActionListener(listen); }
    public void escucharBtnCerrarSesion(ActionListener listen) { btnCerrarSesion.addActionListener(listen); }
}