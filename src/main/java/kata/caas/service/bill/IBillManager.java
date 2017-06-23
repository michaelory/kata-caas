package kata.caas.service.bill;

import kata.caas.service.cart.ICartManager;

import java.io.IOException;

/**
 * Created by ORY099M on 19/06/2017.
 */
public interface IBillManager {

    void generateBill(String path, ICartManager cartManager) throws IOException;

    void printBill() throws IOException;
}
