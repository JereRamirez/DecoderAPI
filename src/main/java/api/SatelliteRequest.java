package api;

import lombok.Data;

@Data
public class SatelliteRequest {
    String name;
    float distance;
    String[] message;
}
