package kata.caas.business;

import java.util.List;

/**
 * Created by ORY099M on 19/06/2017.
 */
public class Cart {

    private int quantity;

    private List<Product> products;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
