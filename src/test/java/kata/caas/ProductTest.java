package kata.caas;

import kata.caas.business.Product;
import kata.caas.service.cart.CartHelper;
import kata.caas.service.cart.ICartManager;
import kata.caas.util.WeldJUnit4Runner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Created by michael on 13/06/17.
 */
@RunWith(WeldJUnit4Runner.class)
public class ProductTest {

    @Inject
    private CartHelper cartHelper;

    @Inject
    private ICartManager cartManager;

    @Before
    public void clean() {
        cartManager.clearCart();
    }

    @Test
    public void testInput1() {
        Product livre = cartHelper.addBookToCart(12.49);
        Product cd = cartHelper.addServiceToCart(14.99);
        Product barre = cartHelper.addFoodToCart(0.85);

        Assert.assertTrue(12.49 == cartManager.formatAmountTTC(livre));
        Assert.assertTrue(16.49 == cartManager.formatAmountTTC(cd));
        Assert.assertTrue(0.85 == cartManager.formatAmountTTC(barre));
        Assert.assertTrue(1.50 == cartManager.formatTotalTax());
        Assert.assertTrue(29.83 == cartManager.formatTotalAmount());
    }

    @Test
    public void testInput2() {
        Product boite = cartHelper.addFoodImportedToCart(10.0);
        Product parfum = cartHelper.addServiceImportedToCart(47.5);

        Assert.assertTrue(10.50 == cartManager.formatAmountTTC(boite));
        Assert.assertTrue(54.65 == cartManager.formatAmountTTC(parfum));
        Assert.assertTrue(7.65 == cartManager.formatTotalTax());
        Assert.assertTrue(65.15 == cartManager.formatTotalAmount());
    }

    @Test
    public void testInput3() {
        Product parfumImporte = cartHelper.addServiceImportedToCart(27.99);
        Product parfum = cartHelper.addServiceToCart(18.99);
        Product pillule = cartHelper.addMedicalToCart(9.75);
        Product chocolat = cartHelper.addFoodImportedToCart(11.25);

        Assert.assertTrue(32.19 == cartManager.formatAmountTTC(parfumImporte));
        Assert.assertTrue(20.89 == cartManager.formatAmountTTC(parfum));
        Assert.assertTrue(9.75 == cartManager.formatAmountTTC(pillule));
        Assert.assertTrue(11.85 == cartManager.formatAmountTTC(chocolat));
        Assert.assertTrue(6.70 == cartManager.formatTotalTax());
        Assert.assertTrue(74.68 == cartManager.formatTotalAmount());
    }
}
