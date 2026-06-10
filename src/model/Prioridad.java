package model;

public class Prioridad {
    private int id_prioridad;
    private String prioridad;

    public Prioridad(){
    }

    public Prioridad(int IdPrioridad, String prioridad){
        this.id_prioridad = IdPrioridad ;
        this.prioridad = prioridad;
    }

    public int getId_prioridad() {
        return id_prioridad;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setId_prioridad(int id_prioridad) {
        this.id_prioridad = id_prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    

    @Override
    public String toString(){
        return this.prioridad;
    }
}
