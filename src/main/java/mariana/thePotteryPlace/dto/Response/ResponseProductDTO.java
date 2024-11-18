package mariana.thePotteryPlace.dto.Response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ResponseProductDTO {
    private String title;
    private String description;


    private BigDecimal price;

}
