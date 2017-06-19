package kata.caas.service.bill;

/**
 * Created by ORY099M on 19/06/2017.
 */
public interface IBillManager {

    void generateBill(String path) throws FileException;

    void printBill() throws FileException;
}
