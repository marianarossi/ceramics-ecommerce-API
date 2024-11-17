package mariana.thePotteryPlace.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mariana.thePotteryPlace.model.Order;
import mariana.thePotteryPlace.model.Product;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor //precisa?
public class OrderItemDTO {
    private Long id;

    private Order order;

    private Product product;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int quantity;
}
