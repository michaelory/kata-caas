package kata.caas.service.cart;

import kata.caas.business.Cart;
import kata.caas.business.Product;
import kata.caas.service.bill.IBillManager;
import kata.caas.service.format.IProductFormat;

import java.util.Map;

/**
 * Created by ORY099M on 15/06/2017.
 */
public interface ICartManager extends IProductFormat, IBillManager{

    Product addProduct(String label, Double amountHT, Boolean tvaApplied, Boolean imported);

    Double getTotalTax();

    Double getTotalAmount();

    void clearCart();

    Map<String, Cart> getCart();

}
