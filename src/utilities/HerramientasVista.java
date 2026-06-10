package utilities;

import javax.swing.JComboBox;
import java.util.List;

import dao.LineaDAO;
import dao.PrioridadDAO;
import dao.TipoDañoDAO;
import dao.TipoInfraDAO;
import model.Estacion;
import model.Linea;
import model.Prioridad;
import model.TipoDaño;
import model.TipoInfra;
import dao.EstacionDAO;


    /*  Estas "herramienta" lo que hace es cargar las lineas en los combo box.
    Por ende, solo hay que llamarlo a donde se necesita, en cuestion de los controladores...(Son todos los que acaban en -Combo)*/

public class HerramientasVista {

    //Esta es especificamete para Los comboBox que muestren las líneas
    public static void cargarLineasEnCombo(JComboBox<Linea> comboLineas) {
        
        comboLineas.removeAllItems();
        
        LineaDAO lineaDAO = new LineaDAO();
        List<Linea> listaLineas = lineaDAO.obtenerTodasLasLineas();

        for (Linea linea : listaLineas) {
            comboLineas.addItem(linea);
        }
    }
    
    //Este es especificamente para los combo box que muestren las Estaciones
    public static void cargarEstacionesEnCombo(JComboBox<Estacion> comboEstaciones, int idLineaFiltro) {
        
        comboEstaciones.removeAllItems(); 
        
        EstacionDAO estacionDAO = new EstacionDAO();
        List<Estacion> listaEstaciones = estacionDAO.obtenerEstacionesPorLinea(idLineaFiltro);

        for (Estacion estacion : listaEstaciones) {
            comboEstaciones.addItem(estacion);
        }
    }

    //Esta es para los comboBox que ccarguen los tipos de infraestructura
    public static void CargarTipoInfraCombo(JComboBox<TipoInfra> comboInfra) {

        comboInfra.removeAllItems(); 

        TipoInfraDAO infraDAO = new TipoInfraDAO();
        List<TipoInfra> listaInfra = infraDAO.ObetenerTodosLosTInfra();

        for(TipoInfra infra : listaInfra){
            comboInfra.addItem(infra);
        }
    }

        //Herramienta para cargar los comboBox de tipo de daño
    public static void CargarTipoDanoCombo(JComboBox<TipoDaño> comboDano){

        comboDano.removeAllItems();

        TipoDañoDAO danoDAO = new TipoDañoDAO();
        List<TipoDaño> listaDano = danoDAO.ObtenerLosTDano();

        for(TipoDaño daño : listaDano){
            comboDano.addItem(daño);
        }
    }

    //Heramienta para todos los comboBox de Prioridades
    public static void CargarPrioridadesCombo(JComboBox<Prioridad> comboPrioridad){

        comboPrioridad.removeAllItems();

        PrioridadDAO prioridadDAO = new PrioridadDAO();
        List<Prioridad> listaPrioridad = prioridadDAO.ObtenerLasPrioridades();

        for(Prioridad prioridad : listaPrioridad){
            comboPrioridad.addItem(prioridad);
        }
    }


}
