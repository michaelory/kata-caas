package kata.caas.service.format;

import kata.caas.business.Cart;
import kata.caas.business.Product;

import javax.inject.Inject;

/**
 * Created by ORY099M on 20/06/2017.
 */
public class FormatManager implements IFormat {

    @Inject
    private Cart cart;

    @Override
    @Format
    public Double formatAmountTTC(Product product) {
        return product.getAmountTTC();
    }

    @Override
    @Format
    public Double formatTotalTax() {
        return cart.getTotalTax();
    }

    @Override
    @Format
    public Double formatTotalAmount() {
        return cart.getTotalAmount();
    }
}
