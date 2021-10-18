package tienda;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.types.ObjectId;

public class Main {

    private final MongoClient mongoClient;
    private MongoCollection<Pedido> pedidos;
    private MongoCollection<Cliente> clientes;

    public Main()   {
        this.mongoClient = new MongoClient( "localhost" , 27017 );
        this.getDatabase();
    }

    public MongoDatabase getDatabase() {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(
                        PojoCodecProvider.builder().automatic(true).build()
                )
        );
        MongoDatabase database = this.mongoClient.getDatabase("SRP").withCodecRegistry(pojoCodecRegistry);

        this.pedidos = database.getCollection("pedidos", Pedido.class);
        this.clientes = database.getCollection("clientes", Cliente.class);
        
        return database;
    }

    public Object crearPedido() {

        Pedido pedido = new Pedido();
        pedido.setId((new ObjectId()).toString());
        return pedido;
    }

    public Object crearCliente(String numeroDocumento) {

        Cliente cliente = new Cliente(numeroDocumento);
        clientes.insertOne(cliente);
        return cliente;
    }

    public void asignaClientePedido(Pedido order, String cliente) {
       
        System.out.println("Cliente que quiere el pedido: " + cliente);
        order.setCliente(cliente);
        System.out.println("Pedido asignado: " + order.getId());
        pedidos.findOneAndReplace(new Document("numero", order.getId()), order, new FindOneAndReplaceOptions());

    }

    public static String imprimeDatosCliente(String numeroDocumento, String nombreCliente, String direccionCliente)  {

        StringBuffer salida = new StringBuffer();
        salida.append("Cliente Id: " + numeroDocumento);
        salida.append("\nNombres: " + nombreCliente);
        salida.append("\nDireccion: " + direccionCliente);
        return salida.toString();
    }

    public Double calcularMontoPedido( Pedido order )  {

        Double montoTotal = 0.0;

        for (PedidoDetalle item : order.getDetallePedido()) {
            if (item.getTipo().equals("Internet")) {

                Double customPrice = item.getPrecio() * item.getCantidad() * 0.85;
                montoTotal += customPrice;
            }
            else if (item.getTipo().equals("Promocion")) {

                Double customPrice = item.getPrecio() * item.getCantidad();
                customPrice -= 20.0;
                if (customPrice < 0 ) {
                    customPrice = 0.0;
                }
                montoTotal += customPrice;
            } 
        }    

        return montoTotal;
    }

    public static void main(String args[])  {

        Main m = new Main();

        Pedido order1 = (Pedido)m.crearPedido();
        List<PedidoDetalle> items = new ArrayList<PedidoDetalle>();
        PedidoDetalle oi1 = new PedidoDetalle( "P01010034", 1, 400.90, "Internet");
        PedidoDetalle oi2 = new PedidoDetalle( "P01010025", 1, 600.90, "Promocion");
        items.add(oi1);
        items.add(oi2);
        order1.setDetallePedido(items);
        order1.setMontoTotal(m.calcularMontoPedido(order1));
        System.out.println("Monto pedido: " + order1.getMontoTotal() );
        m.pedidos.insertOne(order1);

        String numeroDocumento = "70909012";
        m.crearCliente( numeroDocumento );
        m.asignaClientePedido( order1, numeroDocumento);

        System.out.println( imprimeDatosCliente("70909012", "Gianluca Lapadula", "Calle Roma 720"));

        order1.pagarBanco(new BancoMetodoPago());
    }
}
