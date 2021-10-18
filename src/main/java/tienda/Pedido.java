package tienda;

import java.util.List;

public class Pedido {
    private String id;
    private String cliente;
    private Double montoTotal;

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    private List<PedidoDetalle> detallePedido;

    public Pedido() {}

    public Pedido(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCliente(String cliente)  {
        this.cliente = cliente;
    }

    public String getCliente()  {
        return cliente;
    }

    public List<PedidoDetalle> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<PedidoDetalle> detallePedido) {
        this.detallePedido = detallePedido;
    }

    public void pagarBlockChain(BlockChainMetodoPago paymentMethod)   {

        System.out.println("Paying order blockchain"+getId());
        paymentMethod.walletPayOrder(this);
    }

    public void pagarBanco(BancoMetodoPago paymentMethod)   {

        System.out.println("Paying order Bank"+getId());
        paymentMethod.BankPayOrder(this);
    }
}