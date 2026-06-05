package model;

public class Reporte {
    private int id_Reporte;
    private String estado, ubicacion, descripcion;

    public Reporte(){
    }
    
    public Reporte(int ID, String estado, String ubicacion, String descripcion){
    this.id_Reporte = ID;
    this.estado = estado ;
    this.ubicacion = ubicacion;
    this.descripcion = descripcion;

    }

    public int getId_Reporte() {
        return id_Reporte;
    }

    public String getEstado() {
        return estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId_Reporte(int id_Reporte) {
        this.id_Reporte = id_Reporte;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
