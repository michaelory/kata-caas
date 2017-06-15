package kata.caas.service.format;

import kata.caas.util.Log;
import org.slf4j.Logger;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by ORY099M on 15/06/2017.
 */
@Format
@Interceptor
@Priority(1)
public class FormatInterceptor {

    @Inject
    @Log
    private Logger LOG;

    @AroundInvoke
    public Object format(InvocationContext ctx) throws Exception {
        LOG.debug("Entering method {} in class {}", new Object[]{ctx.getMethod().getName(), ctx.getMethod().getDeclaringClass().getName()});
        Format format = ctx.getMethod().getAnnotation(Format.class);
        DecimalFormat decimalFormat;
        LOG.debug("format {}, precision {}", new Object[]{format.value(), format.halfUpFormat()});
        switch (format.value()) {
            case Format.HALF_UP:
                decimalFormat = new DecimalFormat(format.halfUpFormat());
                decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
                break;
            default:
                throw new FormatException("Format no supported");
        }
        return Double.valueOf(decimalFormat.format(ctx.proceed()).replace(",", "."));
    }
}
