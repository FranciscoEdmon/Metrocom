package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.Prioridad;
import model.TipoDaño;
import model.TipoInfra;

public class NuevoReporteView extends JFrame {

    private JComboBox<TipoInfra> cmbInfraestructura;
    private JComboBox<TipoDaño> cmbTipoDano;
    private JComboBox<Prioridad> cmbPrioridad;
    private JTextField txtUbicacionEspecifica;
    private JTextArea txaDescripcion;

    private JButton btnEnviarReporte;
    private JButton btnLimpiar;
    private JButton btnCancelar;

    public NuevoReporteView() {
        setTitle("MetroCom - Levantar Nuevo Reporte");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo cierra esta ventana, no toda la app
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Título superior
        JLabel lblTitulo = new JLabel("Formulario de Falla en Estación", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Centro: Formulario
        JPanel formReporte = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; formReporte.add(new JLabel("Equipo Afectado:"), gbc);
        cmbInfraestructura = new JComboBox<>();
        gbc.gridx = 1; formReporte.add(cmbInfraestructura, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formReporte.add(new JLabel("Naturaleza del Daño:"), gbc);
        cmbTipoDano = new JComboBox<>();
        gbc.gridx = 1; formReporte.add(cmbTipoDano, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formReporte.add(new JLabel("Nivel de Urgencia:"), gbc);
        cmbPrioridad = new JComboBox<>();
        gbc.gridx = 1; formReporte.add(cmbPrioridad, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formReporte.add(new JLabel("Ubicación Exacta:"), gbc);
        txtUbicacionEspecifica = new JTextField(15);
        txtUbicacionEspecifica.setToolTipText("Ej. Andén Sur, Torniquetes Norte");
        gbc.gridx = 1; formReporte.add(txtUbicacionEspecifica, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.NORTH;
        formReporte.add(new JLabel("Descripción Detallada:"), gbc);
        txaDescripcion = new JTextArea(5, 15);
        txaDescripcion.setLineWrap(true);
        txaDescripcion.setWrapStyleWord(true);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        formReporte.add(new JScrollPane(txaDescripcion), gbc);

        panelPrincipal.add(formReporte, BorderLayout.CENTER);

        // Sur: Botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnCancelar = new JButton("Cancelar");
        btnLimpiar = new JButton("Limpiar");
        btnEnviarReporte = new JButton("Enviar Reporte");
        btnEnviarReporte.setBackground(new Color(102, 204, 102));

        panelBotones.add(btnCancelar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEnviarReporte);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    // Getters para el controlador
    public TipoInfra getInfraestructura() { return (TipoInfra) cmbInfraestructura.getSelectedItem(); }
    public TipoDaño getTipoDano() { return (TipoDaño) cmbTipoDano.getSelectedItem(); }
    public Prioridad getPrioridad() { return (Prioridad) cmbPrioridad.getSelectedItem(); }
    public String getUbicacion() { return txtUbicacionEspecifica.getText().trim(); }
    public String getDescripcion() { return txaDescripcion.getText().trim(); }

    // Getters de los JComboBox para que HerramientasVista los pueda llenar
    public JComboBox<TipoInfra> getCmbInfraestructura() { return cmbInfraestructura; }
    public JComboBox<TipoDaño> getCmbTipoDano() { return cmbTipoDano; }
    public JComboBox<Prioridad> getCmbPrioridad() { return cmbPrioridad; }

    public void limpiarCampos() {
        if(cmbInfraestructura.getItemCount() > 0) cmbInfraestructura.setSelectedIndex(0);
        if(cmbTipoDano.getItemCount() > 0) cmbTipoDano.setSelectedIndex(0);
        if(cmbPrioridad.getItemCount() > 0) cmbPrioridad.setSelectedIndex(0);
        txtUbicacionEspecifica.setText("");
        txaDescripcion.setText("");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    // Listeners
    public void escucharBtnEnviar(ActionListener l) { btnEnviarReporte.addActionListener(l); }
    public void escucharBtnLimpiar(ActionListener l) { btnLimpiar.addActionListener(l); }
    public void escucharBtnCancelar(ActionListener l) { btnCancelar.addActionListener(l); }
}