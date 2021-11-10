package tienda.controllers.impl;

import tienda.config.Paths;
import tienda.controllers.ProductController;
import tienda.models.Producto;
import tienda.models.impl.CategoriaTrabajo;
import tienda.models.impl.FamiliaCelulares;
import tienda.models.impl.GamaAltaFactory;
import tienda.models.impl.GamaBajaFactory;
import tienda.models.interfaces.IProductoFactory;
import tienda.repositories.ProductoRepositorio;
import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;

import com.google.common.base.StandardSystemProperty;

//import org.bson.types.ObjectId;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;


public class ProductControllerImpl implements ProductController {
    private static final String ID = "id";

    private ProductoRepositorio productRepository;

    public ProductControllerImpl(ProductoRepositorio postRepository) {
        this.productRepository = postRepository;
    }

    @Override
    public void create(Context context) {

        Producto Product = context.bodyAsClass(Producto.class);
        System.out.println("Producto: " + Product);

        //if (Product.getId() != null) {
        //    throw new BadRequestResponse(String.format("Unable to create a new post with existing id: %s", Product));
        //}

        productRepository.create(Product);
        context.status(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.name(), Paths.formatPostLocation(Product.getId().toString()));

    }

    @Override
    public void delete(Context context) {
        productRepository.delete(context.pathParam(ID));

    }

    @Override
    public void find(Context context) {
        String id = context.pathParam(ID);
        Producto Product = productRepository.find(id);

        if (Product == null) {
            throw new NotFoundResponse(String.format("A Product with id '%s' is not found", id));
        }

        context.json(Product);

    }

    @Override
    public void findAll(Context context) {
        context.json(productRepository.findAll());
    }

    @Override
    public void update(Context context) {
        Producto product = context.bodyAsClass(Producto.class);
        String id = context.pathParam(ID);

        if (product.getId() != null && !product.getId().toString().equals(id)) {
            throw new BadRequestResponse("Id update is not allowed");
        }

        productRepository.update(product, id);

    }

    @Override
    public void loadProducts(Context context) {

        IProductoFactory factoryProduct = new GamaAltaFactory();
        String lineaA = factoryProduct.getLineaProducto().getLinea();
        String mantenimientoA = factoryProduct.getMantenimiento().getPeriodo();

        factoryProduct = new GamaBajaFactory();
        String lineaB = factoryProduct.getLineaProducto().getLinea();
        String mantenimientoB = factoryProduct.getMantenimiento().getPeriodo();

        Producto pr1 = new Producto("P01010016","Samsung S10", 2500.00, lineaA, mantenimientoA);

        pr1.setFamilia( new FamiliaCelulares( new CategoriaTrabajo()) );
        System.out.println( pr1.getFamilia().getNombre() );
        System.out.println( pr1.getFamilia().getEspecificaciones() );
                
        productRepository.create(pr1);
        Producto pr2 = new Producto("P01010017","Samsung A10", 1000.00, lineaB, mantenimientoB);
        productRepository.create(pr2);
        //Producto pr3 = new Producto("P01010018","Samsung S20", 3000.00, lineaA, mantenimientoA);
        //productRepository.create(pr3);


    }
    
}