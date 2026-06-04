package modelo;

import java.time.LocalDate;

public class GerenteLinea extends Usuario{
    private int id_GerenteLinea;
    private Linea LineaAsignada;

    public GerenteLinea(){
        super();
    }

    public GerenteLinea(int IDGerente,int Usuario, LocalDate fechaDeNacimiento, String nombre, String apellidoPat, String apellidoMat, String correo, String contraseña, Linea LineaAsignada){
        super(Usuario, fechaDeNacimiento, nombre, apellidoPat, apellidoMat, correo, contraseña);
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
