package views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import views.base.VentanaBase;
import views.base.TarjetaModerna;
import views.base.BotonModerno;

public class MisReportesView extends VentanaBase {

    private JTable tblMisReportes;
    private JLabel lblTotalReportes;
    private JLabel lblTitulo;

    private JButton btnActualizar;
    private JButton btnRegresar;

    // Contenedores dinámicos para alternar el estado
    private JPanel panelCentralDinamico;
    private JScrollPane scrollTabla;
    private JPanel panelNubeVacia;

    public MisReportesView() {
        // Mismo tamaño panorámico de NuevoReporteView para mantener simetría en el sistema
        super("MetroCom - Mis Reportes", 760, 490, JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        // Fondo degradado general del sistema
        PanelModerno panelPrincipal = new PanelModerno(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        // Tarjeta contenedora translúcida oscura
        TarjetaModerna tarjeta = new TarjetaModerna(new GridBagLayout());
        tarjeta.setPreferredSize(new Dimension(710, 420));

        GridBagConstraints gbcT = new GridBagConstraints();
        gbcT.insets = new Insets(6, 14, 6, 14);

        // =====================================================================
        //   COLUMNA IZQUIERDA: DECORACIÓN / ICONO FIJO (30% del ancho)
        // =====================================================================
        gbcT.gridx = 0;
        gbcT.gridy = 0;
        gbcT.gridheight = 4;
        gbcT.weightx = 0.25;
        gbcT.fill = GridBagConstraints.CENTER;

        try {
            ImageIcon historyIcon = new ImageIcon(getClass().getResource("/multimedia/HistorialIcon.png"));
            Image img = historyIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel lblIconoLateral = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            tarjeta.add(lblIconoLateral, gbcT);
        } catch (Exception e) {
            JLabel lblFallback = new JLabel("📋", SwingConstants.CENTER);
            lblFallback.setFont(new Font("Segoe UI", Font.PLAIN, 80));
            tarjeta.add(lblFallback, gbcT);
        }

        // =====================================================================
        //   COLUMNA DERECHA: ENCABEZADO (Título y Contador)
        // =====================================================================
        gbcT.gridheight = 1;
        gbcT.weightx = 0.75;
        gbcT.fill = GridBagConstraints.HORIZONTAL;
        gbcT.gridx = 1;
        gbcT.gridy = 0;

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);

        lblTitulo = new JLabel("Historial de Fallas Reportadas", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(255, 233, 157)); // Color FFE99D para coherencia visual

        lblTotalReportes = new JLabel("Total de reportes: 0");
        lblTotalReportes.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotalReportes.setForeground(Color.WHITE);

        panelHeader.add(lblTitulo, BorderLayout.WEST);
        panelHeader.add(lblTotalReportes, BorderLayout.EAST);
        tarjeta.add(panelHeader, gbcT);

        // =====================================================================
        //   COLUMNA DERECHA: CONTENEDOR DINÁMICO (Tabla o Nubecita)
        // =====================================================================
        gbcT.gridy = 1;
        gbcT.weighty = 1.0; // Absorbe el espacio vertical disponible
        gbcT.fill = GridBagConstraints.BOTH;

        panelCentralDinamico = new JPanel(new CardLayout());
        panelCentralDinamico.setOpaque(false);

        // --- ESTADO A: Con Reportes (Tabla Estilo Premium Blanco) ---
        tblMisReportes = new JTable();
        tblMisReportes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblMisReportes.setFillsViewportHeight(true);
        tblMisReportes.setRowHeight(28);
        tblMisReportes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblMisReportes.setBackground(Color.WHITE);
        tblMisReportes.setForeground(new Color(30, 30, 30));
        tblMisReportes.setGridColor(new Color(230, 230, 230));

        // Estilizar el encabezado de la tabla (Naranja #FFA504)
        JTableHeader header = tblMisReportes.getTableHeader();
        header.setBackground(new Color(255, 165, 4));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setReorderingAllowed(false);

        // Centrar los datos de las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblMisReportes.setDefaultRenderer(Object.class, centerRenderer);

        scrollTabla = new JScrollPane(tblMisReportes);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 4), 1));
        scrollTabla.getViewport().setBackground(Color.WHITE);

        // --- ESTADO B: Sin Reportes (Nubecita Sonriente) ---
        panelNubeVacia = new JPanel(new GridBagLayout());
        panelNubeVacia.setOpaque(false);

        GridBagConstraints gbcNube = new GridBagConstraints();
        gbcNube.gridx = 0;
        gbcNube.fill = GridBagConstraints.CENTER;

        JLabel lblNubeImg = new JLabel();
        try {
            ImageIcon nubeIcon = new ImageIcon(getClass().getResource("/multimedia/NubeSonriente.png"));
            Image imgNube = nubeIcon.getImage().getScaledInstance(110, 90, Image.SCALE_SMOOTH);
            lblNubeImg.setIcon(new ImageIcon(imgNube));
        } catch (Exception e) {
            // Fallback amigable si el recurso no está compilado aún
            lblNubeImg.setText("☁️ 😊");
            lblNubeImg.setFont(new Font("Segoe UI", Font.PLAIN, 65));
        }

        gbcNube.gridy = 0;
        panelNubeVacia.add(lblNubeImg, gbcNube);

        JLabel lblMensajeVacio = new JLabel("¡Excelente! No tienes reportes activos en este momento.", SwingConstants.CENTER);
        lblMensajeVacio.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblMensajeVacio.setForeground(new Color(255, 233, 157));

        gbcNube.gridy = 1;
        gbcNube.insets = new Insets(10, 0, 0, 0);
        panelNubeVacia.add(lblMensajeVacio, gbcNube);

        // Registrar ambos estados en el CardLayout
        panelCentralDinamico.add(scrollTabla, "CON_DATOS");
        panelCentralDinamico.add(panelNubeVacia, "VACIO");

        tarjeta.add(panelCentralDinamico, gbcT);

        // =====================================================================
        //   FILA INFERIOR: ACCIONES
        // =====================================================================
        gbcT.gridy = 2;
        gbcT.weighty = 0.0;
        gbcT.fill = GridBagConstraints.HORIZONTAL;
        gbcT.insets = new Insets(12, 14, 5, 14);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panelAcciones.setOpaque(false);

        btnRegresar = new BotonModerno("Regresar al Menú", new Color(120, 120, 120), Color.WHITE);
        btnActualizar = new BotonModerno("Actualizar Tabla");

        panelAcciones.add(btnRegresar);
        panelAcciones.add(btnActualizar);
        tarjeta.add(panelAcciones, gbcT);

        panelPrincipal.add(tarjeta, gbc);
        setContentPane(panelPrincipal);

        // Por defecto, inicializar en estado vacío hasta que el controlador inyecte información
        mostrarEstadoVacio(true);
    }

    /**
     * MÉTODO CLAVE PARA EL CONTROLADOR:
     * Recibe los datos de la BD, calcula las filas y cambia entre la tabla o la nubecita de forma inteligente.
     */
    public void setModeloTabla(TableModel modelo) {
        if (modelo != null && modelo.getRowCount() > 0) {
            tblMisReportes.setModel(modelo);
            lblTotalReportes.setText("Total de reportes: " + modelo.getRowCount());
            mostrarEstadoVacio(false);
        } else {
            lblTotalReportes.setText("Total de reportes: 0");
            mostrarEstadoVacio(true);
        }
    }

    private void mostrarEstadoVacio(boolean estaVacio) {
        CardLayout cl = (CardLayout) panelCentralDinamico.getLayout();
        if (estaVacio) {
            cl.show(panelCentralDinamico, "VACIO");
        } else {
            cl.show(panelCentralDinamico, "CON_DATOS");
        }
        panelCentralDinamico.revalidate();
        panelCentralDinamico.repaint();
    }

    // Getters y mapeos necesarios por tus controladores antiguos
    public JTable getTblMisReportes() { return tblMisReportes; }

    public void setTotalReportes(int total) {
        lblTotalReportes.setText("Total de reportes: " + total);
        mostrarEstadoVacio(total == 0);
    }

    // Vinculación limpia de Listeners
    public void escucharBtnRegresar(ActionListener l) { btnRegresar.addActionListener(l); }
    public void escucharBtnActualizar(ActionListener l) { btnActualizar.addActionListener(l); }
}