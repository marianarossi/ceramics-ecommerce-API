package mariana.thePotteryPlace.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mariana.thePotteryPlace.model.Order;
import mariana.thePotteryPlace.model.Product;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
