package kata.caas;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michael on 13/06/17.
 */
public class ProductManager {

    public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
    public static final String TOTAL_TAX = "TOTAL_TAX";

    private List<Product> products = new ArrayList<>();

    private Double totalTax = new Double(0);
    private Double totalAmount = new Double(0);

    public void addProduct(Product product) {
        products.add(product);
    }

    public Map<String, String> calculateAmount() {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);

        Map<String, String> amounts = new HashMap<>();
        for (Product p : products) {
            Double ttc = calculateTTC(p);
            totalAmount = totalAmount + ttc;
            amounts.put(p.getLabel(), df.format(ttc.doubleValue()));
        }
        amounts.put(TOTAL_TAX, df.format(totalTax.doubleValue()));
        amounts.put(TOTAL_AMOUNT, df.format(totalAmount.doubleValue()));
        return amounts;
    }

    private Double calculateTTC(Product product) {
        Double ttc;
        if (product.isTvaApplied()) {
            ttc = product.getAmountHT() * 1.10;
            totalTax = totalTax + product.getAmountHT() * 0.10;
        } else
            ttc = product.getAmountHT();

        if (product.isImported()) {
            ttc = product.getAmountHT() * 1.05;
            totalTax = totalTax + product.getAmountHT() * 0.05;
        }

        return ttc;
    }
}
