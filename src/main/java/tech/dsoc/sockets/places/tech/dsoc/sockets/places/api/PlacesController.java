package tech.dsoc.sockets.places.tech.dsoc.sockets.places.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.dsoc.sockets.places.ds.PlaceRepo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author sih
 */
@RestController
public class PlacesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlacesController.class);

    @Autowired
    private PlaceRepo repo;

    @RequestMapping(path = "place/{id}", method = RequestMethod.GET)
    public Place getPlace(final @PathVariable(value="id") long id, HttpServletResponse response) {
        Place p = null;
        try {
            p = repo.findById(id).orElse(null);
            if (null == p) {
                String message = "Could not find place with id "+id;
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(message);
            }
        }

        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            LOGGER.error(e.getMessage(),e);
        }
        return p;
    }

}
