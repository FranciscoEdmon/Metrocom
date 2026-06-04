package utilidades;

import javax.swing.JComboBox;
import java.util.List;
import modelo.Linea;
import modelo.Estacion;
import dao.LineaDAO;
import dao.EstacionDAO;

public class HerramientasVista {

    /*  Estas "herramienta" lo que hace es cargar las lineas en los combo box.
    Por ende, solo hay que llamarlo a donde se necesita, en cuestion de los controladores...(Son todos los que acaban en -Combo)*/

    public static void cargarLineasEnCombo(JComboBox<Linea> comboLineas) {
        
        comboLineas.removeAllItems();
        
        LineaDAO lineaDAO = new LineaDAO();
        List<Linea> listaLineas = lineaDAO.obtenerTodasLasLineas();

        for (Linea linea : listaLineas) {
            comboLineas.addItem(linea);
        }
    }

    public static void cargarEstacionesEnCombo(JComboBox<Estacion> comboEstaciones, int idLineaFiltro) {
        
        comboEstaciones.removeAllItems(); 
        
        EstacionDAO estacionDAO = new EstacionDAO();
        List<Estacion> listaEstaciones = estacionDAO.obtenerEstacionesPorLinea(idLineaFiltro);

        for (Estacion estacion : listaEstaciones) {
            comboEstaciones.addItem(estacion);
        }
    }
    
}
