package modelo;

    

public class TipoInfra {
    
    private int id_infra;
    private String tipoInfra;

    public TipoInfra() {
    }

    public TipoInfra(int id_infra, String tipoInfra) {
        this.id_infra = id_infra;
        this.tipoInfra = tipoInfra;
    }

    public int getId_infra() {
        return id_infra;
    }

    public void setId_infra(int id_infra) {
        this.id_infra = id_infra;
    }

    public String getTipoInfra() {
        return tipoInfra;
    }

    public void setTipoInfra(String tipoInfra) {
        this.tipoInfra = tipoInfra;
    }

    // 5. Pro-tip para la Vista (Interfaz Gráfica)
    @Override
    public String toString() {
        return this.tipoInfra;
    }
}

