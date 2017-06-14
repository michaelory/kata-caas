package kata.caas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by michael on 13/06/17.
 */
public class ProductManager {

    private static final Logger LOG = LoggerFactory.getLogger(ProductManager.class);

    private static final double TAUX_10 = 0.10;
    private static final double TAUX_05 = 0.05;
    private static final String FORMAT_PATTERN = "0.00";

    private Function<Double, Double> ROUND_CHOICE = (tauxTax) -> tauxTax == TAUX_10 ? TAUX_05 : TAUX_10;
    private BiFunction<Double, Double, Double> APPLY_ROUND = (amountHT, tauxTax) -> Math.round(amountHT * tauxTax / ROUND_CHOICE.apply(tauxTax)) * ROUND_CHOICE.apply(tauxTax);

    private List<Product> products = new ArrayList<>();

    private Double totalTax = new Double(0);
    private Double totalAmount = new Double(0);

    private DecimalFormat df;

    public ProductManager() {
        df = new DecimalFormat(FORMAT_PATTERN);
        df.setRoundingMode(RoundingMode.HALF_UP);
    }

    public Product addProduct(String label, Double amountHT) {
        return addProduct(label, amountHT, Boolean.FALSE, Boolean.FALSE);
    }

    public Product addProduct(String label, Double amountHT, Boolean tvaApplied) {
        return addProduct(label, amountHT, tvaApplied, Boolean.FALSE);
    }

    public Product addProduct(String label, Double amountHT, Boolean tvaApplied, Boolean imported) {
        Product product = new Product();
        product.setLabel(label);
        product.setAmountHT(amountHT);
        product.setTvaApplied(tvaApplied);
        product.setImported(imported);

        calculateAmounts(product);
        products.add(product);
        return product;
    }

    public String format(Double amountTTC) {
        return df.format(amountTTC);
    }

    public void print() {
        for (Product p : products) {

        }
    }

    private void calculateAmounts(Product product) {
        Double ttc = product.getAmountHT();
        if (product.isTvaApplied()) {
            double amountTax = APPLY_ROUND.apply(product.getAmountHT(), TAUX_10);
            LOG.debug("{} APPLY TVA ht : {}, taux tax : {}, amountTax : {}", new Object[]{product.getLabel(), product.getAmountHT(), TAUX_10, amountTax});
            totalTax = totalTax + amountTax;
            ttc = ttc + amountTax;
        }

        if (product.isImported()) {
            double amountTax = APPLY_ROUND.apply(product.getAmountHT(), TAUX_05);
            LOG.debug("{} APPLY IMPORTED ht : {}, taux tax : {}, amountTax : {}", new Object[]{product.getLabel(), product.getAmountHT(), TAUX_05, amountTax});
            totalTax = totalTax + amountTax;
            ttc = ttc + amountTax;
        }
        LOG.debug("{} TTC : {}", new Object[]{product.getLabel(), ttc});

        totalAmount = totalAmount + ttc;
        product.setAmountTTC(ttc);
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}
