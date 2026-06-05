package modelo;

public class Prioridad {
    private int id_prioridad;
    private String nivel, criterio;

    public Prioridad(){
    }

    public Prioridad(int IdPrioridad, String nivel, String criterio){
        this.id_prioridad = IdPrioridad ;
        this.nivel = nivel;
        this.criterio = criterio;
    }

    public int getId_prioridad() {
        return id_prioridad;
    }

    public String getNivel() {
        return nivel;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setId_prioridad(int id_prioridad) {
        this.id_prioridad = id_prioridad;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }
    

    @Override
    public String toString(){
        return this.nivel;
    }
}
