package modelo;
import java.time.LocalDate; 

abstract public class Usuario {
    private int id_Usuario;
    private LocalDate fechaDeNacimiento;
    private String nombre, apellidoPat, apellidoMat, correo, contrasena;
    
    public Usuario(){
    }

    public Usuario(int usuario, LocalDate fechaDeNacimiento, String nombre, String apellidoPat, String apellidoMat, String correo, String contrasena){

        this.id_Usuario = usuario ;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.nombre = nombre ;
        this.correo = correo ;
        this.apellidoPat = apellidoPat ;
        this.apellidoMat = apellidoMat ;
        this.contrasena = contrasena;

    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPat() {
        return apellidoPat;
    }

    public String getApellidoMat() {
        return apellidoMat;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }
    
    public void setFechaDeNacimiento(LocalDate fechaNacimiento) {
        this.fechaDeNacimiento = fechaNacimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPat(String apellidoPat) {
        this.apellidoPat = apellidoPat;
    }

    public void setApellidoMat(String apellidoMat) {
        this.apellidoMat = apellidoMat;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
}
