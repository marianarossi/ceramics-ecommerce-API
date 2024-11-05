package mariana.thePotteryPlace.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mariana.thePotteryPlace.model.Order;
import mariana.thePotteryPlace.model.Product;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToOne
    private Product product;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int quantity;
}
