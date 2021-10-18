package tienda;

public class BancoMetodoPago {

    private String bankId;
    private Double comision;

    public void BankPayOrder(Pedido order){
        
        comision = order.getMontoTotal() * 0.15;
        /* Doing Blok Chain Validation */
        System.out.println("Procesando el pago con Banco "+getBankId()+" | total: "+order.getMontoTotal() + " comision: " +comision);
    }

    public String getBankId() {
        if(this.bankId == null){
            setBankId("B90000000001");
        }
        return bankId;
    }

    public void setBankId(String bankId){
        this.bankId = bankId;
    }
}