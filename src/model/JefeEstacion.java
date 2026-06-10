package model;

import java.time.LocalDate;

public class JefeEstacion extends Usuario {
    private int id_jefeDeEstacion;
    private Estacion estacionAsignada;

    public JefeEstacion() {
        super();
    }

    public JefeEstacion(int usuario, LocalDate fechaDeNacimiento, String nombre, String apellidoPat, String apellidoMat,
            String correo, String contrasena, int id_jefeDeEstacion, Estacion estacionAsignada) {
        super(usuario, fechaDeNacimiento, nombre, apellidoPat, apellidoMat, correo, contrasena);
        this.id_jefeDeEstacion = id_jefeDeEstacion;
        this.estacionAsignada = estacionAsignada;
    }

    public int getId_jefeDeEstacion() {
        return id_jefeDeEstacion;
    }

    public Estacion getEstacionAsignada() {
        return estacionAsignada;
    }

    public void setId_jefeDeEstacion(int id_jefeDeEstacion) {
        this.id_jefeDeEstacion = id_jefeDeEstacion;
    }

    public void setEstacionAsignada(Estacion estacionAsignada) {
        this.estacionAsignada = estacionAsignada;
    }

}
