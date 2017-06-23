package kata.caas.service.bill;

import kata.caas.business.Product;
import kata.caas.service.cart.ICartManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by ORY099M on 19/06/2017.
 */
public class BillManager implements IBillManager {

    private Logger LOG = LoggerFactory.getLogger(BillManager.class);

    private String path;

    @Override
    public void generateBill(String path, ICartManager cartManager) throws FileException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), Charset.forName("US-ASCII"))) {
            this.path = path;
            for (Map.Entry<String, List<Product>> cartEntry : cartManager.getCart().entrySet()) {
                writer.write(String.valueOf(cartEntry.getValue().size()));
                writer.write(" ");
                writer.write(cartEntry.getKey());
                writer.newLine();
                for (Product product : cartEntry.getValue()) {
                    writer.write(" ");
                    writer.write(product.getLabel());
                    writer.write(" ");
                    writer.write(String.valueOf(product.getAmountTTC()));
                    writer.newLine();
                }
            }
            writer.newLine();
            writer.write("Montant des taxes : ");
            writer.write(String.valueOf(cartManager.getTotalTax()));
            writer.newLine();
            writer.write("Total : ");
            writer.write(String.valueOf(cartManager.getTotalAmount()));
        } catch (IOException ioe) {
            throw new FileException(ioe.getMessage(), ioe);
        }
    }

    @Override
    public void printBill() throws FileException {
        try {
            Desktop.getDesktop().print(new File(path));
        } catch (IOException ioe) {
            throw new FileException(ioe.getMessage(), ioe);
        }
    }
}
