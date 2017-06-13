package kata.caas;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by michael on 13/06/17.
 */
public class ProductTest {

    @Test
    public void testInput1() {
        ProductManager productManager = new ProductManager();
        Product livre = new Product();
        livre.setLabel("livre");
        livre.setAmountHT(12.49);
        productManager.addProduct(livre);

        Product cd = new Product();
        cd.setLabel("cd");
        cd.setAmountHT(14.99);
        cd.setTvaApplied(true);
        productManager.addProduct(cd);

        Product barre = new Product();
        barre.setLabel("barre");
        barre.setAmountHT(0.85);
        productManager.addProduct(barre);

        Map<String, String> amounts = productManager.calculateAmount();
        Assert.assertTrue(amounts.get("livre"), "12,49".equals(amounts.get("livre")));
        Assert.assertTrue(amounts.get("cd"), "16,49".equals(amounts.get("cd")));
        Assert.assertTrue(amounts.get("barre"), "0,85".equals(amounts.get("barre")));
        Assert.assertTrue(amounts.get(ProductManager.TOTAL_TAX), "1,50".equals(amounts.get(ProductManager.TOTAL_TAX)));
        Assert.assertTrue(amounts.get(ProductManager.TOTAL_AMOUNT), "29,83".equals(amounts.get(ProductManager.TOTAL_AMOUNT)));
    }

    @Test
    public void testInput2() {
        ProductManager productManager = new ProductManager();
        Product boite = new Product();
        boite.setLabel("boite");
        boite.setAmountHT(10.0);
        boite.setImported(true);
        productManager.addProduct(boite);

        Product parfum = new Product();
        parfum.setLabel("parfum");
        parfum.setAmountHT(47.5);
        parfum.setTvaApplied(true);
        productManager.addProduct(parfum);

        Map<String, String> amounts = productManager.calculateAmount();
        Assert.assertTrue("10,50".equals(amounts.get("boite")));
        Assert.assertTrue(amounts.get("parfum"), "54,65".equals(amounts.get("parfum")));
        Assert.assertTrue(amounts.get(ProductManager.TOTAL_TAX), "7,65".equals(amounts.get(ProductManager.TOTAL_TAX)));
        Assert.assertTrue(amounts.get(ProductManager.TOTAL_AMOUNT), "65,15".equals(amounts.get(ProductManager.TOTAL_AMOUNT)));
    }

    @Test
    public void testInput3() {
        ProductManager productManager = new ProductManager();
        Product parfumImporte = new Product();
        parfumImporte.setLabel("parfumImporte");
        parfumImporte.setAmountHT(27.99);
        parfumImporte.setTvaApplied(true);
        parfumImporte.setImported(true);
        productManager.addProduct(parfumImporte);

        Product parfum = new Product();
        parfum.setLabel("parfum");
        parfum.setAmountHT(18.99);
        parfum.setTvaApplied(true);
        productManager.addProduct(parfum);

        Product livre = new Product();
        livre.setLabel("livre");
        livre.setAmountHT(12.49);
        productManager.addProduct(livre);

        Product pillule = new Product();
        pillule.setLabel("pillule");
        pillule.setAmountHT(9.75);
        productManager.addProduct(pillule);

        Product chocolat = new Product();
        chocolat.setLabel("chocolat");
        chocolat.setAmountHT(11.25);
        chocolat.setImported(true);
        productManager.addProduct(chocolat);

        Map<String, String> amounts = productManager.calculateAmount();
        Assert.assertTrue("32,19".equals(amounts.get("parfumImporte")));
        Assert.assertTrue("20,89".equals(amounts.get("parfum")));
        Assert.assertTrue("9,75".equals(amounts.get("pillule")));
        Assert.assertTrue("11,85".equals(amounts.get("chocolat")));
        Assert.assertTrue(amounts.get(ProductManager.TOTAL_TAX), "6,70".equals(amounts.get(ProductManager.TOTAL_TAX)));
        Assert.assertTrue(amounts.get(ProductManager.TOTAL_AMOUNT), "74,68".equals(amounts.get(ProductManager.TOTAL_AMOUNT)));
    }
}
