package com.decoderapi.utils;

import com.decoderapi.domain.Satellite;
import com.decoderapi.domain.Coordinates;
import com.decoderapi.service.LocationService;
import com.decoderapi.service.SatelliteService;
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
    public void getMessage_nullMessage_throwsException(){
        Assertions.assertThatThrownBy( ()-> locationService.getLocation(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The distances between the satellites can not be empty.");
    }

    @Test
    public void getMessage_otherThanThreeMessages_throwsException(){
        Assertions.assertThatThrownBy( ()-> locationService.getLocation(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The amount of distances must be three.");

        Assertions.assertThatThrownBy( ()-> locationService.getLocation(1,2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The amount of distances must be three.");

        Assertions.assertThatThrownBy( ()-> locationService.getLocation(1,2,3,4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The amount of distances must be three.");
    }

    @Test
    public void getLocation_fromThreeValidDistances_returnsLocation(){
        Mockito.when(satelliteService.findAll()).thenReturn(satellites);
        float kenobiDistance = 200;
        float skywalkerDistance = 470.4f;
        float satoDistance = 880;
        Coordinates transmitterCoordinates = locationService.getLocation(kenobiDistance, skywalkerDistance, satoDistance);

        Assertions.assertThat(transmitterCoordinates).isNotNull();
        Assertions.assertThat(transmitterCoordinates.getX()).isEqualTo(-367.7332f);
        Assertions.assertThat(transmitterCoordinates.getY()).isEqualTo(-49.98171f);
    }

    @Test
    public void getLocation_fromThreeInvalidDistanceCombination_throwsException(){
        Mockito.when(satelliteService.findAll()).thenReturn(satellites);
        float kenobiDistance = 100;
        float skywalkerDistance = 470.4f;
        float satoDistance = 880;

        Assertions.assertThatThrownBy( ()-> locationService.getLocation(kenobiDistance, skywalkerDistance, satoDistance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Can not get valid coordinates from the information received.");
    }
}
