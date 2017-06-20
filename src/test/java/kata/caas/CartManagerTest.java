package kata.caas;

import kata.caas.business.QuantityOfProduct;
import kata.caas.service.bill.FileException;
import kata.caas.service.bill.IBillManager;
import kata.caas.service.cart.CartHelper;
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
    private CartHelper cartHelper;

    @Inject
    private ICartManager cartManager;

    @Inject
    private IBillManager billManager;

    @Before
    public void clean() {
        cartManager.clearCart();
    }

    @Test
    public void testAddProduct() {
        Assert.assertTrue(cartManager.getCart().getCartMap().isEmpty());
        cartManager.addProduct("F1", 1.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F1", 2.0, Boolean.FALSE, Boolean.FALSE);
        QuantityOfProduct f1 = cartManager.getCart().getCartMap().get("F1");
        Assert.assertTrue(f1.getQuantity() == 2);
    }

    @Test
    public void testAddProductNFamilly() {
        Assert.assertTrue(cartManager.getCart().getCartMap().isEmpty());
        cartManager.addProduct("F1", 1.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F1", 2.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F2", 4.0, Boolean.FALSE, Boolean.FALSE);
        QuantityOfProduct f1 = cartManager.getCart().getCartMap().get("F1");
        Assert.assertTrue(f1.getQuantity() == 2);
        Assert.assertTrue(cartManager.getCart().getCartMap().get("F2").getQuantity() == 1);
    }

    @Test
    public void testGenerateBill() {
        Assert.assertTrue(cartManager.getCart().getCartMap().isEmpty());
        cartManager.addProduct("F1", 1.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F1", 2.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F2", 4.0, Boolean.FALSE, Boolean.FALSE);
        try {
            billManager.generateBill("monfichier.txt");
        } catch (FileException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testInput1Bill() {
        cartHelper.addBookToCart(12.49);
        cartHelper.addServiceToCart(14.99);
        cartHelper.addFoodToCart(0.85);
        try {
            billManager.generateBill("monfichier.txt");
        } catch (FileException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testInput1BillPrint() {
        cartHelper.addBookToCart(12.49);
        cartHelper.addServiceToCart(14.99);
        cartHelper.addFoodToCart(0.85);
        try {
            billManager.generateBill("monfichier.txt");
            billManager.printBill();
        } catch (FileException e) {
            Assert.fail(e.getMessage());
        }
    }
}
