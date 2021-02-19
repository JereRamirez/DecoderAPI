package api;

import domain.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class TopSecret {
    public Coordinates coordinates;
    public String message;
}
