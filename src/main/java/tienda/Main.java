package tienda;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;

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

    public Object crearPedido(String numero) {

        Pedido pedido = new Pedido(numero);
        pedidos.insertOne(pedido);
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
        System.out.println("Pedido asignado: " + order.getNumero());
        pedidos.findOneAndReplace(new Document("numero", order.getNumero()), order, new FindOneAndReplaceOptions());

    }

    public static String imprimeDatosCliente(String numeroDocumento, String nombreCliente, String direccionCliente)  {

        StringBuffer salida = new StringBuffer();
        salida.append("Cliente Id: " + numeroDocumento);
        salida.append("\nNombres: " + nombreCliente);
        salida.append("\nDireccion: " + direccionCliente);
        return salida.toString();
    }

    public static void main(String args[])  {

        Main m = new Main();
        String numeroDocumento = "7090901";
        m.crearCliente( numeroDocumento );
        Pedido order1 = (Pedido)m.crearPedido("7001");

        
        m.asignaClientePedido( order1, numeroDocumento);

        System.out.println( imprimeDatosCliente("7090901", "Gianluca Lapadula", "Calle Roma 720"));
    }
}
