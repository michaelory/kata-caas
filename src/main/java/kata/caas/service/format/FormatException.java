package kata.caas.service.format;

/**
 * Created by ORY099M on 15/06/2017.
 */
public class FormatException extends Exception {

    public FormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormatException(String message) {
        super(message);
    }
}
