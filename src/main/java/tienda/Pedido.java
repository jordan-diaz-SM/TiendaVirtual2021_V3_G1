package tienda;

public class Pedido {
    //private String id;
    private String numero;
    private String cliente;

    public Pedido() {}

    public Pedido(String numero) {
        this.numero = numero;
    }

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCliente(String cliente)  {
        this.cliente = cliente;
    }

    public String getCliente()  {
        return cliente;
    }

}