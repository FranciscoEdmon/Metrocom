package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import views.base.VentanaBase;

public class LoginView extends VentanaBase {
    private CampoLoginTexto txtCorreo;
    private CampoLoginPassword txtContrasena;
    private BotonLogin btnIngresar;

    public LoginView() {
        // 1. CAMBIO DE TAMAÑO: Ahora es más ancha (640) que alta (380)
        super("MetroCom - Acceso al Sistema", 640, 380, JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        // Fondo Sólido Plano Local (#FFE99D)
        JPanel panelFondo = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 233, 157));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };

        // 2. CAMBIO EN LA TARJETA: Dimensiones horizontales (540 ancho x 280 alto)
        TarjetaLogin tarjeta = new TarjetaLogin(new GridBagLayout());
        tarjeta.setPreferredSize(new Dimension(540, 280));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15); // Margen interno entre componentes

        // =====================================================================
        //   COLUMNA IZQUIERDA: LOGOTIPO (Ocupa las 3 filas de alto)
        // =====================================================================
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;          // Se estira verticalmente a lo largo de 3 celdas
        gbc.weightx = 0.4;           // Toma el 40% del ancho de la tarjeta
        gbc.fill = GridBagConstraints.CENTER;

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/multimedia/Logotipo.png"));
            Image img = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            tarjeta.add(lblLogo, gbc);
        } catch (Exception e) {
            JLabel lblFallback = new JLabel("METROCOM", SwingConstants.CENTER);
            lblFallback.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblFallback.setForeground(new Color(40, 20, 0));
            tarjeta.add(lblFallback, gbc);
        }

        // =====================================================================
        //   COLUMNA DERECHA: FORMULARIO Y BOTÓN
        // =====================================================================
        gbc.gridheight = 1;          // Restauramos la altura normal de celda
        gbc.weightx = 0.6;           // Toma el 60% del ancho de la tarjeta
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Fila 0: Campo Correo ---
        gbc.gridx = 1;
        gbc.gridy = 0;
        tarjeta.add(crearCampoTexto("Correo Electrónico"), gbc);

        // --- Fila 1: Campo Contraseña ---
        gbc.gridx = 1;
        gbc.gridy = 1;
        tarjeta.add(crearCampoPass("Contraseña"), gbc);

        // --- Fila 2: Botón Ingresar ---
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 15, 5, 15); // Espaciado extra superior para el botón
        btnIngresar = new BotonLogin("Ingresar");
        btnIngresar.setPreferredSize(new Dimension(200, 38));
        tarjeta.add(btnIngresar, gbc);

        panelFondo.add(tarjeta);
        setContentPane(panelFondo);
    }

    private JPanel crearCampoTexto(String titulo) {
        JPanel panel = new JPanel(new BorderLayout(3, 3));
        panel.setOpaque(false);

        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(new Color(40, 20, 0));
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));

        txtCorreo = new CampoLoginTexto("ejemplo@correo.com");

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(txtCorreo, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearCampoPass(String titulo) {
        JPanel panel = new JPanel(new BorderLayout(3, 3));
        panel.setOpaque(false);

        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(new Color(40, 20, 0));
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));

        txtContrasena = new CampoLoginPassword("••••••••");

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(txtContrasena, BorderLayout.CENTER);
        return panel;
    }

    // Getters y mapeo de eventos
    public String getCorreo() { return txtCorreo.getText().trim(); }
    public String getContrasena() { return new String(txtContrasena.getPassword()); }
    public void escucharBtnIngresar(ActionListener listen) { btnIngresar.addActionListener(listen); }
    public void mostrarMensajeError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
    }

    // =========================================================================
    //   COMPONENTES GRÁFICOS PRIVADOS (MANTIENEN EL ESTILO PLANO REDONDEADO)
    // =========================================================================

    private static class TarjetaLogin extends JPanel {
        public TarjetaLogin(LayoutManager layout) {
            super(layout);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(255, 210, 106)); // #FFD26A plano
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

            g2.setColor(new Color(255, 165, 4)); // #FFA504 plano
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class CampoLoginTexto extends JTextField {
        private final String placeholder;

        public CampoLoginTexto(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(false);
            setForeground(new Color(40, 20, 0));
            setCaretColor(new Color(40, 20, 0));
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            g2.setColor(new Color(255, 165, 4));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
            g2.dispose();
            super.paintComponent(g);

            if (getText().isEmpty()) {
                Graphics2D gP = (Graphics2D) g.create();
                gP.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gP.setColor(new Color(170, 170, 170));
                gP.setFont(getFont().deriveFont(Font.ITALIC));
                FontMetrics fm = gP.getFontMetrics();
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                gP.drawString(placeholder, getInsets().left, y);
                gP.dispose();
            }
        }
    }

    private static class CampoLoginPassword extends JPasswordField {
        private final String placeholder;

        public CampoLoginPassword(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(false);
            setForeground(new Color(40, 20, 0));
            setCaretColor(new Color(40, 20, 0));
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            g2.setColor(new Color(255, 165, 4));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
            g2.dispose();
            super.paintComponent(g);

            if (getPassword().length == 0) {
                Graphics2D gP = (Graphics2D) g.create();
                gP.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gP.setColor(new Color(170, 170, 170));
                gP.setFont(getFont().deriveFont(Font.ITALIC));
                FontMetrics fm = gP.getFontMetrics();
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                gP.drawString(placeholder, getInsets().left, y);
                gP.dispose();
            }
        }
    }

    private static class BotonLogin extends JButton {
        private final Color colorFondo = new Color(255, 165, 4);
        private final Color colorHover = new Color(255, 188, 55);
        private final Color colorClick = new Color(255, 123, 0);
        private boolean mouseOver = false;

        public BotonLogin(String texto) {
            super(texto);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(new Color(40, 20, 0));
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { mouseOver = true; repaint(); }
                @Override public void mouseExited(MouseEvent e) { mouseOver = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isPressed()) {
                g2.setColor(colorClick);
            } else if (mouseOver) {
                g2.setColor(colorHover);
            } else {
                g2.setColor(colorFondo);
            }

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}