package modelo;

import java.time.LocalDate;

public class Administrador extends Usuario {
    private int id_Administrador;

    public Administrador(){
        super();
    }
    
    public Administrador(int IDAdministrador,int Usuario, LocalDate fechaDeNacimiento, String nombre, String apellidoPat, String apellidoMat, String correo, String contraseña){
        super(Usuario, fechaDeNacimiento, nombre, apellidoPat, apellidoMat, correo,contraseña);
        this.id_Administrador = IDAdministrador;
    }

    public int getId_Administrador() {
        return id_Administrador;
    }

    public void setId_Administrador(int id_Administrador) {
        this.id_Administrador = id_Administrador;
    }
    
}
