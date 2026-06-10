package model;

public class TipoDaño {
    private int id_TipoDaño;
    private String nombreDaño;

    public TipoDaño(){
    }
    
    public TipoDaño(int idTipoDaño, String nombreDaño){
        this.id_TipoDaño = idTipoDaño ;
        this.nombreDaño = nombreDaño ;
    }

    public int getId_TipoDaño() {
        return id_TipoDaño;
    }

    public String getNombreDaño() {
        return nombreDaño;
    }

    public void setId_TipoDaño(int id_TipoDaño) {
        this.id_TipoDaño = id_TipoDaño;
    }

    public void setNombreDaño(String nombreDaño) {
        this.nombreDaño = nombreDaño;
    }
    
    @Override
    public String toString() {
        return this.nombreDaño;
    }
}
