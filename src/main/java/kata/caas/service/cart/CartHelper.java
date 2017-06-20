package kata.caas.service.cart;

import kata.caas.business.Product;

import javax.inject.Inject;

/**
 * Created by ORY099M on 15/06/2017.
 */
public class CartHelper {

    @Inject
    private ICartManager cartManager;

    public Product addBookToCart(Double amountHT) {
        return cartManager.addProduct("Book", amountHT, Boolean.FALSE, Boolean.FALSE);
    }

    public Product addBookImportedToCart(Double amountHT) {
        return cartManager.addProduct("Book Imported", amountHT, Boolean.FALSE, Boolean.TRUE);
    }

    public Product addFoodToCart(Double amountHT) {
        return cartManager.addProduct("Food", amountHT, Boolean.FALSE, Boolean.FALSE);
    }

    public Product addFoodImportedToCart(Double amountHT) {
        return cartManager.addProduct("Food Imported", amountHT, Boolean.FALSE, Boolean.TRUE);
    }

    public Product addMedicalToCart(Double amountHT) {
        return cartManager.addProduct("Medical", amountHT, Boolean.FALSE, Boolean.FALSE);
    }

    public Product addMedicalImportedToCart(Double amountHT) {
        return cartManager.addProduct("Medical Imported", amountHT, Boolean.FALSE, Boolean.TRUE);
    }

    public Product addServiceToCart(Double amountHT) {
        return cartManager.addProduct("Service", amountHT, Boolean.TRUE, Boolean.FALSE);
    }

    public Product addServiceImportedToCart(Double amountHT) {
        return cartManager.addProduct("Service Imported", amountHT, Boolean.TRUE, Boolean.TRUE);
    }
}
