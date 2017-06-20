package kata.caas.service.bill;

import kata.caas.business.Cart;
import kata.caas.business.Product;
import kata.caas.business.QuantityOfProduct;
import kata.caas.service.format.IFormat;
import kata.caas.util.Log;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by ORY099M on 19/06/2017.
 */
public class BillManager implements IBillManager {

    @Inject
    @Log
    private Logger LOG;

    @Inject
    private Cart cart;

    @Inject
    private IFormat format;

    private String path;

    @Override
    public void generateBill(String path) throws FileException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), Charset.forName("US-ASCII"))) {
            this.path = path;
            for (Map.Entry<String, QuantityOfProduct> cartEntry : cart.getCartMap().entrySet()) {
                writer.write(String.valueOf(cartEntry.getValue().getQuantity()));
                writer.write(" ");
                writer.write(cartEntry.getKey());
                writer.newLine();
                for (Product product : cartEntry.getValue().getProducts()) {
                    writer.write(" ");
                    writer.write(product.getLabel());
                    writer.write(" ");
                    writer.write(String.valueOf(format.formatAmountTTC(product)));
                    writer.newLine();
                }
            }
            writer.newLine();
            writer.write("Montant des taxes : ");
            writer.write(String.valueOf(format.formatTotalTax()));
            writer.newLine();
            writer.write("Total : ");
            writer.write(String.valueOf(format.formatTotalAmount()));
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
