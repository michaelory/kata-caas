package kata.caas;

import kata.caas.business.Product;
import kata.caas.service.bill.BillManager;
import kata.caas.service.bill.IBillManager;
import kata.caas.service.cart.CartManager;
import kata.caas.service.cart.ICartManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by ORY099M on 19/06/2017.
 */
public class CartManagerTest {

    private ICartManager cartManager;

    private IBillManager billManager;

    @Before
    public void clean() {
        cartManager = new CartManager();
        billManager = new BillManager();
    }

    @Test
    public void testInput1() {
        Product livre = cartManager.addBookToCart(12.49);
        Product cd = cartManager.addServiceToCart(14.99);
        Product barre = cartManager.addFoodToCart(0.85);

        Assert.assertTrue(12.49 == livre.getAmountTTC());
        Assert.assertTrue(16.49 == cd.getAmountTTC());
        Assert.assertTrue(0.85 == barre.getAmountTTC());
        Assert.assertTrue(1.50 == cartManager.getTotalTax());
        Assert.assertTrue(29.83 == cartManager.getTotalAmount());
    }

    @Test
    public void testInput2() {
        Product boite = cartManager.addFoodImportedToCart(10.0);
        Product parfum = cartManager.addServiceImportedToCart(47.5);

        Assert.assertTrue(10.50 == boite.getAmountTTC());
        Assert.assertTrue(54.65 == parfum.getAmountTTC());
        Assert.assertTrue(7.65 == cartManager.getTotalTax());
        Assert.assertTrue(65.15 == cartManager.getTotalAmount());
    }

    @Test
    public void testInput3() {
        Product parfumImporte = cartManager.addServiceImportedToCart(27.99);
        Product parfum = cartManager.addServiceToCart(18.99);
        Product pillule = cartManager.addMedicalToCart(9.75);
        Product chocolat = cartManager.addFoodImportedToCart(11.25);

        Assert.assertTrue(32.19 == parfumImporte.getAmountTTC());
        Assert.assertTrue(20.89 == parfum.getAmountTTC());
        Assert.assertTrue(9.75 == pillule.getAmountTTC());
        Assert.assertTrue(11.85 == chocolat.getAmountTTC());
        Assert.assertTrue(6.70 == cartManager.getTotalTax());
        Assert.assertTrue(74.68 == cartManager.getTotalAmount());
    }

    @Test
    public void testAddProductNFamilly() {
        Assert.assertTrue(cartManager.getCart().isEmpty());
        cartManager.addProduct("F1", 1.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F1", 2.0, Boolean.FALSE, Boolean.FALSE);
        cartManager.addProduct("F2", 4.0, Boolean.FALSE, Boolean.FALSE);
        Assert.assertTrue(cartManager.getCart().get("F1").size() == 2);
        Assert.assertTrue(cartManager.getCart().get("F2").size() == 1);
    }
}
