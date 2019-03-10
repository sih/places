package tech.dsoc.sockets.places.api;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This is a domain object tied to the places table.<p/>
 * One place is considered the same as another if and only if they share the same:
 * <ul>
 *     <li>cityName</li>
 *     <li>countryName</li>
 *     <li>latitude</li>
 *     <li>longitude</li>
 * </ul>
 *
 * @author sih
 */
@Entity
@Table(name = "places")
@Getter
@Setter
@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
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
    @EqualsAndHashCode.Include
    private String cityName;

    @Column
    private String stateName;

    @Column
    @EqualsAndHashCode.Include
    private String countryName;

    @Column
    @EqualsAndHashCode.Include
    private Double latitude;

    @Column
    @EqualsAndHashCode.Include
    private Double longitude;

}
