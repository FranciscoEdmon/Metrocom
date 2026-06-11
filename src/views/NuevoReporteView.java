package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.Prioridad;
import model.TipoDaño;
import model.TipoInfra;
import views.base.VentanaBase;
import views.base.TarjetaModerna;
import views.base.BotonModerno;

public class NuevoReporteView extends VentanaBase {

    private JComboBox<TipoInfra> cmbInfraestructura;
    private JComboBox<TipoDaño> cmbTipoDano;
    private JComboBox<Prioridad> cmbPrioridad;
    private JTextField txtUbicacionEspecifica;
    private JTextArea txaDescripcion;

    private JButton btnEnviarReporte;
    private JButton btnLimpiar;
    private JButton btnCancelar;

    // VARIABLE NUEVA: Ahora es global para que el controlador pueda modificarla dinámicamente
    private JLabel lblIcono;

    public NuevoReporteView() {
        super("MetroCom - Levantar Nuevo Reporte de Falla", 760, 490, JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        PanelModerno panelPrincipal = new PanelModerno(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        TarjetaModerna tarjeta = new TarjetaModerna(new GridBagLayout());
        tarjeta.setPreferredSize(new Dimension(710, 420));

        GridBagConstraints gbcT = new GridBagConstraints();
        gbcT.insets = new Insets(6, 14, 6, 14);

        // =====================================================================
        //   COLUMNA IZQUIERDA: ESPACIO PARA LA IMAGEN DE LA BD
        // =====================================================================
        gbcT.gridx = 0;
        gbcT.gridy = 0;
        gbcT.gridheight = 5;
        gbcT.weightx = 0.25;
        gbcT.fill = GridBagConstraints.CENTER;

        // Colocamos temporalmente un texto o emoji de carga.
        // El controlador se encargará de reemplazar esto inmediatamente.
        lblIcono = new JLabel("⏳", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 70));
        tarjeta.add(lblIcono, gbcT);

        // =====================================================================
        //   COLUMNA DERECHA: CAMPOS BLANCOS (ESTILO LOGIN)
        // =====================================================================
        gbcT.gridheight = 1;
        gbcT.weightx = 0.75;
        gbcT.fill = GridBagConstraints.HORIZONTAL;
        gbcT.gridx = 1;

        cmbInfraestructura = new JComboBox<>();
        cmbTipoDano = new JComboBox<>();
        cmbPrioridad = new JComboBox<>();
        txtUbicacionEspecifica = new JTextField();
        txaDescripcion = new JTextArea(3, 20);
        txaDescripcion.setLineWrap(true);
        txaDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txaDescripcion);

        gbcT.gridy = 0;
        tarjeta.add(crearFilaFormulario("Tipo de Infraestructura afectada:", cmbInfraestructura), gbcT);

        gbcT.gridy = 1;
        tarjeta.add(crearFilaFormulario("Tipo de Daño / Falla detectada:", cmbTipoDano), gbcT);

        gbcT.gridy = 2;
        tarjeta.add(crearFilaFormulario("Prioridad del Reporte:", cmbPrioridad), gbcT);

        gbcT.gridy = 3;
        tarjeta.add(crearFilaFormulario("Ubicación Específica (Ej: Andén Central, Torniquetes Sur):", txtUbicacionEspecifica), gbcT);

        gbcT.gridy = 4;
        tarjeta.add(crearFilaFormulario("Descripción Detallada de la Incidencia:", scrollDesc), gbcT);

        // =====================================================================
        //   FILA DE ACCIONES
        // =====================================================================
        gbcT.gridx = 0;
        gbcT.gridy = 5;
        gbcT.gridwidth = 2;
        gbcT.insets = new Insets(15, 14, 5, 14);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        panelBotones.setOpaque(false);

        btnLimpiar = new BotonModerno("Limpiar", new Color(120, 120, 120), Color.WHITE);
        btnCancelar = new BotonModerno("Cancelar", new Color(210, 60, 0), Color.WHITE);
        btnEnviarReporte = new BotonModerno("Enviar Reporte");

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnEnviarReporte);

