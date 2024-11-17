package mariana.thePotteryPlace.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mariana.thePotteryPlace.model.Address;
import mariana.thePotteryPlace.model.User;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor //precisa?
public class OrderDTO {
    private Long id;

    private User user;

    @NotNull
    private BigDecimal shipping;

    @NotNull
    private String payment;

    @NotNull
    private Address address;

    List<OrderItemDTO> items;
}
