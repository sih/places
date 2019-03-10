package tech.dsoc.scokets.places.config;

/**
 * @author sih
 */

import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import tech.dsoc.sockets.places.ds.PlaceRepo;

/**
 * @author sih
 */
@Profile("mock-repo-config")
@Configuration
@Slf4j
public class DataServiceConfiguration {


    @Bean
    @Primary
    public PlaceRepo placeRepo() {
        return Mockito.mock(PlaceRepo.class);
    }

}
