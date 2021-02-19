package api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SatelliteRequest {
    String name;
    float distance;
    String[] message;
}
