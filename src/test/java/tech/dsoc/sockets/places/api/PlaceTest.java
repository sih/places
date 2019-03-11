package tech.dsoc.sockets.places.api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author sih
 */
public class PlaceTest {

    private Place londonGb;
    private Place testPlace;

    @Before
    public void setUp() {
        londonGb = new Place();
        londonGb.setCityName("London");
        londonGb.setStateName(null);
        londonGb.setCountryName("GB");
        londonGb.setLatitude(51.5118569D);
        londonGb.setLongitude(-0.1280232D);

        testPlace = new Place();
        testPlace.setCityName("London");
        testPlace.setStateName(null);
        testPlace.setCountryName("GB");
        testPlace.setLatitude(51.5118569D);
        testPlace.setLongitude(-0.1280232D);
    }

    @Test
    public void hashCodeShouldBeBasedOnSelectedFields() {
        /*
            cityName
            countryName
            latitude
            longitude
         */
        assertEquals(londonGb.getCityName(), testPlace.getCityName());
        assertEquals(londonGb.getCountryName(), testPlace.getCountryName());
        assertEquals(londonGb.getLatitude(), testPlace.getLatitude());
        assertEquals(londonGb.getLongitude(), testPlace.getLongitude());

        assertEquals(londonGb.hashCode(), testPlace.hashCode());

        // should not be based on state
        testPlace.setStateName("Hello");
        assertNotEquals(londonGb.getStateName(), testPlace.getStateName());

        assertEquals(londonGb.hashCode(), testPlace.hashCode());

        // ... or id
        testPlace.setId(999L);
        assertNotEquals(londonGb.getId(), testPlace.getId());

        assertEquals(londonGb.hashCode(), testPlace.hashCode());


    }

}