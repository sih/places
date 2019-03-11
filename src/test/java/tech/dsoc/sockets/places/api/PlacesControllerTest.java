package tech.dsoc.sockets.places.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import tech.dsoc.sockets.places.ds.PlaceRepo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author sih
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class PlacesControllerTest {


    private static final ObjectMapper mapper = new ObjectMapper();
    private Place londonGb;     // does not contain the id (i.e. it's null)
    private Place dbLondonGb;   // contains the id set

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private MediaType contentType = new MediaType(APPLICATION_JSON.getType(),
            APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @MockBean
    private PlaceRepo placeRepo;


    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters)
                .stream()
                .filter(hmc ->
                        hmc instanceof MappingJackson2HttpMessageConverter)
                        .findAny()
                        .get();
    }



    /**
     * Set up the web mvc context and a test fixture for London, GB
     * <code>
     * {
     *  "id": 1,
     *  "cityName": "London",
     *  "stateName": null,
     *  "countryName": "GB",
     *  "latitude": 51.5118569,
     *  "longitude": -0.1280232
     * }
     * </code>
     */
    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        londonGb = new Place();
        londonGb.setCityName("London");
        londonGb.setStateName(null);
        londonGb.setCountryName("GB");
        londonGb.setLatitude(51.5118569D);
        londonGb.setLongitude(-0.1280232D);

        dbLondonGb = new Place();
        dbLondonGb.setId(1L);
        dbLondonGb.setCityName("London");
        dbLondonGb.setStateName(null);
        dbLondonGb.setCountryName("GB");
        dbLondonGb.setLatitude(51.5118569D);
        dbLondonGb.setLongitude(-0.1280232D);

    }

    @Test
    public void saveShouldReturn400AndErrorMessageWhenNoCityName() throws Exception {
        londonGb.setId(null);  // set null as about to save
        londonGb.setCityName(null);

        mockMvc
                .perform(post("/places")
                        .content(mapper.writeValueAsString(londonGb))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].error").value("You need to supply a city name, e.g. London"))
        ;
    }


    @Test
    public void saveShouldReturn400AndErrorMessageWhenNoCountryName() throws Exception {
        londonGb.setId(null);  // set null as about to save
        londonGb.setCountryName(null);

        mockMvc
                .perform(post("/places")
                        .content(mapper.writeValueAsString(londonGb))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].error").value("You need to supply a country name, e.g. GB"))
        ;
    }

    @Test
    public void saveShouldReturn400AndErrorMessageWhenNoLatitude() throws Exception {
        londonGb.setId(null);  // set null as about to save
        londonGb.setLatitude(null);

        mockMvc
                .perform(post("/places")
                        .content(mapper.writeValueAsString(londonGb))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].error").value("You need to supply a valid latitude and longitude"))
        ;
    }

    @Test
    public void saveShouldReturn400AndErrorMessageWhenNoLongitude() throws Exception {
        londonGb.setId(null);  // set null as about to save
        londonGb.setLongitude(null);

        mockMvc
                .perform(post("/places")
                        .content(mapper.writeValueAsString(londonGb))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].error").value("You need to supply a valid latitude and longitude"))
        ;
    }

    @Test
    public void saveShouldReturn400AndCombinedErrorMessages() throws Exception {
        londonGb.setId(null);  // set null as about to save
        londonGb.setCityName(null);
        londonGb.setCountryName(null);
        londonGb.setLatitude(null);
        londonGb.setLongitude(null);

        mockMvc
                .perform(post("/places")
                        .content(mapper.writeValueAsString(londonGb))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].error").value("You need to supply a city name, e.g. London"))
                .andExpect(jsonPath("$[1].error").value("You need to supply a country name, e.g. GB"))
                .andExpect(jsonPath("$[2].error").value("You need to supply a valid latitude and longitude"))
        ;
    }

    @Test
    public void saveShouldNotPersistInvalidRecords() throws Exception {
        londonGb.setId(null);  // set null as about to save
        londonGb.setCityName(null);

        mockMvc
                .perform(post("/places")
                        .content(mapper.writeValueAsString(londonGb))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
        ;

        Mockito.verify(placeRepo, never()).save(londonGb);

    }

    @Test
    public void saveShouldPersistValidRecords() throws Exception {

        when(placeRepo.save(londonGb)).thenReturn(dbLondonGb);
        mockMvc
                .perform(post("/places")
                        .content(mapper.writeValueAsString(londonGb))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .characterEncoding("utf-8"))
        ;

        Mockito.verify(placeRepo, times(1)).save(londonGb);
    }


    @Test
    public void saveShouldReturnCreatedStatusCodesForValidRecords() throws Exception {
        when(placeRepo.save(londonGb)).thenReturn(dbLondonGb);
        mockMvc
                .perform(post("/places")
                        .content(mapper.writeValueAsString(londonGb))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
        ;

    }


    @Test
    public void saveShouldReturnTheCorrectLocationHeaderForValidRecords() throws Exception {
        when(placeRepo.save(londonGb)).thenReturn(dbLondonGb);
        String expectedLondonGbHeader  = "/places/"+dbLondonGb.getId();
        mockMvc
                .perform(post("/places")
                        .content(mapper.writeValueAsString(londonGb))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .characterEncoding("utf-8"))
                .andExpect(header().string("Location", expectedLondonGbHeader))
        ;

    }

    @Test
    public void getShouldReturn404WhenNoSuchId() throws Exception {
        Long noSuchId = 99L;
        when(placeRepo.findById(noSuchId)).thenReturn(null);
        mockMvc
                .perform(get("/places{id}", noSuchId))
                .andExpect(status().isNotFound())
        ;
    }


    @Test
    public void getShouldReturn200AndValidObjectWhenIdMatches() throws Exception {
        when(placeRepo.findById(dbLondonGb.getId()))
                .thenReturn(Optional.of(dbLondonGb))
        ;
        mockMvc
                .perform(get("/places/{id}", dbLondonGb.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dbLondonGb.getId().intValue()))
                .andExpect(jsonPath("$.cityName").value(dbLondonGb.getCityName()))
                .andExpect(jsonPath("$.countryName").value(dbLondonGb.getCountryName()))
                .andExpect(jsonPath("$.latitude").value(dbLondonGb.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(dbLondonGb.getLongitude()))
        ;
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}