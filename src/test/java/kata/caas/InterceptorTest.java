package kata.caas;

import kata.caas.service.format.Format;
import kata.caas.util.WeldJUnit4Runner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Created by ORY099M on 15/06/2017.
 */
@RunWith(WeldJUnit4Runner.class)
public class InterceptorTest {

    @Inject
    private IVehicle vehicle;

    @Test
    public void testInterceptor() {
        assert "myVehicle".equals(vehicle.getName());
        assert vehicle.getPower() == 15.02;
    }

    public static class Vehicle implements IVehicle {

        @Override
        public String getName() {
            return "myVehicle";
        }

        @Format
        @Override
        public Double getPower() {
            return 15.015;
        }

    }

    public interface IVehicle {
        String getName();

        Double getPower();

    }
}
