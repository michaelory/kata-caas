package kata.caas.service;

import kata.caas.business.Product;
import kata.caas.service.format.Format;
import kata.caas.service.format.IProductFormat;
import kata.caas.util.Log;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by michael on 13/06/17.
 */
@Singleton
public class ProductManager implements IProductManager, IProductFormat {

    @Inject
    @Log
    private Logger LOG;

    private static final double RATE_10 = 0.10;
    private static final double RATE_05 = 0.05;

    private static final Function<Double, Double> ROUND_CHOICE = (tauxTax) -> tauxTax == RATE_10 ? RATE_05 : RATE_10;
    private static final BiFunction<Double, Double, Double> APPLY_TAX = (amountHT, tauxTax) -> Math.round(amountHT * tauxTax / ROUND_CHOICE.apply(tauxTax)) * ROUND_CHOICE.apply(tauxTax);

    private Double totalTax;
    private Double totalAmount;

    @PostConstruct
    @Override
    public void clearCart() {
        totalTax = new Double(0);
        totalAmount = new Double(0);
    }

    @Override
    public Product addProduct(String label, Double amountHT, Boolean tvaApplied, Boolean imported) {
        Product product = new Product();
        product.setLabel(label);
        product.setAmountHT(amountHT);
        product.setTvaApplied(tvaApplied);
        product.setImported(imported);

        calculateAmounts(product);
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

    @Override
    @Format
    public Double formatAmountTTC(Product product) {
        return product.getAmountTTC();
    }

    @Override
    @Format
    public Double formatTotalTax() {
        return totalTax;
    }

    @Override
    @Format
    public Double formatTotalAmount() {
        return totalAmount;
    }

    @Override
    public Double getTotalTax() {
        return totalTax;
    }

    @Override
    public Double getTotalAmount() {
        return totalAmount;
    }
}