package kata.caas;

import kata.caas.business.Product;
import kata.caas.service.cart.CartHelper;
import kata.caas.service.cart.ICartManager;
import kata.caas.service.format.IFormat;
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

    @Inject
    private IFormat format;

    @Before
    public void clean() {
        cartManager.clearCart();
    }

    @Test
    public void testInput1() {
        Product livre = cartHelper.addBookToCart(12.49);
        Product cd = cartHelper.addServiceToCart(14.99);
        Product barre = cartHelper.addFoodToCart(0.85);

        Assert.assertTrue(12.49 == format.formatAmountTTC(livre));
        Assert.assertTrue(16.49 == format.formatAmountTTC(cd));
        Assert.assertTrue(0.85 == format.formatAmountTTC(barre));
        Assert.assertTrue(1.50 == format.formatTotalTax());
        Assert.assertTrue(29.83 == format.formatTotalAmount());
    }

    @Test
    public void testInput2() {
        Product boite = cartHelper.addFoodImportedToCart(10.0);
        Product parfum = cartHelper.addServiceImportedToCart(47.5);

        Assert.assertTrue(10.50 == format.formatAmountTTC(boite));
        Assert.assertTrue(54.65 == format.formatAmountTTC(parfum));
        Assert.assertTrue(7.65 == format.formatTotalTax());
        Assert.assertTrue(65.15 == format.formatTotalAmount());
    }

    @Test
    public void testInput3() {
        Product parfumImporte = cartHelper.addServiceImportedToCart(27.99);
        Product parfum = cartHelper.addServiceToCart(18.99);
        Product pillule = cartHelper.addMedicalToCart(9.75);
        Product chocolat = cartHelper.addFoodImportedToCart(11.25);

        Assert.assertTrue(32.19 == format.formatAmountTTC(parfumImporte));
        Assert.assertTrue(20.89 == format.formatAmountTTC(parfum));
        Assert.assertTrue(9.75 == format.formatAmountTTC(pillule));
        Assert.assertTrue(11.85 == format.formatAmountTTC(chocolat));
        Assert.assertTrue(6.70 == format.formatTotalTax());
        Assert.assertTrue(74.68 == format.formatTotalAmount());
    }
}
