package kata.caas.service.cart;

import kata.caas.business.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by michael on 13/06/17.
 */
public class CartManager implements ICartManager {

    private Logger LOG = LoggerFactory.getLogger(CartManager.class);

    private Map<String, List<Product>> cartMap;

    private Double totalTax;

    private Double totalAmount;

    private DecimalFormat decimalFormat;

    private static final double RATE_10 = 0.10;
    private static final double RATE_05 = 0.05;

    private static final Function<Double, Double> ROUND_CHOICE = (tauxTax) -> tauxTax == RATE_10 ? RATE_05 : RATE_10;
    private static final BiFunction<Double, Double, Double> APPLY_TAX = (amountHT, tauxTax) -> Math.round(amountHT * tauxTax / ROUND_CHOICE.apply(tauxTax)) * ROUND_CHOICE.apply(tauxTax);

    public CartManager() {
        decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        clearCart();
    }

    @Override
    public void clearCart() {
        totalTax = new Double(0);
        totalAmount = new Double(0);
        if (cartMap == null)
            cartMap = new HashMap();
        else
            cartMap.clear();
    }

    @Override
    public Product addProduct(String label, Double amountHT, Boolean tvaApplied, Boolean imported) {
        Product product = new Product();
        product.setLabel(label);
        product.setAmountHT(amountHT);
        product.setTvaApplied(tvaApplied);
        product.setImported(imported);
        return addProduct(product);
    }

    @Override
    public Double getTotalTax() {
        return totalTax;
    }

    @Override
    public Double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public Map<String, List<Product>> getCart() {
        return cartMap;
    }

    private Product addProduct(Product product) {
        calculateAmounts(product);
        format(product);
        cartMap.compute(product.getLabel(), (key, p) -> {
            if (p == null)
                p = new ArrayList<>();
            p.add(product);
            return p;
        });
        return product;
    }

    private void calculateAmounts(Product product) {
        Double ttc = product.getAmountHT();
        if (product.isTvaApplied()) {
            double amountTax = APPLY_TAX.apply(product.getAmountHT(), RATE_10);
            LOG.debug("{} APPLY TVA ht : {}, taux tax : {}, amountTax : {}", new Object[]{product.getLabel(), product.getAmountHT(), RATE_10, amountTax});
            totalTax = totalTax + amountTax;
            ttc = ttc + amountTax;
        }

        if (product.isImported()) {
            double amountTax = APPLY_TAX.apply(product.getAmountHT(), RATE_05);
            LOG.debug("{} APPLY IMPORTED ht : {}, taux tax : {}, amountTax : {}", new Object[]{product.getLabel(), product.getAmountHT(), RATE_05, amountTax});
            totalTax = totalTax + amountTax;
            ttc = ttc + amountTax;
        }
        LOG.debug("{} TTC : {}", new Object[]{product.getLabel(), ttc});

        totalAmount = totalAmount + ttc;
        product.setAmountTTC(ttc);
    }

    private void format(Product product) {
        product.setAmountTTC(Double.valueOf(decimalFormat.format(product.getAmountTTC()).replace(",", ".")));
        totalTax = Double.valueOf(decimalFormat.format(totalTax).replace(",", "."));
        totalAmount = Double.valueOf(decimalFormat.format(totalAmount).replace(",", "."));
    }
}