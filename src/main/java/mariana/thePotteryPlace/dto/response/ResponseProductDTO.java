package mariana.thePotteryPlace.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ResponseProductDTO {
    private String title;

    private String text;

    private BigDecimal price;

}