        tarjeta.add(panelBotones, gbcT);

        panelPrincipal.add(tarjeta, gbc);
        setContentPane(panelPrincipal);
    }

    /**
     * MÉTODO CLAVE: Permite al controlador enviarle la imagen obtenida de la base de datos
     */
    public void setIconoReporte(Image img) {
        if (img != null) {
            // Escalamos la imagen de la BD al tamaño correcto de la columna izquierda
            Image imgEscalada = img.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            lblIcono.setText(""); // Removemos el emoji de carga temporal
            lblIcono.setIcon(new ImageIcon(imgEscalada));
        } else {
            // Si en la BD no hay foto o marca error, ponemos un fallback por defecto
            lblIcono.setText("📝");
            lblIcono.setIcon(null);
            lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 85));
        }
        lblIcono.repaint();
    }

    private JPanel crearFilaFormulario(String tituloLabel, JComponent componente) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(false);

        JLabel lbl = new JLabel(tituloLabel);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(255, 233, 157));

        componente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        componente.setBackground(Color.WHITE);
        componente.setForeground(new Color(30, 30, 30));

        if (componente instanceof JTextField) {
            JTextField txt = (JTextField) componente;
            txt.setCaretColor(new Color(30, 30, 30));
            txt.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 165, 4), 1),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
        } else if (componente instanceof JScrollPane) {
            JScrollPane scroll = (JScrollPane) componente;
            scroll.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 4), 1));
            scroll.getViewport().setBackground(Color.WHITE);

            txaDescripcion.setBackground(Color.WHITE);
            txaDescripcion.setForeground(new Color(30, 30, 30));
            txaDescripcion.setCaretColor(new Color(30, 30, 30));
            txaDescripcion.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        } else if (componente instanceof JComboBox) {
            JComboBox<?> combo = (JComboBox<?>) componente;
            combo.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 4), 1));
            combo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (isSelected) {
                        c.setBackground(new Color(255, 165, 4));
                        c.setForeground(Color.WHITE);
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(new Color(30, 30, 30));
                    }
                    return c;
                }
            });
        }

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(componente, BorderLayout.CENTER);
        return panel;
    }

    // Getters y mapeos lógicos respetados al 100%
    public TipoInfra getInfraestructura() { return (TipoInfra) cmbInfraestructura.getSelectedItem(); }
    public TipoDaño getTipoDano() { return (TipoDaño) cmbTipoDano.getSelectedItem(); }
    public Prioridad getPrioridad() { return (Prioridad) cmbPrioridad.getSelectedItem(); }
    public String getUbicacion() { return txtUbicacionEspecifica.getText().trim(); }
    public String getDescripcion() { return txaDescripcion.getText().trim(); }

    public JComboBox<TipoInfra> getCmbInfraestructura() { return cmbInfraestructura; }
    public JComboBox<TipoDaño> getCmbTipoDano() { return cmbTipoDano; }
    public JComboBox<Prioridad> getCmbPrioridad() { return cmbPrioridad; }

    public void limpiarCampos() {
        if (cmbInfraestructura.getItemCount() > 0) cmbInfraestructura.setSelectedIndex(0);
        if (cmbTipoDano.getItemCount() > 0) cmbTipoDano.setSelectedIndex(0);
        if (cmbPrioridad.getItemCount() > 0) cmbPrioridad.setSelectedIndex(0);
        txtUbicacionEspecifica.setText("");
        txaDescripcion.setText("");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void escucharBtnEnviar(ActionListener l) { btnEnviarReporte.addActionListener(l); }
    public void escucharBtnLimpiar(ActionListener l) { btnLimpiar.addActionListener(l); }
    public void escucharBtnCancelar(ActionListener l) { btnCancelar.addActionListener(l); }
}