package mariana.thePotteryPlace.dto.response;

import lombok.Data;

import java.math.BigDecimal;
@Data

public class ResponseOrderItemDTO {
//    private Long productId;

    private String productName;

    private BigDecimal price;

    private int quantity;
}
