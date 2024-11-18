package mariana.thePotteryPlace.dto.Response;

import lombok.Data;
import mariana.thePotteryPlace.model.Product;

import java.math.BigDecimal;
@Data

public class ResponseOrderItemDTO {
//    private Long productId;

    private String productName;

    private BigDecimal price;

    private int quantity;
}
