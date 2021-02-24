package com.decoderapi.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SatellitesRequest {
    private List<SatelliteInfo> satellites;
}
