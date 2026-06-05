package model;

import java.time.LocalDate;

public class GerenteLinea extends Usuario{
    private int id_GerenteLinea;
    private Linea LineaAsignada;

    public GerenteLinea(){
        super();
    }

    public GerenteLinea(int usuario, LocalDate fechaDeNacimiento, String nombre, String apellidoPat, String apellidoMat, String correo, String contrasena, int IDGerente, Linea LineaAsignada){
        super(usuario, fechaDeNacimiento, nombre, apellidoPat, apellidoMat, correo, contrasena);
        this.id_GerenteLinea = IDGerente;
        this.LineaAsignada = LineaAsignada;
    }

    public int getId_GerenteLinea() {
        return id_GerenteLinea;
    }

    public Linea getLineaAsignada() {
        return LineaAsignada;
    }

    public void setId_GerenteLinea(int id_GerenteLinea) {
        this.id_GerenteLinea = id_GerenteLinea;
    }

    public void setLineaAsignada(Linea LineaAsignada) {
        this.LineaAsignada = LineaAsignada;
    }
    
    
}
