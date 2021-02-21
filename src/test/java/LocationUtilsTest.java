import com.decoder.domain.Coordinates;
import com.decoder.domain.Satellite;
import com.decoder.service.LocationService;
import com.decoder.service.SatelliteService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class LocationUtilsTest {

    private LocationService locationService;
    private SatelliteService satelliteService;
    private List<Satellite> satellites;

    @Before
    public void setUp(){
        satelliteService = Mockito.mock(SatelliteService.class);
        locationService = new LocationService(satelliteService);
        satellites = new ArrayList<>();

        Satellite kenobiSatellite = new Satellite();
        kenobiSatellite.setName("Kenoby");
        kenobiSatellite.setCoordinates(new Coordinates(-500, -200));
        Satellite skywalkerSatellite = new Satellite();
        skywalkerSatellite.setName("Skywalker");
        skywalkerSatellite.setCoordinates(new Coordinates(100, -100));
        Satellite satoSatellite = new Satellite();
        satoSatellite.setName("Sato");
        satoSatellite.setCoordinates(new Coordinates(500, 100));

        satellites.add(kenobiSatellite);
        satellites.add(skywalkerSatellite);
        satellites.add(satoSatellite);
    }

    @Test
    public void getLocation_validDistanceLocation_returnsLocation(){
        Mockito.when(satelliteService.findAll()).thenReturn(satellites);
        float kenobisDistance = 200;
        float skywalkerDistance = 470.4f;
        float satoDistance = 880;
        Coordinates transmitterCoordinates = locationService.getLocation(kenobisDistance, skywalkerDistance, satoDistance);

        Assertions.assertThat(transmitterCoordinates).isNotNull();
    }
}
