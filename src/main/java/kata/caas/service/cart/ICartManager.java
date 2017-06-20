package kata.caas.service.cart;

import kata.caas.business.Cart;
import kata.caas.business.Product;
import kata.caas.service.format.IFormat;

/**
 * Created by ORY099M on 15/06/2017.
 */
public interface ICartManager {

    Product addProduct(String label, Double amountHT, Boolean tvaApplied, Boolean imported);

    void clearCart();

    Cart getCart();

    IFormat getFormat();

}
