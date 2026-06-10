package model;

import java.time.LocalDateTime;

public class Reporte {
    private int id_Reporte;
    private String estado, ubicacionExacta, descripcion;
    LocalDateTime fechaCreacion;
    JefeEstacion jefeEstacion;
    private Prioridad prioridad;
    private TipoInfra tipoInfra;
    private TipoDaño tipoDanio;

    public Reporte(){
    }
    
    public Reporte(int id_Reporte, String estado, String ubicacionExacta, String descripcion, LocalDateTime fechaCreacion, JefeEstacion jefeEstacion, Prioridad prioridad, TipoInfra tipoInfra, TipoDaño tipoDanio){
    this.id_Reporte = id_Reporte;
    this.estado = estado ;
    this.ubicacionExacta = ubicacionExacta;
    this.descripcion = descripcion;
    this.fechaCreacion = fechaCreacion;
    this.jefeEstacion = jefeEstacion;
    this.prioridad = prioridad;
    this.tipoInfra = tipoInfra ;
    this.tipoDanio = tipoDanio;

    }

    public int getId_Reporte() {
        return id_Reporte;
    }

    public String getEstado() {
        return estado;
    }

    public String getUbicacionExacta() {
        return ubicacionExacta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public JefeEstacion getJefeEstacion() {
        return jefeEstacion;
    }

    public Prioridad getPrioridad (){
        return prioridad;
    }

    public TipoDaño getTipoDaño (){
        return tipoDanio;
    }

    public TipoInfra getTipoInfra (){
        return tipoInfra;
    }

    public void setId_Reporte(int id_Reporte) {
        this.id_Reporte = id_Reporte;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setUbicacionExacta(String ubicacionExacta) {
        this.ubicacionExacta = ubicacionExacta;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setJefeEstacion(JefeEstacion jefeEstacion) {
        this.jefeEstacion = jefeEstacion;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
    
    public void setTipoDaño(TipoDaño tipoDanio) {
        this.tipoDanio = tipoDanio;
    }
    
    public void setTipoInfra(TipoInfra tipoInfra) {
        this.tipoInfra = tipoInfra;
    }
}
