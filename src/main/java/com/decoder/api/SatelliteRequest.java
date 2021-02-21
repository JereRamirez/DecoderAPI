package com.decoder.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class SatelliteRequest {

    @Id
    @GeneratedValue
    private String id;
    private String name;
    private float distance;
    @ElementCollection
    private List<String> message;
}
