package kata.caas;

import kata.caas.business.Cart;
import kata.caas.service.bill.FileException;
import kata.caas.service.cart.ICartManager;
import kata.caas.util.WeldJUnit4Runner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Created by ORY099M on 19/06/2017.
 */
@RunWith(WeldJUnit4Runner.class)
public class CartManagerTest {

    @Inject
    private ICartManager cartManager;

    @Before
    public void clean() {
        cartManager.clearCart();
    }

    @Test
    public void testAddProduct() {
        Assert.assertTrue(cartManager.getCart().isEmpty());
        cartManager.addProduct("F1", 1.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F1", 2.0, Boolean.FALSE, Boolean.FALSE);
        Cart f1 = cartManager.getCart().get("F1");
        Assert.assertTrue(f1.getQuantity() == 2);
    }

    @Test
    public void testAddProduct_nFamilly() {
        Assert.assertTrue(cartManager.getCart().isEmpty());
        cartManager.addProduct("F1", 1.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F1", 2.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F2", 4.0, Boolean.FALSE, Boolean.FALSE);
        Cart f1 = cartManager.getCart().get("F1");
        Assert.assertTrue(f1.getQuantity() == 2);
        Assert.assertTrue(cartManager.getCart().get("F2").getQuantity() == 1);
    }

    @Test
    public void testGenerateBill() {
        Assert.assertTrue(cartManager.getCart().isEmpty());
        cartManager.addProduct("F1", 1.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F1", 2.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F2", 4.0, Boolean.FALSE, Boolean.FALSE);
        try {
            cartManager.generateBill("monfichier.txt");
        } catch (FileException e) {
            Assert.fail(e.getMessage());
        }
    }
}
