package com.decoder.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Satellite {

    @Id
    @GeneratedValue
    private String id;
    private String name;
    private float xPosition;
    private float yPosition;

    public Coordinates getCoordinates(){
        return new Coordinates(getXPosition(), getYPosition());
    }

    public void setCoordinates(Coordinates coordinates){
        setXPosition(coordinates.getXPosition());
        setYPosition(coordinates.getYPosition());
    }
}
