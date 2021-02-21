package com.decoder.api;

import com.decoder.api.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TopSecret {
    public Coordinates coordinates;
    public String message;
}
