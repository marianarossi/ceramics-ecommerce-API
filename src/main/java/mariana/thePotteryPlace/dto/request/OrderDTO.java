package mariana.thePotteryPlace.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mariana.thePotteryPlace.model.Address;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor //precisa?
public class OrderDTO {
    private Long userid;

    @NotNull
    private BigDecimal shipping;

    @NotNull
    private String payment;

    @NotNull
    private Address address;

    List<OrderItemDTO> items;
}
