package model;

public class Linea {
    private int  id_Linea;
    private String nombreLinea, colorLinea;

    public Linea(){
    }

    public Linea(int ID, String nombre, String color){
        this.id_Linea = ID;
        this.nombreLinea = nombre ;
        this.colorLinea = color;
    }

    public int getId_Linea() {
        return id_Linea;
    }

    public String getNombreLinea() {
        return nombreLinea;
    }

    public String getColorLinea() {
        return colorLinea;
    }

    public void setId_Linea(int id_Linea) {
        this.id_Linea = id_Linea;
    }

    public void setNombreLinea(String nombreLinea) {
        this.nombreLinea = nombreLinea;
    }

    public void setColorLinea(String colorLinea) {
        this.colorLinea = colorLinea;
    }
    
    @Override
    public String toString() {
        return this.nombreLinea;
    }
}
