package com.decoderapi.api;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TopSecret {
    private Position position;
    private String message;
}
