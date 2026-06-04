package modelo;

import java.time.LocalDate;

public class JefeEstacion extends Usuario{
    private int id_jefeEsatción;
    private Estacion estacionAsignada;

    public JefeEstacion(){
        super();
    }

    public JefeEstacion(int IDJefeEstacion,int Usuario, LocalDate fechaDeNacimiento, String nombre, String apellidoPat, String apellidoMat, String correo, String contraseña, Estacion estacionAsignada){
        super(Usuario, fechaDeNacimiento, nombre, apellidoPat, apellidoMat, correo, contraseña);
        this.id_jefeEsatción = IDJefeEstacion;
        this.estacionAsignada = estacionAsignada;
    }

    public int getId_jefeEsatción() {
        return id_jefeEsatción;
    }

    public Estacion getEstacionAsignada() {
        return estacionAsignada;
    }

    public void setId_jefeEsatción(int id_jefeEsatción) {
        this.id_jefeEsatción = id_jefeEsatción;
    }

    public void setEstacionAsignada(Estacion estacionAsignada) {
        this.estacionAsignada = estacionAsignada;
    }
    
}
