package kata.caas.business;

import kata.caas.service.format.Format;
import kata.caas.service.format.IProductFormat;

import javax.inject.Singleton;
import java.util.Map;

/**
 * Created by ORY099M on 20/06/2017.
 */
@Singleton
public class Cart implements IProductFormat {

    private Map<String, QuantityOfProduct> cartMap;

    private Double totalTax;
    private Double totalAmount;

    @Override
    @Format
    public Double formatAmountTTC(Product product) {
        return product.getAmountTTC();
    }

    @Override
    @Format
    public Double formatTotalTax() {
        return totalTax;
    }

    @Override
    @Format
    public Double formatTotalAmount() {
        return getTotalAmount();
    }

    public Map<String, QuantityOfProduct> getCartMap() {
        return cartMap;
    }

    public void setCartMap(Map<String, QuantityOfProduct> cartMap) {
        this.cartMap = cartMap;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
