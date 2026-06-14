package model;

public class Estacion {
    int id_Estacion, id_linea;
    String nombreEstacion;
    boolean transbordo;

    public Estacion(){
    }

    public Estacion(int id_estacion, String nombreEstacion, boolean transbordo, int id_linea){
        this.id_Estacion = id_estacion;
        this.nombreEstacion = nombreEstacion;
        this.transbordo = transbordo;
        this.id_linea = id_linea;
    }

    public int getId_Estacion() { return id_Estacion; }
    public String getNombreEstacion() { return nombreEstacion; }
    public boolean isTransbordo() { return transbordo; }
    public int getId_linea() { return id_linea; } // Agregado para consistencia

    public void setId_Estacion(int id_Estacion) { this.id_Estacion = id_Estacion; }
    public void setNombreEstacion(String nombreEstacion) { this.nombreEstacion = nombreEstacion; }
    public void setTransbordo(boolean transbordo) { this.transbordo = transbordo; }
    public void setId_linea(int id_linea) { this.id_linea = id_linea; } // Agregado para consistencia

    @Override
    public String toString() {
        return this.nombreEstacion;
    }
}