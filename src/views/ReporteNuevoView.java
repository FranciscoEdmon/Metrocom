package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.TipoInfra;
import model.TipoDano;
import model.Prioridad;

public class ReporteNuevoView extends JFrame {
    private JComboBox<TipoInfra> cbTipoInfra;
    private JComboBox<TipoDano> cbTipoDano;
    private JComboBox<Prioridad> cbPrioridad;
    private JTextField txtUbicacionExacta;
    private JTextArea txtDescripcion;
    private JButton btnGuardar, btnCancelar;

    public ReporteNuevoView() {
        setTitle("MetroCom - Formulario de Levantamiento de Reporte");
        setSize(550, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Detalles del Incidente en Infraestructura"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ComboBoxes de catálogos relacionales
        cbTipoInfra = new JComboBox<>();
        cbTipoDano = new JComboBox<>();
        cbPrioridad = new JComboBox<>();

        txtUbicacionExacta = new JTextField();
        txtDescripcion = new JTextArea(5, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);

        // Distribución en GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(new JLabel("Tipo Infraestructura:"), gbc);
        gbc.gridx = 1; panelFormulario.add(cbTipoInfra, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(new JLabel("Especificación de Daño:"), gbc);
        gbc.gridx = 1; panelFormulario.add(cbTipoDano, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(new JLabel("Nivel Prioridad:"), gbc);
        gbc.gridx = 1; panelFormulario.add(cbPrioridad, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelFormulario.add(new JLabel("Ubicación Exacta:"), gbc);
        gbc.gridx = 1; panelFormulario.add(txtUbicacionExacta, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelFormulario.add(new JLabel("Descripción Técnica:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; panelFormulario.add(scrollDesc, gbc);

        // Botones de control de flujo
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnGuardar = new JButton("Enviar Reporte");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);

        setLayout(new BorderLayout());
        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Métodos para rellenar los combos desde el controlador
    public void agregarInfraestructura(TipoInfra item) { cbTipoInfra.addItem(item); }
    public void agregarDaño(TipoDano item) { cbTipoDano.addItem(item); }
    public void agregarPrioridad(Prioridad item) { cbPrioridad.addItem(item); }

    // Getters de objetos seleccionados y textos
    public TipoInfra getInfraSeleccionada() { return (TipoInfra) cbTipoInfra.getSelectedItem(); }
    public TipoDano getDañoSeleccionado() { return (TipoDano) cbTipoDano.getSelectedItem(); }
    public Prioridad getPrioridadSeleccionada() { return (Prioridad) cbPrioridad.getSelectedItem(); }
    public String getUbicacionExacta() { return txtUbicacionExacta.getText().trim(); }
    public String getDescripcion() { return txtDescripcion.getText().trim(); }

    public void addListeners(ActionListener l) {
        btnGuardar.addActionListener(l);
        btnCancelar.addActionListener(l);
    }
}