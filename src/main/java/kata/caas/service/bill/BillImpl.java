package kata.caas.service.bill;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ORY099M on 19/06/2017.
 */
@Target({TYPE, FIELD})
@Retention(RUNTIME)
@Qualifier
public @interface BillImpl {
}
