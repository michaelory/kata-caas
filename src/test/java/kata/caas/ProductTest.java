package kata.caas;

import kata.caas.business.Product;
import kata.caas.service.ProductFactory;
import kata.caas.service.ProductManager;
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
    private ProductFactory productFactory;

    @Inject
    private ProductManager productManager;

    @Before
    public void clean() {
        productManager.clearCart();
    }

    @Test
    public void testInput1() {
        Product livre = productFactory.addBookProduct(12.49);
        Product cd = productFactory.addServiceProduct(14.99);
        Product barre = productFactory.addFoodProduct(0.85);

        Assert.assertTrue(12.49 == productManager.formatAmountTTC(livre));
        Assert.assertTrue(16.49 == productManager.formatAmountTTC(cd));
        Assert.assertTrue(0.85 == productManager.formatAmountTTC(barre));
        Assert.assertTrue(1.50 == productManager.formatTotalTax());
        Assert.assertTrue(29.83 == productManager.formatTotalAmount());
    }

    @Test
    public void testInput2() {
        Product boite = productFactory.addFoodImportedProduct(10.0);
        Product parfum = productFactory.addServiceImportedProduct(47.5);

        Assert.assertTrue(10.50 == productManager.formatAmountTTC(boite));
        Assert.assertTrue(54.65 == productManager.formatAmountTTC(parfum));
        Assert.assertTrue(7.65 == productManager.formatTotalTax());
        Assert.assertTrue(65.15 == productManager.formatTotalAmount());
    }

    @Test
    public void testInput3() {
        Product parfumImporte = productFactory.addServiceImportedProduct(27.99);
        Product parfum = productFactory.addServiceProduct(18.99);
        Product pillule = productFactory.addMedicalProduct(9.75);
        Product chocolat = productFactory.addFoodImportedProduct(11.25);

        Assert.assertTrue(32.19 == productManager.formatAmountTTC(parfumImporte));
        Assert.assertTrue(20.89 == productManager.formatAmountTTC(parfum));
        Assert.assertTrue(9.75 == productManager.formatAmountTTC(pillule));
        Assert.assertTrue(11.85 == productManager.formatAmountTTC(chocolat));
        Assert.assertTrue(6.70 == productManager.formatTotalTax());
        Assert.assertTrue(74.68 == productManager.formatTotalAmount());
    }
}
