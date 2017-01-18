package is.monkeydrivers.car_sensors;

import is.monkeydrivers.sensors.carsDistance;
import is.monkeydrivers.sensors.travelCar;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class carsDistance_ {


    @Test
    public void isTheSameDistance() throws Exception {
        assertThat(new carsDistance(new travelCar (20, 12), new travelCar (20, 12)).isTheSameSpeed(), is(true));
    }
    @Test
    public void isTheFirstCarMoreFaster() throws Exception {
        assertThat(new carsDistance(new travelCar (80, 20), new travelCar (60, 20)).isTheFirstCarMoreFaster(), is(true));
    }

    @Test
    public void isTheNewSpeed() throws Exception {
        assertThat(new carsDistance(new travelCar (60, 20), new travelCar (80, 20)).newSpeed(), is((double)4));
    }


}
