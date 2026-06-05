package modelo;

public class TipoDano {
    private int id_TipoDano;
    private String nombreDano;

    public TipoDano(){
    }
    
    public TipoDano(int idTipoDano, String nombreDano){
        this.id_TipoDano = idTipoDano ;
        this.nombreDano = nombreDano ;
    }

    public int getId_TipoDano() {
        return id_TipoDano;
    }

    public String getNombreDano() {
        return nombreDano;
    }

    public void setId_TipoDano(int id_TipoDano) {
        this.id_TipoDano = id_TipoDano;
    }

    public void setNombreDaño(String nombreDano) {
        this.nombreDano = nombreDano;
    }
    
    @Override
    public String toString() {
        return this.nombreDano;
    }
}
