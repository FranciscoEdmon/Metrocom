import javax.swing.*;
import java.awt.*;
import views.*;

public class LauncherPrototipo extends JFrame {

    public LauncherPrototipo() {
        setTitle("MetroCom - Selector de Vistas (Modo Prototipo)");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel contenedor con rejilla ordenada
        JPanel panelButtons = new JPanel(new GridLayout(4, 2, 10, 10));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- BOTÓN 1: LOGIN ---
        JButton btnLogin = new JButton("Ver: Login");
        btnLogin.addActionListener(e -> new LoginView().setVisible(true));
        panelButtons.add(btnLogin);

        // --- BOTÓN 2: MENÚ ADMIN ---
        JButton btnAdmin = new JButton("Ver: Menú Administrador");
        btnAdmin.addActionListener(e -> new AdminMenuView().setVisible(true));
        panelButtons.add(btnAdmin);

        // --- BOTÓN 3: MENÚ GERENTE ---
        JButton btnGerente = new JButton("Ver: Menú Gerente");
        btnGerente.addActionListener(e -> {
            GerenteLineaMenuView v = new GerenteLineaMenuView();
            v.setNombreGerente("Carlos Mendoza (Prueba)");
            v.setLineaAsignada("Línea 1 - Pantitlán / Observatorio");
            v.setVisible(true);
        });
        panelButtons.add(btnGerente);

        // --- BOTÓN 4: MENÚ JEFE ESTACIÓN ---
        JButton btnJefe = new JButton("Ver: Menú Jefe Estación");
        btnJefe.addActionListener(e -> {
            JefeEstacionMenuView v = new JefeEstacionMenuView();
            v.setNombreJefe("Sofía Galindo");
            v.setEstacionAsignada("Balderas");
            v.setVisible(true);
        });
        panelButtons.add(btnJefe);

        // --- BOTÓN 5: NUEVO REPORTE ---
        JButton btnNuevoRep = new JButton("Ver: Crear Nuevo Reporte");
        btnNuevoRep.addActionListener(e -> new NuevoReporteView().setVisible(true));
        panelButtons.add(btnNuevoRep);

        // --- BOTÓN 6: HISTORIAL DE REPORTES ---
        JButton btnMisRep = new JButton("Ver: Historial Mis Reportes");
        btnMisRep.addActionListener(e -> {
            MisReportesView v = new MisReportesView();
            v.setTotalReportes(5); // Datos quemados para ver diseño
            v.setVisible(true);
        });
        panelButtons.add(btnMisRep);

        // --- BOTÓN 7: BANDEJA DE ENTRADA ---
        JButton btnBandeja = new JButton("Ver: Bandeja de Entrada");
        btnBandeja.addActionListener(e -> new BandejaEntradaView().setVisible(true));
        panelButtons.add(btnBandeja);

        // --- BOTÓN 8: REPORTES EN ATENCIÓN ---
        JButton btnAtencion = new JButton("Ver: Reportes en Atención");
        btnAtencion.addActionListener(e -> new ReportesAtencionView().setVisible(true));
        panelButtons.add(btnAtencion);

        add(panelButtons);
    }

    public static void main(String[] args) {
        // Ejecutar el selector de entornos
        SwingUtilities.invokeLater(() -> new LauncherPrototipo().setVisible(true));
    }
}