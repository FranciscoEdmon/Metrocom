package model;

import java.time.LocalDate;

public class Administrador extends Usuario {
    private int id_Administrador;

    public Administrador(){
        super();
    }
    
    public Administrador(int usuario, LocalDate fechaDeNacimiento, String nombre, String apellidoPat, String apellidoMat, String correo, String contrasena, int IDAdministrador){
        super(usuario, fechaDeNacimiento, nombre, apellidoPat, apellidoMat, correo,contrasena);
        this.id_Administrador = IDAdministrador;
    }

    public int getId_Administrador() {
        return id_Administrador;
    }

    public void setId_Administrador(int id_Administrador) {
        this.id_Administrador = id_Administrador;
    }
    
}
