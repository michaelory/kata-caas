package kata.caas.service.bill;

import kata.caas.business.Cart;
import kata.caas.business.Product;
import kata.caas.service.cart.CartManager;
import org.jboss.weld.environment.se.WeldContainer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by ORY099M on 19/06/2017.
 */
@Singleton
@BillImpl
public class BillManager implements IBillManager {

    @Inject
    private WeldContainer container;

    private CartManager cartManager;

    private String path;

    @PostConstruct
    public void init() {
        cartManager = container.instance().select(CartManager.class).get();
    }

    @Override
    public void generateBill(String path) throws FileException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), Charset.forName("US-ASCII"))) {
            for (Map.Entry<String, Cart> cartEntry : cartManager.getCart().entrySet()) {
                writer.write(String.valueOf(cartEntry.getValue().getQuantity()));
                writer.write(" ");
                writer.write(cartEntry.getKey());
                writer.newLine();
                for (Product product : cartEntry.getValue().getProducts()) {
                    writer.write(" ");
                    writer.write(product.getLabel());
                    writer.write(" ");
                    writer.write(String.valueOf(product.getAmountTTC()));
                    writer.newLine();
                }
            }
            writer.newLine();
            writer.write("Montant des taxes :");
            writer.write(String.valueOf(cartManager.getTotalTax()));
            writer.newLine();
            writer.write("Total :");
            writer.write(String.valueOf(cartManager.getTotalAmount()));
        } catch (IOException ioe) {
            throw new FileException(ioe.getMessage(), ioe);
        }

    }

    @Override
    public void printBill() throws FileException {

    }

   /* @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        return 0;
    }*/
}
