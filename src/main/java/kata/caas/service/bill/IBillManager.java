package kata.caas.service.bill;

import kata.caas.service.cart.ICartManager;

/**
 * Created by ORY099M on 19/06/2017.
 */
public interface IBillManager {

    void generateBill(String path, ICartManager cartManager) throws FileException;

    void printBill() throws FileException;
}
