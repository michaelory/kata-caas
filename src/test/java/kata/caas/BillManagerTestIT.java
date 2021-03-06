package kata.caas;

import kata.caas.service.bill.BillManager;
import kata.caas.service.bill.IBillManager;
import kata.caas.service.cart.CartManager;
import kata.caas.service.cart.ICartManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by michael on 13/06/17.
 */
public class BillManagerTestIT {

    private ICartManager cartManager;

    private IBillManager billManager;

    @Before
    public void clean() {
        cartManager = new CartManager();
        billManager = new BillManager();
    }

    @Test
    public void testPrintBillInput1() {
        cartManager.addBookToCart(12.49);
        cartManager.addServiceToCart(14.99);
        cartManager.addFoodToCart(0.85);
        try {
            billManager.generateBill("monfichier.txt", cartManager);
            billManager.printBill();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }
}
