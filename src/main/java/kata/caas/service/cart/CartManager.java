package kata.caas.service.cart;

import kata.caas.business.Cart;
import kata.caas.business.Product;
import kata.caas.business.QuantityOfProduct;
import kata.caas.util.Log;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by michael on 13/06/17.
 */
public class CartManager implements ICartManager {

    @Inject
    @Log
    private Logger LOG;

    @Inject
    private Cart cart;

    private static final double RATE_10 = 0.10;
    private static final double RATE_05 = 0.05;

    private static final Function<Double, Double> ROUND_CHOICE = (tauxTax) -> tauxTax == RATE_10 ? RATE_05 : RATE_10;
    private static final BiFunction<Double, Double, Double> APPLY_TAX = (amountHT, tauxTax) -> Math.round(amountHT * tauxTax / ROUND_CHOICE.apply(tauxTax)) * ROUND_CHOICE.apply(tauxTax);

    @PostConstruct
    @Override
    public void clearCart() {
        cart.setTotalTax(new Double(0));
        cart.setTotalAmount(new Double(0));
        if (cart.getCartMap() == null)
            cart.setCartMap(new HashMap());
        else
            cart.getCartMap().clear();
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
        getCart().getCartMap().compute(product.getLabel(), (key, p) -> {
            if (p != null)
                p.setQuantity(p.getQuantity() + 1);
            else {
                p = new QuantityOfProduct();
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
            getCart().setTotalTax(getCart().getTotalTax() + amountTax);
            ttc = ttc + amountTax;
        }

        if (product.isImported()) {
            double amountTax = APPLY_TAX.apply(product.getAmountHT(), RATE_05);
            LOG.debug("{} APPLY IMPORTED ht : {}, taux tax : {}, amountTax : {}", new Object[]{product.getLabel(), product.getAmountHT(), RATE_05, amountTax});
            getCart().setTotalTax(getCart().getTotalTax() + amountTax);
            ttc = ttc + amountTax;
        }
        LOG.debug("{} TTC : {}", new Object[]{product.getLabel(), ttc});

        getCart().setTotalAmount(getCart().getTotalAmount() + ttc);
        product.setAmountTTC(ttc);
    }

    @Override
    public Cart getCart() {
        return cart;
    }
}