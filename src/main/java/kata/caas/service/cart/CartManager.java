package kata.caas.service.cart;

import kata.caas.business.Cart;
import kata.caas.business.Product;
import kata.caas.service.bill.BillImpl;
import kata.caas.service.bill.FileException;
import kata.caas.service.bill.IBillManager;
import kata.caas.service.format.Format;
import kata.caas.service.format.IProductFormat;
import kata.caas.util.Log;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by michael on 13/06/17.
 */
@Singleton
public class CartManager implements ICartManager, IProductFormat, IBillManager {

    @Inject
    @Log
    private Logger LOG;

    @Inject
    @BillImpl
    private IBillManager billManager;

    private static final double RATE_10 = 0.10;
    private static final double RATE_05 = 0.05;

    private static final Function<Double, Double> ROUND_CHOICE = (tauxTax) -> tauxTax == RATE_10 ? RATE_05 : RATE_10;
    private static final BiFunction<Double, Double, Double> APPLY_TAX = (amountHT, tauxTax) -> Math.round(amountHT * tauxTax / ROUND_CHOICE.apply(tauxTax)) * ROUND_CHOICE.apply(tauxTax);

    private Double totalTax;
    private Double totalAmount;
    private Map<String, Cart> cartMap;

    @PostConstruct
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

    private Product addProduct(Product product) {
        calculateAmounts(product);
        cartMap.compute(product.getLabel(), (key, p) -> {
            if (p != null)
                p.setQuantity(p.getQuantity() + 1);
            else {
                p = new Cart();
                p.setQuantity(1);
                p.setProducts(new ArrayList<>());
            }
            p.getProducts().add(product);
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

    @Override
    public void generateBill(String path) throws FileException {
        billManager.generateBill(path);
    }

    @Override
    public void printBill() throws FileException {
        billManager.printBill();
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

    @Override
    public Map<String, Cart> getCart() {
        return cartMap;
    }
}