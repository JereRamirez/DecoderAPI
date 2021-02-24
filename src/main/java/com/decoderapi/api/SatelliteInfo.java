package com.decoderapi.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SatelliteInfo {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    private float distance;
    private String[] message;
}
