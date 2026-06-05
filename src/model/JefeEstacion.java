package model;

import java.time.LocalDate;

public class JefeEstacion extends Usuario{
    private int id_jefeEsatcion;
    private Estacion estacionAsignada;

    public JefeEstacion(){
        super();
    }

    public JefeEstacion(int usuario, LocalDate fechaDeNacimiento, String nombre, String apellidoPat, String apellidoMat, String correo, String contrasena, int IDJefeEstacion, Estacion estacionAsignada){
        super(usuario, fechaDeNacimiento, nombre, apellidoPat, apellidoMat, correo, contrasena);
        this.id_jefeEsatcion = IDJefeEstacion;
        this.estacionAsignada = estacionAsignada;
    }

    public int getId_jefeEsatcion() {
        return id_jefeEsatcion;
    }

    public Estacion getEstacionAsignada() {
        return estacionAsignada;
    }

    public void setId_jefeEsatción(int id_jefeEsatcion) {
        this.id_jefeEsatcion = id_jefeEsatcion;
    }

    public void setEstacionAsignada(Estacion estacionAsignada) {
        this.estacionAsignada = estacionAsignada;
    }
    
}
