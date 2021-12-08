package tienda.models.impl;

import tienda.models.BlockChainMetodoPago;
import tienda.models.Pedido;
import tienda.models.interfaces.IEstadoPedido;

public class PedidoPorEntregar implements IEstadoPedido {

    private String id;
    
    public PedidoPorEntregar()  {}

    @Override
    public void procesar(Pedido pedido) {

        BlockChainMetodoPago paymentMethod = new BlockChainMetodoPago();
        pedido.pagar(paymentMethod);
        System.out.println("Pedido Pagado.");
        
        System.out.println("Entregando pedido: " + pedido.getId());
        System.out.println("Pedido Entregado.");

        pedido.setEstadoPedido( new PedidoFinalizado() );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
