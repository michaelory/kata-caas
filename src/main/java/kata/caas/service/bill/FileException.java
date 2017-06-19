package kata.caas.service.bill;

/**
 * Created by ORY099M on 19/06/2017.
 */
public class FileException extends Exception {

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(String message) {
        super(message);
    }
}
