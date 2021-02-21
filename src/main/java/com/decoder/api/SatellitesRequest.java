package com.decoder.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SatellitesRequest {
    List<SatelliteRequest> satellites;
}
