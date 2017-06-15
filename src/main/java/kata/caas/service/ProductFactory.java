package kata.caas.service;

import kata.caas.business.Product;

import javax.inject.Inject;

/**
 * Created by ORY099M on 15/06/2017.
 */
public class ProductFactory {

    @Inject
    private ICartManager productManager;

    public Product addBookProduct(Double amountHT) {
        return productManager.addProduct("Book", amountHT, Boolean.FALSE, Boolean.FALSE);
    }

    public Product addBookImportedProduct(Double amountHT) {
        return productManager.addProduct("Book Imported", amountHT, Boolean.FALSE, Boolean.TRUE);
    }

    public Product addFoodProduct(Double amountHT) {
        return productManager.addProduct("Food", amountHT, Boolean.FALSE, Boolean.FALSE);
    }

    public Product addFoodImportedProduct(Double amountHT) {
        return productManager.addProduct("Food Imported", amountHT, Boolean.FALSE, Boolean.TRUE);
    }

    public Product addMedicalProduct(Double amountHT) {
        return productManager.addProduct("Medical", amountHT, Boolean.FALSE, Boolean.FALSE);
    }

    public Product addMedicalImportedProduct(Double amountHT) {
        return productManager.addProduct("Medical Imported", amountHT, Boolean.FALSE, Boolean.TRUE);
    }

    public Product addServiceProduct(Double amountHT) {
        return productManager.addProduct("Service", amountHT, Boolean.TRUE, Boolean.FALSE);
    }

    public Product addServiceImportedProduct(Double amountHT) {
        return productManager.addProduct("Service Imported", amountHT, Boolean.TRUE, Boolean.TRUE);
    }
}
