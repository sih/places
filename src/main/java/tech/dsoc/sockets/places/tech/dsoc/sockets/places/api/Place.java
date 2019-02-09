package tech.dsoc.sockets.places.tech.dsoc.sockets.places.api;

import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author sih
 */
@Entity
@Table(name = "places")
public class Place implements Serializable {

    private static final long serialVersionUID = 8556874861318791324L;
    private static final double RADIUS_OF_EARTH = 6334.4D;
    private static final double LIMIT_LOW = -180;
    private static final double LIMIT_HIGH = 180;


    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long id;

    @Column
    private String cityName;

    @Column
    private String stateName;

    @Column
    private String countryName;

    @Column
    private double latitude;

    @Column
    private double longitude;

    private static final Logger LOGGER = LoggerFactory.getLogger(Place.class);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Double.compare(place.latitude, latitude) == 0 &&
                Double.compare(place.longitude, longitude) == 0 &&
                Objects.equals(id, place.id) &&
                Objects.equals(cityName, place.cityName) &&
                Objects.equals(stateName, place.stateName) &&
                Objects.equals(countryName, place.countryName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, cityName, stateName, countryName, latitude, longitude);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
