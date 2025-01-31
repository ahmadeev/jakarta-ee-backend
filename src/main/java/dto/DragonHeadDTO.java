package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DefaultValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DragonHeadDTO {
    @DefaultValue(value="-1")
    private long id;
    private float eyesCount;
    @NotNull
    private Double toothCount; //Поле не может быть null
}
