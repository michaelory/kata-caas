package kata.caas;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by michael on 13/06/17.
 */
public class ProductTest {

    @Test
    public void testInput1() {
        ProductManager productManager = new ProductManager();
        Product livre = productManager.addProduct("livre", 12.49);
        Product cd = productManager.addProduct("cd", 14.99, true);
        Product barre = productManager.addProduct("barre", 0.85);

        Assert.assertTrue("12,49".equals(productManager.format(livre.getAmountTTC())));
        Assert.assertTrue("16,49".equals(productManager.format(cd.getAmountTTC())));
        Assert.assertTrue("0,85".equals(productManager.format(barre.getAmountTTC())));
        Assert.assertTrue("1,50".equals(productManager.format(productManager.getTotalTax())));
        Assert.assertTrue("29,83".equals(productManager.format(productManager.getTotalAmount())));
    }

    @Test
    public void testInput2() {
        ProductManager productManager = new ProductManager();
        Product boite = productManager.addProduct("boite", 10.0, false, true);
        Product parfum = productManager.addProduct("parfum", 47.5, true, true);

        Assert.assertTrue("10,50".equals(productManager.format(boite.getAmountTTC())));
        Assert.assertTrue("54,65".equals(productManager.format(parfum.getAmountTTC())));
        Assert.assertTrue("7,65".equals(productManager.format(productManager.getTotalTax())));
        Assert.assertTrue("65,15".equals(productManager.format(productManager.getTotalAmount())));
    }

    @Test
    public void testInput3() {
        ProductManager productManager = new ProductManager();
        Product parfumImporte = productManager.addProduct("parfumImporte", 27.99, true, true);
        Product parfum = productManager.addProduct("parfum", 18.99, true);
        Product pillule = productManager.addProduct("pillule", 9.75);
        Product chocolat = productManager.addProduct("chocolat", 11.25, false, true);

        Assert.assertTrue("32,19".equals(productManager.format(parfumImporte.getAmountTTC())));
        Assert.assertTrue("20,89".equals(productManager.format(parfum.getAmountTTC())));
        Assert.assertTrue("9,75".equals(productManager.format(pillule.getAmountTTC())));
        Assert.assertTrue("11,85".equals(productManager.format(chocolat.getAmountTTC())));
        Assert.assertTrue("6,70".equals(productManager.format(productManager.getTotalTax())));
        Assert.assertTrue("74,68".equals(productManager.format(productManager.getTotalAmount())));
    }
}
