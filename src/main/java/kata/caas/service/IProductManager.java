package kata.caas.service;

import kata.caas.business.Product;

/**
 * Created by ORY099M on 15/06/2017.
 */
public interface IProductManager {

    Product addProduct(String label, Double amountHT, Boolean tvaApplied, Boolean imported);

    Double getTotalTax();

    Double getTotalAmount();

    void clearCart();

}
