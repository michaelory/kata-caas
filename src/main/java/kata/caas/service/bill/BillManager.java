package kata.caas.service.bill;

import kata.caas.business.Cart;
import kata.caas.business.Product;
import kata.caas.business.QuantityOfProduct;

import javax.inject.Inject;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by ORY099M on 19/06/2017.
 */
public class BillManager implements IBillManager {

    @Inject
    private Cart cart;

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
                    writer.write(String.valueOf(cart.formatAmountTTC(product)));
                    writer.newLine();
                }
            }
            writer.newLine();
            writer.write("Montant des taxes : ");
            writer.write(String.valueOf(cart.formatTotalTax()));
            writer.newLine();
            writer.write("Total : ");
            writer.write(String.valueOf(cart.formatTotalAmount()));
        } catch (IOException ioe) {
            throw new FileException(ioe.getMessage(), ioe);
        }
    }

    @Override
    public void printBill() throws FileException {
        Path path = Paths.get(this.path);
        try {
            List<String> contents = Files.readAllLines(path);
            Printable printable = (graphics, pageFormat, pageIndex) -> {
                int x = (int) pageFormat.getImageableX();
                int y = (int) pageFormat.getImageableY();
                //for (String contentLine : contents)
                    graphics.drawString("TEST", x, y);
                return Printable.PAGE_EXISTS;
            };
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(printable);
            boolean doPrint = job.printDialog();
            if (doPrint) {
                try {
                    job.print();
                } catch (PrinterException ioe) {
                    throw new FileException(ioe.getMessage(), ioe);
                }
            }

        } catch (IOException ioe) {
            throw new FileException(ioe.getMessage(), ioe);
        }
    }
}
