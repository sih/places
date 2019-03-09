package tech.dsoc.sockets.places.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tech.dsoc.sockets.places.ds.PlaceRepo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sih
 */
@RestController
public class PlacesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlacesController.class);

    @Autowired
    private PlaceRepo repo;

    @RequestMapping(
            path = "places/{id}",
            method = RequestMethod.GET,
            produces = "application/json"
            )
    public Place get(final @PathVariable(value="id") long id, HttpServletResponse response) {
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

    @RequestMapping(
            path = "places",
            method = RequestMethod.POST,
            consumes = "application/json"
    )
    public void save(@RequestBody final Place place, HttpServletResponse response) {
        try {
            List<String> errors = new ArrayList<>();
            if (place != null) {
                if (StringUtils.isEmpty(place.getCityName())) {
                    errors.add("You need to supply a city name, e.g. London");
                } else if (StringUtils.isEmpty(place.getCountryName())) {
                    errors.add("You need to supply an ISO-3166 country code, e.g. GB");
                } else if (null == place.getLatitude() || null == place.getLongitude()) {
                    errors.add("You need to supply a valid latitude and longitude");
                } else {
                    Place savedPlace = repo.save(place);
                    response.setHeader(HttpHeaders.LOCATION, "/places/"+savedPlace.getId());
                }
            } else {
                errors.add("You need to supply a valid place to save");
            }
            if (errors.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(errorJson(errors));
            }
        } catch(IOException ioe) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String errorJson(final List<String> errors) {
        StringBuilder builder = new StringBuilder()
                .append("[");
        for (String error: errors) {
            builder
                    .append("{")
                    .append("\"error\":")
                    .append(" ")
                    .append("\"")
                    .append(error)
                    .append("\"")
                    .append("}")
                    .append(",");
        }
        builder.replace(builder.lastIndexOf(","),builder.lastIndexOf(",")+1,"");
        builder.append("]");
        return builder.toString();
    }

}