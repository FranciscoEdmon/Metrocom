package modelo;

public class Estacion {
    int id_Estacion;
    String nombreEstacion;
    boolean transbordo;

    public Estacion(){
    }
    
    public Estacion(int ID, String nombre, boolean transbordo){
        
    this.id_Estacion = ID ;
    this.nombreEstacion = nombre ;
    this.transbordo = transbordo ;
    
    }

    public int getId_Estacion() {
        return id_Estacion;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public boolean isTransbordo() {
        return transbordo;
    }

    public void setId_Estacion(int id_Estacion) {
        this.id_Estacion = id_Estacion;
    }

    public void setNombreEstacion(String nombreEstacion) {
        this.nombreEstacion = nombreEstacion;
    }

    public void setTransbordo(boolean transbordo) {
        this.transbordo = transbordo;
    }
    
    @Override
    public String toString() {
    return this.nombreEstacion;
}
}
