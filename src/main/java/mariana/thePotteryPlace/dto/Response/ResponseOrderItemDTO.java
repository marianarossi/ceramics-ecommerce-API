package mariana.thePotteryPlace.dto.Response;

import lombok.Data;
import mariana.thePotteryPlace.model.Product;

import java.math.BigDecimal;
@Data

public class ResponseOrderItemDTO {
    private Product product;

    private BigDecimal price;

    private int quantity;
}
