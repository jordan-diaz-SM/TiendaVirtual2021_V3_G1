package tienda;

public class Cliente {
    private String numeroDocumento;
    
    public Cliente() {}

    public Cliente(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
}
