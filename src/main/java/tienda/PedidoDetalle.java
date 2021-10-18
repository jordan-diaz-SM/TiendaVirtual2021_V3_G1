package tienda;

public class PedidoDetalle {
    
    private String idProduct;
    private Integer cantidad;
    private Double precio;
    private String tipo;

    public PedidoDetalle()  {

    }
    
    public PedidoDetalle(String idProduct, Integer cantidad, Double precio, String tipo)  {
        this.idProduct = idProduct;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipo = tipo;
    }

    public String getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
