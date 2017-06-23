package kata.caas.service.cart;

import kata.caas.business.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by ORY099M on 15/06/2017.
 */
public interface ICartManager {

    Product addProduct(String label, Double amountHT, Boolean tvaApplied, Boolean imported);

    Double getTotalTax();

    Double getTotalAmount();

    Map<String, List<Product>> getCart();

    void clearCart();

    default Product addBookToCart(Double amountHT) {
        return addProduct("Book", amountHT, Boolean.FALSE, Boolean.FALSE);
    }

    default Product addBookImportedToCart(Double amountHT) {
        return addProduct("Book Imported", amountHT, Boolean.FALSE, Boolean.TRUE);
    }

    default Product addFoodToCart(Double amountHT) {
        return addProduct("Food", amountHT, Boolean.FALSE, Boolean.FALSE);
    }

    default Product addFoodImportedToCart(Double amountHT) {
        return addProduct("Food Imported", amountHT, Boolean.FALSE, Boolean.TRUE);
    }

    default Product addMedicalToCart(Double amountHT) {
        return addProduct("Medical", amountHT, Boolean.FALSE, Boolean.FALSE);
    }

    default Product addMedicalImportedToCart(Double amountHT) {
        return addProduct("Medical Imported", amountHT, Boolean.FALSE, Boolean.TRUE);
    }

    default Product addServiceToCart(Double amountHT) {
        return addProduct("Service", amountHT, Boolean.TRUE, Boolean.FALSE);
    }

    default Product addServiceImportedToCart(Double amountHT) {
        return addProduct("Service Imported", amountHT, Boolean.TRUE, Boolean.TRUE);
    }
}
