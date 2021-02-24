package com.decoderapi.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Satellite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Embedded
    private Coordinates coordinates;
}
