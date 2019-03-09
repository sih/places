package tech.dsoc.sockets.places.ds;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.dsoc.sockets.places.api.Place;

/**
 * @author sih
 */
@Repository
public interface PlaceRepo extends CrudRepository<Place, Long> {

}
