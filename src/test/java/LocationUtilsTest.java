import domain.Coordinates;
import domain.Satellite;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.LocationService;

public class LocationUtilsTest {

    LocationService locationService;

    @BeforeEach
    public void setUp(){
        locationService = new LocationService();
        Satellite kenobiSatellite = new Satellite();
        kenobiSatellite.setName("Kenoby");
        kenobiSatellite.setCoordinates(new Coordinates(-500, -200));
        Satellite skywalkerSatellite = new Satellite();
        skywalkerSatellite.setName("Skywalker");
        skywalkerSatellite.setCoordinates(new Coordinates(100, -100));
        Satellite satoSatellite = new Satellite();
        satoSatellite.setName("Sato");
        satoSatellite.setCoordinates(new Coordinates(500, 100));

        locationService.addSatellites(kenobiSatellite, skywalkerSatellite,satoSatellite);
    }
    @Test
    public void getLocation_validDistanceLocation_returnsLocation(){
        float kenobisDistance = 700;
        float skywalkerDistance = 290.5f;
        float satoDistance = 400;
        Coordinates transmitterCoordinates = locationService.getLocation(kenobisDistance, skywalkerDistance, satoDistance);

        Assertions.assertThat(transmitterCoordinates).isNotNull();
    }
}
