package tienda;


public class BlockChainMetodoPago {

    private String walletId;
    private Double comision;

    public void walletPayOrder(Pedido order){
        
        comision = order.getMontoTotal() * 0.05;
        /* Doing Blok Chain Validation */
        System.out.println("Procesando el pago con wallet "+getWalletId()+" | total: "+order.getMontoTotal() + " comision: " +comision);
    }

    public String getWalletId() {
        if(this.walletId == null){
            setWalletId("WI-323232");
        }
        return walletId;
    }

    public void setWalletId(String walletId){
        this.walletId = walletId;
    }
}
