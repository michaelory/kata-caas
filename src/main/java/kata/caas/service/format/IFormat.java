package kata.caas.service.format;

import kata.caas.business.Product;

/**
 * Created by ORY099M on 15/06/2017.
 */
public interface IFormat {

    Double formatAmountTTC(Product product);

    Double formatTotalTax();

    Double formatTotalAmount();
}
